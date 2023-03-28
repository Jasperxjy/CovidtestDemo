package com.Covidtest.controller;

import com.Covidtest.dto.MedicalLoginDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.entity.Medicalfacility;
import com.Covidtest.service.MedicalfacilityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/medical")
public class MedicalfacilityController {
    @Resource
    private MedicalfacilityService medicalfacilityService;

    @GetMapping("/login")
    public Result login(@RequestBody MedicalLoginDTO medicalLoginDTO, HttpSession session){
        return  medicalfacilityService.login(medicalLoginDTO,session);
    }
    @PostMapping("/sign_in")
    public Result sign_in(@RequestBody Medicalfacility medicalfacility,HttpSession session){
        return  medicalfacilityService.sign_in(medicalfacility,session);
    }
    @PostMapping("/setinfo")
    public Result set_info(@RequestBody Medicalfacility medicalfacility,HttpSession session){
        return medicalfacilityService.set_info(medicalfacility,session);
    }

}
