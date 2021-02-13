package com.expense.jwt.api.controller;
import com.expense.jwt.api.beans.AuthRequest;
import com.expense.jwt.api.beans.MServices;
import com.expense.jwt.api.beans.Merchant;
import com.expense.jwt.api.repository.MerchantDao;
import com.expense.jwt.api.repository.Servicedao;
import com.expense.jwt.api.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/getMerchantServices")
    public String getMerchantDetails(@RequestParam("merchantName") String merchantName) throws InterruptedException, ExecutionException{
        log.info("get_merchant-------------------> {} ",merchantName);
        String allServices="";
        try {
            allServices=servicedao.getService(null,merchantName);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(allServices.toString());
        return allServices;
    }

    @GetMapping("/getAllMerchantANDServices")
    public Map<String, List<MServices>> getAllMerchantANDServices() {
        log.info("getAllMerchantANDServices-------------------> {} ");
        Map<String, List<MServices>> allServices=new HashMap();
        try {
            allServices=merchantDao.getAllMerchantAndServices();
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(allServices.toString());
        return allServices;
    }

    @GetMapping("/getAllMerchants")
    public String getAllMerchants() {
        log.info("getAllMerchantANDServices-------------------> {} ");
        String allMerchant= "";
        try {
            allMerchant=merchantDao.getAllMerchants();
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return allMerchant;
    }

    @PostMapping("/createMerchant")
    public String createMerchant(@RequestBody Merchant merchantDetailBean ) throws InterruptedException, ExecutionException {
        log.info("create_merchant-------------------> {}",merchantDetailBean.toString());
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=merchantDao.createMerchant(merchantDetailBean);
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
        return response;
    }

    @PostMapping("/updateMerchant")
    public String updateMerchant(@RequestBody Merchant merchantDetailBean) throws InterruptedException, ExecutionException {
        log.trace("update_merchant_Details------------------->");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=merchantDao.updateMerchant(merchantDetailBean);
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
        return response;
    }

    @GetMapping("/deleteMerchant")
    public String deleteMerchant(@RequestParam("merchantName") String merchantName){
        log.info("--------------/deleteMerchant-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=merchantDao.deleteMerchant(merchantName);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }

    // TODO GET MERCHANT DETAIL PARTICULARLY AND ALL

}
