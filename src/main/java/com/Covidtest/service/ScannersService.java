package com.Covidtest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Covidtest.entity.Scanners;

/**
 * (Scanners)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 22:09:44
 */
public interface ScannersService extends IService<Scanners> {

    public boolean is_assignable(String scannerid);

    public boolean set_assign(String scannerid,String assignedid);

    public boolean clean_assign(String scannerid);

}

