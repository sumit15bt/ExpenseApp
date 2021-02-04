package com.expense.jwt.api.controller;
import com.expense.jwt.api.beans.AuthRequest;
import com.expense.jwt.api.beans.Merchant;
//import com.expense.jwt.api.entity.User;
//import com.expense.jwt.api.repository.UserRepository;
import com.expense.jwt.api.repository.UserRepository;
import com.expense.jwt.api.service.MerchantService;
import com.expense.jwt.api.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
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
        log.info("get_merchant------------------->");
        Merchant merchant=null;
        try {
            merchant=merchantService.getMerchantDetails(name);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return merchant;
    }

    @PostMapping("/createMerchant")
    public String createMerchant(@RequestBody Merchant merchantDetailBean ) throws InterruptedException, ExecutionException {
        log.info("create_merchant-------------------> {}",merchantDetailBean.toString());
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=merchantService.saveMerchantDetails(merchantDetailBean);
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
        return response;
    }

    @PutMapping("/updateMerchant")
    public String updateMerchant(@RequestBody Merchant merchant) throws InterruptedException, ExecutionException {
        System.out.println("update_merchant_Details------------------->");
        log.trace("update_merchant_Details------------------->");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=merchantService.updateMerchantDetails(merchant);
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
        return response;
    }

    @DeleteMapping("/deleteMerchant")
    public String deleteMerchant(@RequestParam String name){
        return merchantService.deleteMerchant(name);
    }
}
