package com.expense.jwt.api.controller;
import com.expense.jwt.api.beans.*;
import com.expense.jwt.api.repository.MerchantDao;
import com.expense.jwt.api.repository.Servicedao;
import com.expense.jwt.api.util.JwtUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class MerchantController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private Servicedao servicedao;


    @GetMapping("/")
    public String welcome() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        return "Welcome to BACKEND API !!";
    }

    @GetMapping("/testapi")
    public String testapi() {
        return "BACKEND API working fine !!";
    }

    //    {"userName": "15btcse065@gmail.com",
//            "password":"sumit"}
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        log.trace("----------------authenticate------------------");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    @PostMapping("/getMerchantServices")
    public String getMerchantDetails(@RequestBody RequestBean bean) throws InterruptedException, ExecutionException {
        log.info("get_merchant_services-------------------> {} ", bean.getMerchantName());
        JSONObject allServices=new JSONObject();
        try {
            allServices.put("merchantServices",servicedao.getService(null, bean.getMerchantName()));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.trace(allServices.toString());
        return allServices.toString();
    }

    @GetMapping("/getAllMerchantANDServices")
    public String getAllMerchantANDServices() {
        log.info("getAllMerchantANDServices-------------------> {} ");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("merchantANDServices",merchantDao.getAllMerchantAndServices());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return jsonObject.toString();
    }

    @GetMapping("/getAllMerchants")
    public String getAllMerchants() {
        log.info("getAllMerchantANDServices-------------------> {} ");
        JSONObject jsonObject=new JSONObject();
        JSONArray merchantObjectAll = new JSONArray();
        try {
            List<Merchant> allMerchants = merchantDao.getAllMerchants();
            for (Merchant merchant : allMerchants) {
                JSONObject merchantObject = new JSONObject();
                merchantObject.put("id", merchant.getId());
                merchantObject.put("name", merchant.getName());
                merchantObject.put("primarycontactno", merchant.getPrimarycontactno());
                merchantObject.put("secondarycontactno", merchant.getSecondarycontactno());
                merchantObject.put("gstno", merchant.getGstno());
                merchantObject.put("address", merchant.getAddress());
                merchantObject.put("state", merchant.getState());
                merchantObject.put("cityname", merchant.getCityname());
                merchantObject.put("pincode", merchant.getPincode());
                merchantObject.put("accountholdername", merchant.getAccountholdername());
                merchantObject.put("accountno", merchant.getAccountno());
                merchantObject.put("bankname", merchant.getBankname());
                merchantObject.put("ifsccode", merchant.getIfsccode());
                merchantObject.put("entrydate", merchant.getEntrydate());
                merchantObject.put("lastupdated", merchant.getLastupdated());
                merchantObjectAll.put(merchantObject);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.trace(merchantObjectAll.toString());
        jsonObject.put("merchants",merchantObjectAll);
        return jsonObject.toString();
    }

    @PostMapping("/createMerchant")
    public String createMerchant(@RequestBody Merchant merchantDetailBean) throws InterruptedException, ExecutionException {
        log.info("create_merchant-------------------> {}", merchantDetailBean.toString());
        String response = "SOMETHING WENT WRONG ..!";
        try {
            response = merchantDao.createMerchant(merchantDetailBean);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return response;
    }

    @PostMapping("/updateMerchant")
    public String updateMerchant(@RequestBody Merchant merchantDetailBean) throws InterruptedException, ExecutionException {
        log.trace("update_merchant_Details------------------->");
        String response = "SOMETHING WENT WRONG ..!";
        try {
            response = merchantDao.updateMerchant(merchantDetailBean);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return response;
    }

    @PostMapping("/deleteMerchant")
    public String deleteMerchant(@RequestBody RequestBean bean) {
        log.info("--------------/deleteMerchant-----------");
        String response = "SOMETHING WENT WRONG ..!";
        try {
            response = merchantDao.deleteMerchant(bean.getMerchantName());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }
}