package com.Covidtest.utils;

import org.springframework.stereotype.Component;
import com.Covidtest.dao.UsersDao;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FaceBackUtils {

    @Resource
    private UsersDao usersDao;
    public List<String> get_all_ids() {

       return usersDao.selectAllid();
    }
}
