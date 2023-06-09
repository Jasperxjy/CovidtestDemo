package com.Covidtest.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.Covidtest.dao.RegisterDao;
import com.Covidtest.dto.GetOrderDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.entity.Register;
import com.Covidtest.service.RegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.Covidtest.utils.RedisConstants.*;

/**
 * 核酸登记表(Register)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:09:09
 */
@Service("registerService")
public class RegisterServiceImpl extends ServiceImpl<RegisterDao, Register> implements RegisterService {

    @Resource(name = "myredistemplete")
    private RedisTemplate<String, Object> stringRedisTemplate;

    private boolean tryLock(String key){
        //获取锁
       Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key,"1",10,TimeUnit.SECONDS);
       return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key){
        //释放锁
        stringRedisTemplate.delete(key);
    }

    @Override
    public Result get_14d_Orders(GetOrderDTO getOrderDTO, HttpSession session) {
        //TODO 实现基础查询操作，直接查询缓存
        //取出身份证信息
        String id = getOrderDTO.getId();
        //读取对应的缓存信息
        Map<Object,Object> ordersMap = stringRedisTemplate.opsForHash().entries(CACHE_REGISTER + id);
        //判断是否存在
        if(ordersMap.isEmpty()) {
            //不存在，返回提示刷新
            return Result.fail("缓存空，点击刷新重试");
        }
        //刷新缓存持续时间
        stringRedisTemplate.expire(CACHE_REGISTER + id ,CACHE_ORDERS_LIMIT_TIME , TimeUnit.DAYS);
        //存在，返回json
        return Result.ok(ordersMap);
    }

    @Override
    public Result refresh(GetOrderDTO getOrderDTO, HttpSession session){
        // 从数据库中查询，并添加到缓存中，返回查询结果
        //取出身份证和时间信息
        String id = getOrderDTO.getId();
        LocalDateTime current = getOrderDTO.getCurrent_time();
        //查询刷新缓存间隔时间是否满足
        Map<Object, Object> temp = stringRedisTemplate.opsForHash().entries(LIMIT_REGISTER_REFRESH+id);
        if(temp.get("1")!=null){
            return Result.fail("刷新过于频繁，请稍后重试");
        }
        //检查完毕，立刻加锁增加间隔时间
        stringRedisTemplate.opsForHash().put(LIMIT_REGISTER_REFRESH+id, "1","wait");
        stringRedisTemplate.expire(LIMIT_REGISTER_REFRESH+id ,CACHE_REFRESH_LIMIT_TIME , TimeUnit.MINUTES);
        //读取数据库中id符合且14天内的记录
        //新建一个当前时间14天前的时间
        LocalDateTime startDate = current.plusDays(-14);
        //以列表的形式取出sql中的信息
        List<Register> orderList = lambdaQuery()
                .in(Register::getId,id)
                .ge(Register::getRegistime, startDate) // 大于等于起始时间
                .le(Register::getRegistime, current)// 小于等于当前时间
                .list();
        //判断是否为空
        if(orderList.isEmpty()){
            return  Result.fail("查询记录为空");
        }
        //将列表中的信息，以时间为key存储到ordersmap中，这样可以使用时间模糊访问每一个记录
        // 若有两个记录时间相同（实际上在添加到数据库中时就是不允许的，但为了最大程度减少bug），选取第一个，抛弃第二个
        Map<LocalDateTime,Object> ordersMap = orderList.stream()
                .collect(
                        Collectors.toMap(
                                Register::getRegistime, Function.identity(),(u1, u2) -> u1)
                );
        //将上面生成的map以hash的方式，以id为key，存储在redis中
        //注意，redis的策略表明，若出现了相同的key，那么会覆盖上一个的存储的对象。这正好是更新缓存所需要的。
        stringRedisTemplate.opsForHash().putAll(CACHE_REGISTER + id , ordersMap);
        //设置缓存持续时间,在常数配置中为7天
        stringRedisTemplate.expire(CACHE_REGISTER + id ,CACHE_ORDERS_LIMIT_TIME , TimeUnit.DAYS);
        //返回结果
        return Result.ok(ordersMap);
    }

    @Transactional//这个注解表示开始事务，此后可以使用其它的手动的事务和异常、回滚处理机制
    @Override
    public Result neworder(Register register, HttpSession session) {//可能会有新的插入策略以应对高峰期的插入请求，具体请参考业务测试结果
        //TODO 完成创建新订单，并同时添加到缓存中，要保证事务的一致性
        //获取传入订单中的必要信息
        String id = register.getId();
        LocalDateTime time = register.getRegistime();
        String naid = register.getNaid();
        String group = register.getGroupT();
        //校验前端数据,不为空
        if(id==null||time==null
                ||naid==null||group==null){
            return Result.fail("订单创建失败，数据不完整");
        }
        //向mysql中插入这条新订单
        save(register);
        //从缓存中抽出对象
        Map<Object,Object> ordersMap = stringRedisTemplate.opsForHash().entries(CACHE_REGISTER + id);
        //在对象中添加一条记录,不管到底空不空
            ordersMap.put(register.getRegistime(), register);
        //将map存回redis中
        stringRedisTemplate.opsForHash().putAll(CACHE_REGISTER + id , ordersMap);
        //刷新缓存时间
        stringRedisTemplate.expire(CACHE_REGISTER + id ,CACHE_ORDERS_LIMIT_TIME , TimeUnit.DAYS);
        //返回执行结果
        return Result.ok();
    }
}

