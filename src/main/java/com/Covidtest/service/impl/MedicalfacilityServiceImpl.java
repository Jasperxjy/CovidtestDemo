package com.Covidtest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.Covidtest.dto.MedicalLoginDTO;
import com.Covidtest.dto.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.MedicalfacilityDao;
import com.Covidtest.entity.Medicalfacility;
import com.Covidtest.service.MedicalfacilityService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.Covidtest.utils.RedisConstants.MEDICAL_LOGIN_TIME;
import static com.Covidtest.utils.RedisConstants.MEDICAL_LOGIN_TOKEN;

/**
 * (Medicalfacility)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:08:49
 */
@Service("medicalfacilityService")
public class MedicalfacilityServiceImpl extends ServiceImpl<MedicalfacilityDao, Medicalfacility> implements MedicalfacilityService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result login(MedicalLoginDTO medicalLoginDTO, HttpSession session) {
        //读取信息
        String id=medicalLoginDTO.getId();
        String password=medicalLoginDTO.getPassword();
        //判断是否为空
        if(id==null||password==null) {
            //为空，返回错误
            return Result.fail("登录信息不全，请重新输入");
        }
        Medicalfacility medicalfacility;
        //在数据库种查找用户对象
        medicalfacility=query().eq("medicalfacilityid",id).one();
        //为空，返回错误信息
        if(medicalfacility==null) {
            return Result.fail("用户不存在，请重试");
        }
        //检查密码是否正确
        if(!password.equals(medicalfacility.getPassword())) {
            //错误，返回错误信息
            return Result.fail("用户密码错误，请重新输入");
        }
        //创建map
        Map<String,Object> medicalMap= BeanUtil.beanToMap(medicalfacility);
        //创建token
        String token= UUID.randomUUID().toString();
        //写入redis
        stringRedisTemplate.opsForHash().putAll(MEDICAL_LOGIN_TOKEN+token,medicalMap);
        //刷新缓存
        stringRedisTemplate.expire(MEDICAL_LOGIN_TOKEN+token,MEDICAL_LOGIN_TIME, TimeUnit.MINUTES);
        //返回token
        return Result.ok(token);
    }

    @Override
    public Result sign_in(Medicalfacility medicalfacility, HttpSession session) {
        //校验是否为空
        if(medicalfacility.getName().isEmpty()||medicalfacility.getMedicalfacilityid().isEmpty()||medicalfacility.getPassword().isEmpty()) {
            //为空，返回错误信息
            return Result.fail("注册信息不全，请重新输入");
        }
        //按照id从数据库中查找
        Medicalfacility newmedical;
        if(query().eq("medicalfacilityid",medicalfacility.getMedicalfacilityid()).one()!=null) {
            //存在，返回错误信息
            return Result.fail("用户已经存在，请勿重复注册");
        }
        //将生成的对象存入数据库
        newmedical=new Medicalfacility();
        newmedical.setName(medicalfacility.getName());
        newmedical.setPassword(medicalfacility.getPassword());
        newmedical.setMedicalfacilityid(medicalfacility.getMedicalfacilityid());
        save(newmedical);
        //返回成功信息
        return Result.ok();
    }

    @Override
    public Result set_info(Medicalfacility medicalfacility, HttpSession session) {
        //TODO 这里实际上有严重的安全问题，没有判断当前登录用户与传入需要更改的用户id是否相同
        //获取id
        if(medicalfacility.getMedicalfacilityid().isEmpty()){
            return Result.fail("信息不全，需要提供id");
        }
        //从数据库中查找对象
        Medicalfacility medical=query().eq("medicalfacilityid",medicalfacility.getMedicalfacilityid()).one();
        //判断是否存在
        if (medical==null) {
            //不存在，返回错误信息
           return Result.fail("传入的用户不存在");
        }else {
            medical.setMedicalfacilityid(medicalfacility.getMedicalfacilityid());
        }
        //更改这个对象的属性
        if(!medicalfacility.getName().isEmpty()){
        medical.setName(medicalfacility.getName());
        }
        if(!medicalfacility.getPassword().isEmpty()){
            medical.setPassword(medicalfacility.getPassword());
        }
        //update这个对象
        updateById(medical);
        //返回成功信息
        return Result.ok();
    }
}

