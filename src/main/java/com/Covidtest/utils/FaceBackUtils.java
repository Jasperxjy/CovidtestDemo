package com.Covidtest.utils;

import com.Covidtest.dto.Result;
import org.springframework.stereotype.Component;
import com.Covidtest.dao.UsersDao;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FaceBackUtils {

    @Resource
    private UsersDao usersDao;
    public Result get_all_ids() {
       return Result.ok(usersDao.selectAllid());
    }
}
