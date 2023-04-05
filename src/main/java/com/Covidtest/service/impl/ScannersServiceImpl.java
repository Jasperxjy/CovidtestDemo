package com.Covidtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.ScannersDao;
import com.Covidtest.entity.Scanners;
import com.Covidtest.service.ScannersService;
import org.springframework.stereotype.Service;

/**
 * (Scanners)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:09:44
 */
@Service("scannersService")
public class ScannersServiceImpl extends ServiceImpl<ScannersDao, Scanners> implements ScannersService {

    /**
     * 查询扫管器是否可分配
     * @param scannerid 扫管器分配id
     * @return true：可分配；false：不可分配，或者为不存在的扫管器id
     */
    @Override
    public boolean is_assignable(String scannerid) {
        //查询设备数据
        Scanners scanner = query().eq("scannerid",scannerid).one();
        //检查是否查到
        if(scanner==null) {
            //否，返回false
            return false;
        }
        //检查分配状态为可用,是返回true，否返回false
        return scanner.getAssignstatus().equals("free");
    }

    /**
     * 设置扫管器分配终端
     * @param scannerid 扫管器id
     * @param assignedid 分配的终端的id
     * @return true：分配成功；false：出现非法扫管器id
     */
    @Override
    public boolean set_assign(String scannerid, String assignedid) {
        //查询设备数据
        Scanners scanner= query().eq("scannerid",scannerid).one();
        //检查是否查到
        if(scanner==null) {
            //否，返回false
            return false;
        }
        //设置分配对象和分配状态
        scanner.setAssignedid(assignedid);
        scanner.setAssignstatus("occupied");
        //更新数据库
        updateById(scanner);
        //返回true
        return true;
    }

    /**
     * 将指定扫管器的分配状态清空，设置扫管器为未分配
     * @param scannerid 扫管器id
     * @return true：清空成功；false：出现非法扫管器id
     */
    @Override
    public boolean clean_assign(String scannerid) {
        //查询设备数据
        Scanners scanner= query().eq("scannerid",scannerid).one();
        //检查是否查到
        if(scanner==null) {
            //否，返回false
            return false;
        }
        //设置分配对象为空，设置分配状态为可用
        scanner.setAssignstatus("free");
        scanner.setAssignedid("");
        //更新数据库
        updateById(scanner);
        //返回true
        return true;
    }
}

