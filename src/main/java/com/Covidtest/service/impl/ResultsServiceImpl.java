package com.Covidtest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.Covidtest.dao.ResultsDao;
import com.Covidtest.dto.GetResultsDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.dto.SetReturnResultsDTO;
import com.Covidtest.entity.Results;
import com.Covidtest.service.ResultsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.Covidtest.utils.RedisConstants.CACHE_RESULTS;
import static com.Covidtest.utils.RedisConstants.CACHE_RESULT_LIMIT_TIME;

/**
 * 检测结果表(Results)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:09:26
 */
@Service("resultsService")
public class ResultsServiceImpl extends ServiceImpl<ResultsDao, Results> implements ResultsService {

    @Resource(name = "myredistemplete")
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public Result get_result(GetResultsDTO getResultsDTO, HttpSession session) {
        //新建返回对象
        SetReturnResultsDTO returnResultsDTO = new SetReturnResultsDTO();
        //遍历传入的数组
        for(String groupid:getResultsDTO.getGroups()) {
            //对每一个管号，新建结果表对象
            Results result;
            //查询缓存
            Map<String, Object> resultMap = (Map<String, Object>) (Object)redisTemplate.opsForHash().entries(CACHE_RESULTS+groupid);
            //检查是否为空
            if(!resultMap.isEmpty()) {
                //不为空，设置缓存持续时间,为七天
                redisTemplate.expire(CACHE_RESULTS+groupid,CACHE_RESULT_LIMIT_TIME , TimeUnit.DAYS);
                //将map转换为对象
                JSONObject jsonObject = new JSONObject();
                jsonObject.putAll(resultMap);
                result= jsonObject.toBean(Results.class);
                //将对象加入到返回的DTO的列表中
                returnResultsDTO.getSet_return_results().add(result);
            }else{
            //为空，查询数据库
            result=query().eq("group_t",groupid).one();
            //检查是否为空
            if(result!=null) {

            //不为空，转换为map
            resultMap = BeanUtil.beanToMap(result);
            //存入缓存
            redisTemplate.opsForHash().putAll(CACHE_RESULTS+groupid,resultMap);
            //设置缓存持续时间
            redisTemplate.expire(CACHE_RESULTS+groupid,CACHE_RESULT_LIMIT_TIME , TimeUnit.DAYS);
            //将对象加入到返回的DTO的列表中
            returnResultsDTO.getSet_return_results().add(result);
            }
            }
        }
        //返回这个返回DTO对象
        return Result.ok(returnResultsDTO);
    }

    @Transactional//这个注解表示开始事务，此后可以使用其它的手动的事务和异常、回滚处理机制
    @Override
    public Result new_result(Results results, HttpSession session) {
        //判断是否合乎规范
        if(results.getGroupT()==null||results.getResult()!=null||results.getDetecttime()!=null||results.getDetectunit()!=null) {
            //否，返回错误信息
            return Result.fail("传入数据不合规范");
        }
        //存储到结果表中
        save(results);
        //创建map
        Map<String ,Object> resultMap = BeanUtil.beanToMap(results);
        //将map存储到缓存
        redisTemplate.opsForHash().putAll(CACHE_RESULTS+results.getGroupT(),resultMap);
        //设置缓存时间
        redisTemplate.expire(CACHE_RESULTS+results.getGroupT(),CACHE_RESULT_LIMIT_TIME,TimeUnit.DAYS);
        //返回成功
        return Result.ok();
    }

    //TODO 这个地方要加锁
    @Override
    public Result set_result(SetReturnResultsDTO setResultsDTO, HttpSession session) {
        //array不为空
        if(setResultsDTO.getSet_return_results().isEmpty()){
            return Result.fail("传入的数据为空！");
        }
        //从DTO中对每一个array遍历
        for(Results result:setResultsDTO.getSet_return_results()) {
            //对每一个更改，检查单号和结果不为空
            if(result.getGroupT().isEmpty()||result.getResult().isEmpty()){
                result.setResult("false");
                continue;
            }
            //从数据库按照组号查找一个对象
            Results oldresult = query().eq("group_T",result.getGroupT()).one();
            //判断是否为空
            if(oldresult==null) {
                //为空，将DTO中的结果设置为false，continue
                result.setResult("false");
                continue;
            }
            //设置结果
            oldresult.setResult(result.getResult());
            //判断更改是否有时间
            if(result.getDetecttime()!=null) {
                //有设置时间为更改的时间
                oldresult.setDetecttime(result.getDetecttime());
            }else {
                //无设置为传过来的统一的时间
                if(setResultsDTO.getTime()!=null){
                    oldresult.setDetecttime(setResultsDTO.getTime());
                }else {
                    //无，返回错误
                    result.setResult("false");
                    continue;
                }
            }
            //判断更改有无地点
            if (!result.getDetectunit().isEmpty()) {
                //有设置地点为更改的地点
                oldresult.setDetectunit(result.getDetectunit());
            }else {
                //无，设置为传过来的统一地点
                if(!setResultsDTO.getMedical_name().isEmpty()){
                    oldresult.setDetectunit(setResultsDTO.getMedical_name());
                }else {
                    //无，返回错误
                    result.setResult("false");
                    continue;
                }
            }
            //将这个对象存储到数据库
            updateById(oldresult);
            //将这个对象转换为map
            Map<String,Object> oldresultMap = BeanUtil.beanToMap(oldresult);
            //将这个对象添加到缓存
            redisTemplate.opsForHash().putAll(CACHE_RESULTS+oldresult.getGroupT(),oldresultMap);
            //将这个缓存时间重置
            redisTemplate.expire(CACHE_RESULTS+oldresult.getGroupT(),CACHE_RESULT_LIMIT_TIME,TimeUnit.DAYS);
            //将DTO中的结果设置为true,表示这个成功
            result.setResult("true");
        }
        //返回成功,setResultDTO的结果中包含了对应记录的执行结果
        return Result.ok(setResultsDTO);
    }

}

