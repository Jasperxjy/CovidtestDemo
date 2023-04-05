package com.Covidtest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.Covidtest.dao.UsersDao;
import com.Covidtest.dto.*;
import com.Covidtest.entity.Users;
import com.Covidtest.service.UsersService;
import com.Covidtest.utils.RegexUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.Covidtest.utils.PythonPathConstant.*;
import static com.Covidtest.utils.RedisConstants.LOGIN_LIMIT_TIME;
import static com.Covidtest.utils.RedisConstants.LOGIN_TOKEN;

/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */

@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, Users> implements UsersService {

    //引入redis组件
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(UserLoginFormDTO loginForm, HttpSession session) {
        //TODO 实现登录功能
        String id=loginForm.getId();
        String phone=loginForm.getPhone();
        Users user;
        //判断是否有电话
        if(phone==null){
            //校验身份证
            if(RegexUtils.isIDInvalid(id)){
                //不合法，返回错误信息
                return Result.fail("身份证格式错误，请重新输入");
        }
             //合法，根据身份证查找用户
             user = query().eq("id",id).one();
        }else {
            //校验电话
            if(RegexUtils.isPhoneInvalid(phone)){
                //不合法，返回错误信息
                return Result.fail("电话号格式错误，请重新输入");
            }
            //合法，根据电话号查找用户
            user = query().eq("phonenum",phone).one();
        }
        //判断用户是否存在
        if (user==null){
            //不存在，返回信息
            return Result.fail("用户不存在，请重新输入或注册");
        }
        //存在，校验密码
        if(user.getPassword().equals(loginForm.getPassword())) {
            //密码正确，保存用户信息到redis中

            //使用自带的UUID随机生成token，作为登录令牌
            String token= UUID.randomUUID().toString();
            //将user转为hash存储,后面一个数据将users转为userDTO，实际上是将user的人脸、密码等敏感信息隐去，减小线程池中的内存开支，并提高安全性
            UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
            //将user转成哈希
            Map<String,Object> userMap = BeanUtil.beanToMap(userDTO);
            //存储,注意如果以后userMap中存储了有非string的字段会报错，需要找解决方法
            stringRedisTemplate.opsForHash().putAll(LOGIN_TOKEN + token , userMap);
            //设置有效期
            stringRedisTemplate.expire(LOGIN_TOKEN + token ,LOGIN_LIMIT_TIME, TimeUnit.MINUTES);
            //返回token
            return Result.ok(token);
        }else {
        //密码错误，返回信息
            return Result.fail("密码错误，请重新输入");
        }
    }

    @Override
    public Result sign_In(UserSignFormDTO signForm, HttpSession session) {

        //TODO 实现注册功能
        //取得各个数据
        String id = signForm.getId();
        String name = signForm.getUsername();
        String passwd_1 = signForm.getPassword_1();
        String passwd_2 = signForm.getPassword_2();
        String phone = signForm.getPhone();
        //判断名字、身份证、密码、确认密码是否都有
        if(id==null||name==null||passwd_1==null||passwd_2==null){
        //否，返回错误信息
            return Result.fail("必要信息不全，请重新输入");
        }
        //判断密码与确认密码是否相同
        if(!passwd_1.equals(passwd_2)) {
            //否，返回错误信息
            return Result.fail("两次输入密码不一致，请重新输入");
        }
        //判断身份证是否符合格式
        if(RegexUtils.isIDInvalid(id)) {
            //否，返回错误信息
            return Result.fail("输入的身份证格式错误，请重新输入");
        }
        //如果手机号有，判断是否符合格式
        if(phone!=null&&RegexUtils.isPhoneInvalid(phone)){
            //否，返回错误信息
            return  Result.fail("输入的手机号格式有误，请重新输入");
        }
        Users user= query().eq("id", id).one();
        //判断身份证号是否没有注册
        if(user!=null) {
            //否，返回错误信息
            return Result.fail("该用户已经注册");
        }
        //保存各个信息
        Users newuser = new Users();
        newuser.setId(id);
        newuser.setUsername(name);
        newuser.setPassword(passwd_1);
        if(phone!=null){
            newuser.setPhonenum(phone);
        }
        save(newuser);
        //返回注册成功
        return Result.ok();
    }

    @Override
    public Result get_feature_by_id(String id) {
        Users user;
        user = query().eq("id",id).one();
        if(user==null){
            return Result.fail("用户为空");
        }else{
            if(user.getFaceinfoReveal()==null){
                return Result.fail("没有设置面容");
            }
        return Result.ok(query().eq("id",id).one().getFaceinfoReveal());
        }
    }

    @SneakyThrows  //将线程运行中的异常转为隐式异常
    @Override
    public Result get_id_byface(GetidByFaceDTO getidByFaceDTO, HttpSession session) {
        //将图片转为byte[]
        List<byte[]> imgs_byte = new ArrayList<>();
        for(String img :getidByFaceDTO.getFaceimgs()){
            byte[] img_byte = Base64.getDecoder().decode(img);
            if(img_byte!=null){
                imgs_byte.add(img_byte);
            }
        }
        if(imgs_byte.isEmpty()){
            return Result.fail("错误：未传入图片");
        }
        //将byte[]转换为json
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode imagesNode = mapper.createArrayNode();
        for (int i = 0; i < imgs_byte.size(); i++) {
            ObjectNode imageNode = mapper.createObjectNode();
            imageNode.put("name", "image" + i + ".jpg");
            imageNode.put("data", Base64.getEncoder().encodeToString(imgs_byte.get(i)));
            imagesNode.add(imageNode);
        }
        String imgs_jsonData;
        imgs_jsonData = mapper.writeValueAsString(imagesNode);
        //配置python脚本
        String[] command = new String[] {"python",FACE_RECOG_PYPATH};
        //新建进程
        ProcessBuilder pb = new ProcessBuilder(command);
        //为每个进程分配独立的输入和输出流
        pb.redirectInput(ProcessBuilder.Redirect.PIPE);
        pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
        //启动进程
        Process process  = pb.start();
        // 获取输入和输出流
        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();
        // 将图片信息写入python脚本进程
        stdin.write(imgs_jsonData.getBytes());
        //清空输入缓存
        stdin.flush();
        stdin.close();
        // 读取输出数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        String outputData = reader.readLine();
        //输出符合格式
        if(!RegexUtils.isPyResIdInvalid(outputData)){
            //获取字串，为id
            String id = outputData.substring(FACE_RECOG_OK_HEAD.length());
            //去除杂余部分
            id = id.trim();
            //从数据库中获取用户
            Users users = query().eq("id",id).one();
            //如果不为空
            if(users!=null) {
                //将用户敏感信息隐藏后返回到前端
                UserDTO userDTO = BeanUtil.copyProperties(users, UserDTO.class);
                return Result.ok(userDTO);
            }
            return  Result.fail("位置的异常：python返回的id无法查询到用户");
        }else{
            //获取标准错误流
            InputStream stderr = process.getErrorStream();
            //读取标准错误流
            BufferedReader errReader = new BufferedReader(new InputStreamReader(stderr));
            StringBuilder errLine = new StringBuilder();
            //将错误输出添加到errLine
            errLine.append("返回信息错误，错误原因：\n");
            //将所有错误信息拼接
            while (errReader.readLine()!= null) {
                // 处理标准错误流中的每一行输出
                errLine.append(errReader.readLine());
            }
            // 等待进程执行完毕
            process.waitFor();
            //返回错误信息
            return Result.fail(errLine.toString());
        }
    }
    @SneakyThrows  //将线程运行中的异常转为隐式异常
    @Override
    public Result set_feature(SetFeatureDTO setFeatureDTO, HttpSession session) {
        //将图片转为byte[]
        List<byte[]> imgs_byte = new ArrayList<>();
        for(String img :setFeatureDTO.getImgs()){
                byte[] img_byte = Base64.getDecoder().decode(img);
                if(img_byte!=null){
                    imgs_byte.add(img_byte);
                }
        }
        if(imgs_byte.isEmpty()){
            return Result.fail("错误：未传入图片");
        }
        //将byte[]转换为json
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode imagesNode = mapper.createArrayNode();
        for (int i = 0; i < imgs_byte.size(); i++) {
            ObjectNode imageNode = mapper.createObjectNode();
            imageNode.put("name", "image" + i + ".jpg");
            imageNode.put("data", Base64.getEncoder().encodeToString(imgs_byte.get(i)));
            imagesNode.add(imageNode);
        }
        String imgs_jsonData;
        imgs_jsonData = mapper.writeValueAsString(imagesNode);
        //配置特征提取python脚本
        String[] command = new String[] {"python",FACE_FEATURE_PYPATH};
        //新建进程
        ProcessBuilder pb = new ProcessBuilder(command);
        //为每个进程分配独立的输入和输出流
        pb.redirectInput(ProcessBuilder.Redirect.PIPE);
        pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
        //启动进程
        Process process  = pb.start();
        // 获取输入和输出流
        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();
        // 将图片信息写入python脚本进程
        stdin.write(imgs_jsonData.getBytes());
        //清空输入缓存
        stdin.flush();
        stdin.close();
        // 读取输出数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        String outputData = reader.readLine();
        //输出符合格式
        if(!RegexUtils.isPyResFeatureInvalid(outputData)){
            //获取字串作为向量
            String feature = outputData.substring(FACE_FEATURE_PYPATH.length());
            //查看字符串是否为空
            if(!feature.isEmpty()){
                //不为空，去除杂余部分
                feature = feature.trim();
                //查找用户
                Users users= query().eq("id",setFeatureDTO.getId()).one();
                //判断是否为空
                if(users!=null) {
                    //不为空，设置用户的特征向量
                    users.setFaceinfoReveal(feature.getBytes());
                //更新用户信息
                updateById(users);
                //返回成功
                return Result.ok();
                }else {
                    return Result.fail("未知的异常：传入的id查找不到用户");
                }
            }
            return Result.fail("未知的异常：python返回的特征向量为空");
        }else{
            //获取标准错误流
            InputStream stderr = process.getErrorStream();
            //读取标准错误流
            BufferedReader errReader = new BufferedReader(new InputStreamReader(stderr));
            StringBuilder errLine = new StringBuilder();
            //将错误输出添加到errLine
            errLine.append("返回信息错误，错误原因：\n");
            //将所有错误信息拼接
            while (errReader.readLine()!= null) {
                // 处理标准错误流中的每一行输出
                errLine.append(errReader.readLine());
            }
            // 等待进程执行完毕
            process.waitFor();
            //返回错误信息
            return Result.fail(errLine.toString());
        }
    }
}

