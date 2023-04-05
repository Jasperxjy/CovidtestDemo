package com.Covidtest.controller;

import com.Covidtest.dto.*;
import com.Covidtest.service.UsersService;
import com.Covidtest.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author 熊
 * 类型：用户类服务接口
 * 作用：向前端提供用户类方法接口
 * 时间：23.3.19
 */

//@Slf4j //生成日志
@RestController
@RequestMapping("/users")
public class UsersController {
    @Resource
    private UsersService usersService;

    /**
     * 登录功能
     * @param loginForm 登录参数，包括身份证和密码，@RequestBody注解用于处理前端json格式的数据
     * @param session 会话管理、数据存储、跨页面传递数据和安全验证的重要工具，将用户的标识信息
     *                输入信息等存储在 HttpSession 中
     * @return 登录服务
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginFormDTO loginForm, HttpSession session){
        //TODO 实现登录功能
        return usersService.login(loginForm,session);
    }

    /**
     * 注册功能
     * @param signForm  注册参数，包括身份证、姓名和密码、二次确认密码
     * @param session 同上
     * @return 注册服务
     */
    @PostMapping("/sign_in")
    public Result sign_In(@RequestBody UserSignFormDTO signForm,HttpSession session){
        //TODO 实现注册功能
        return usersService.sign_In(signForm,session);
    }

    /**
     * 获取当前登录用户对象信息
     * @return 当前登录用户对象
     */
    @GetMapping ("/me")
    public Result me(){
        //获取当前登录用户并返回
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    /**
     * 通过前端传入的图片返回用户身份信息
     * @param getidByFaceDTO 传入的图片，格式为json，编码为base64
     * @param session 会话
     * @return 成功即返回用户信息，错误即返回错误信息
     */
    @GetMapping("/get_id_byface")
    public Result get_id_byface(@RequestBody GetidByFaceDTO getidByFaceDTO,HttpSession session){
        return usersService.get_id_byface(getidByFaceDTO,session);
    }

    /**
     * 通过前端传入的图片和身份信息设置用户人脸特征向量
     * @param setFeatureDTO 传入的图片和id，格式为json，编码为base64
     * @param session 会话
     * @return 成功返回ok，失败返回错误信息
     */
    @PostMapping("/set_feature")
    public Result set_feature(@RequestBody SetFeatureDTO setFeatureDTO,HttpSession session){
        return usersService.set_feature(setFeatureDTO,session);
    }

}
