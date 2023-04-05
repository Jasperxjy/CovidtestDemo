package com.Covidtest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.Covidtest.dto.EmployeeLoginFormDTO;
import com.Covidtest.dto.EmployeeLogoutDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.service.ScannersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.EmployeesDao;
import com.Covidtest.entity.Employees;
import com.Covidtest.service.EmployeesService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.Covidtest.utils.RedisConstants.*;

/**
 * (Employees)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:08:31
 */
@Service("employeesService")
public class EmployeesServiceImpl extends ServiceImpl<EmployeesDao, Employees> implements EmployeesService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ScannersService scannersService;

    @Transactional//这个注解表示开始事务
    @Override
    public Result login(EmployeeLoginFormDTO employeeLoginFormDTO, HttpSession session) {
        //TODO 实现登录功能
        //提取信息
        String id = employeeLoginFormDTO.getEmployeeid();
        String scannerid = employeeLoginFormDTO.getAssignment();
        String password=employeeLoginFormDTO.getPassword();
        Employees employee;
        //验证是否为空
        if(id==null||scannerid==null||password==null) {
            //返回错误信息
            return Result.fail("登录信息不完整，请重试");
        }
        //验证是否已经注册
        employee=query().eq("employeeid",id).one();
        if(employee==null) {
            //否，返回错误信息
            return Result.fail("用户未注册");
        }
        //验证密码是否正确
        if(!employee.getPassword().equals(password)) {
            //否，返回错误信息
            return Result.fail("密码错误，请重试");
        }
        //验证是否为合法机器号码
        if(!scannersService.is_assignable(scannerid)) {
            //否，返回错误信息
            return Result.fail("当前扫管器已经分配，或者扫管器id错误");
        }
        //设置机器已经分配
        if(!scannersService.set_assign(scannerid,id)){
            return Result.fail("配置错误");
        }
        //设置对象已经分配
        employee.setAssignsstatus("occupied");
        //设置对象分配机器号
        employee.setAssignment(scannerid);
        //更新数据库
        updateById(employee);
        //使用uuid生成token
        String token= UUID.randomUUID().toString();
        //将对象转为map
        Map<String,Object> employeemap = BeanUtil.beanToMap(employee);
        //用uuid为key将map存入redis中
        stringRedisTemplate.opsForHash().putAll(EMPLOYEE_LOGIN_TOKEN+ token , employeemap);
        //设置有效期,1000h
        stringRedisTemplate.expire(EMPLOYEE_LOGIN_TOKEN + token ,EMPLOYEE_LOGIN_TIME, TimeUnit.HOURS);
        //返回token
        return Result.ok(token);
    }

    @Override
    public Result sign_in( EmployeeLoginFormDTO employeeLoginFormDTO, HttpSession session) {
        //提取信息
        String id = employeeLoginFormDTO.getEmployeeid();
        String password = employeeLoginFormDTO.getPassword();
        //验证信息是否完整
        if(id==null||password==null) {
            //不符合，返回错误信息
            return Result.fail("信息不完整，请重试");
        }
        Employees employee;
        //查询是否存在用户
        employee=query().eq("employeeid",id).one();
        if(employee!=null) {
            //存在，返回错误信息
            return Result.fail("用户已经存在，无需重复注册");
        }else {
            //新建用户，存入表中
            employee=new Employees();
            employee.setEmployeeid(id);
            employee.setPassword(password);
            employee.setAssignsstatus("free");
            save(employee);
        }
        //返回成功信息
        return Result.ok();
    }

    @Transactional//这个注解表示开始事务
    @Override
    public Result logout(EmployeeLogoutDTO employeeLogoutDTO) {

        //提取用户
        Employees employee = query().eq("employeeid",employeeLogoutDTO.getEmployeeid()).one();
        //不存在，返回错误信息
        if(employee==null){return Result.fail("传入的用户id错误，请重试");}
        //提取用户分配扫管
        String scannerid=employee.getAssignment();
        //重设扫管分配状态
        scannersService.clean_assign(scannerid);
        //设置employee为free
        employee.setAssignsstatus("free");
        //向sql存储这个用户的更改
        updateById(employee);
        //将缓存设为过期
        stringRedisTemplate.expire(EMPLOYEE_LOGIN_TOKEN + employeeLogoutDTO.getToken() ,0L, TimeUnit.SECONDS);
        return Result.ok();
    }
}

