package com.Covidtest.dto;

import lombok.Data;
import com.Covidtest.entity.Register;

import java.time.format.DateTimeFormatter;

@Data
public class RegisterDTO {

    //身份证
    private String id;
    //核酸单号
    private String naid;
    //采样时间
    private String registime;
    //管号
    private String groupT;



    public RegisterDTO(Register register) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.registime = register.getRegistime().format(fmt);
        this.groupT=register.getGroupT();
        this.naid=register.getNaid();
        this.id=register.getId();
    }
    public RegisterDTO(){
        this.id="";
        this.registime="";
        this.naid="";
        this.groupT="";
    }


}
