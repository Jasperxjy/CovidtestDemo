package com.Covidtest.service;

import com.Covidtest.dto.MedicalLoginDTO;
import com.Covidtest.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Covidtest.entity.Medicalfacility;

import javax.servlet.http.HttpSession;

/**
 * (Medicalfacility)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 22:08:49
 */
public interface MedicalfacilityService extends IService<Medicalfacility> {

    Result login(MedicalLoginDTO medicalLoginDTO, HttpSession session);

    Result sign_in(Medicalfacility medicalfacility, HttpSession session);

    Result set_info(Medicalfacility medicalfacility, HttpSession session);
}

