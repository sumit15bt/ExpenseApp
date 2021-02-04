package com.expense.jwt.api.controller;

import com.expense.jwt.api.beans.MerchantServiceBean;
import com.expense.jwt.api.service.Merchant_Services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class MerchantServices {

    @Autowired
    Merchant_Services merchantServices;

    @GetMapping("/addService")
    public String addService() {
        log.info("-------addService hitted -----------");
        return "BACKEND API working fine !!";
    }

    @PostMapping("/addMerchantService")
    public String addMerchantService(@RequestBody MerchantServiceBean merchantServiceBean) throws ExecutionException, InterruptedException {
        log.info("-------addService hitted ----------- {}",merchantServiceBean);
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=merchantServices.addMerchantService(merchantServiceBean);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }



}
