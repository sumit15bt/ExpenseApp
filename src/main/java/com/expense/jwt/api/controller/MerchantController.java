package com.expense.jwt.api.controller;
import com.expense.jwt.api.beans.AuthRequest;
import com.expense.jwt.api.beans.Merchant;
//import com.expense.jwt.api.entity.User;
//import com.expense.jwt.api.repository.UserRepository;
import com.expense.jwt.api.beans.MerchantDetailBean;
import com.expense.jwt.api.repository.UserRepository;
import com.expense.jwt.api.service.MerchantService;
import com.expense.jwt.api.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@Log4j2
public class MerchantController {


    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MerchantService merchantService;

    @Autowired
    private UserRepository repository;

    @GetMapping("/")  // get all merchant
    public String welcome() {
//        List<User> allUser=repository.findAll();
//        System.out.println(allUser.toString());

        return "Welcome to BACKEND API !!";
    }

    @GetMapping("/testapi")
    public String testapi() {
        return "BACKEND API working fine !!";
    }

//    @PostMapping("/signup")
//    public String signup(@RequestBody User user) throws Exception {
//        log.trace("-----------------signup----------------");
//        String response="";
//        try {
//            User signUpUser=new User();
//            signUpUser.setUserName(user.getUserName());
//            signUpUser.setEmail(user.getEmail());
//            signUpUser.setPassword(user.getPassword()); // not hashing for now => to do later if told.
//            repository.save(user);
//            response="Sign up Successfully";
//        } catch (Exception ex) {
//            log.trace(ex.getMessage());
//            response="Something went wrong";
//        }
//        return response;
//    }


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

    @GetMapping("/getMerchantDetails")
    public Merchant getMerchantDetails(@RequestParam String name ) throws InterruptedException, ExecutionException{
        System.out.println("get_merchant------------------->");
        return merchantService.getMerchantDetails(name);
    }

    @PostMapping("/createMerchant")
    public String createMerchant(@RequestBody MerchantDetailBean merchantDetailBean ) throws InterruptedException, ExecutionException {
        System.out.println("create_merchant------------------->");
        log.trace("create_merchant------------------->");
        System.out.println(merchantDetailBean.toString());
//        return "created Merchant";
        return merchantService.saveMerchantDetails(merchantDetailBean);
    }

    @PutMapping("/updateMerchant")
    public String updateMerchant(@RequestBody Merchant patient  ) throws InterruptedException, ExecutionException {
        System.out.println("update_merchant_Details------------------->");
        log.trace("update_merchant_Details------------------->");
        return merchantService.updateMerchantDetails(patient);
    }

    @DeleteMapping("/deleteMerchant")
    public String deleteMerchant(@RequestParam String name){
        return merchantService.deleteMerchant(name);
    }
}
