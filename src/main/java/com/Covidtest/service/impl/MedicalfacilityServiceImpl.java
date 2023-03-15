package com.Covidtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.MedicalfacilityDao;
import com.Covidtest.entity.Medicalfacility;
import com.Covidtest.service.MedicalfacilityService;
import org.springframework.stereotype.Service;

/**
 * (Medicalfacility)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:08:49
 */
@Service("medicalfacilityService")
public class MedicalfacilityServiceImpl extends ServiceImpl<MedicalfacilityDao, Medicalfacility> implements MedicalfacilityService {

}

