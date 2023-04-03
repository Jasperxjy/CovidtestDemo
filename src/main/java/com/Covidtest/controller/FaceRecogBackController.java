package com.Covidtest.controller;

import com.Covidtest.service.UsersService;
import com.Covidtest.utils.FaceBackUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/face_DT")
public class FaceRecogBackController {
    @Resource
    private FaceBackUtils faceBackUtils;
    @Resource
    private UsersService usersService;

    /**
     * 返回所有用户id，虽然这并不安全，但是目前使用这个方法调用
     * @return 所有用户的id的list
     */
    @GetMapping("/ids")
    public List<String> get_all_ids(){
        return faceBackUtils.get_all_ids();
    }

    /**
     * 直接使用数据库，查找传入的id的人脸特征向量
     * @param id 身份证
     * @return 人脸特征向量
     */
    @GetMapping("/get_feature")
    public byte[] get_feature_by_id(@RequestBody String id){
        return usersService.get_feature_by_id(id);
    }

}
