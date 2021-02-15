package com.expense.jwt.api.controller;

import com.expense.jwt.api.beans.Expense;
import com.expense.jwt.api.beans.MServices;
import com.expense.jwt.api.repository.Expensedao;
import com.expense.jwt.api.repository.MerchantDao;
import com.expense.jwt.api.repository.Servicedao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class MerchantServicesController {



    @Autowired
    private Servicedao servicedao;

    @Autowired
    private Expensedao expensedao;

    @PostMapping("/createService")
    public String createService(@RequestBody MServices merchantServiceBean) throws ExecutionException, InterruptedException {
        log.info("-------addService hitted ----------- {}",merchantServiceBean);
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=servicedao.createService(merchantServiceBean);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }

    @PostMapping("/updateService")
    public String updateService(@RequestBody MServices merchantServiceBean) throws ExecutionException, InterruptedException {
        log.info("-------addService hitted ----------- {}",merchantServiceBean);
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=servicedao.updateService(merchantServiceBean);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }

    @GetMapping("/deleteService")
    public String deleteService(@RequestParam("serviceName") String serviceName){
        log.info("--------------/deleteService-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=servicedao.deleteService(serviceName);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }

    @GetMapping("/getExpensesOfService")
    public String getExpensesOfService(@RequestParam("serviceName") String serviceName){
        return expensedao.getExpense(null,serviceName,null,null);
    }

    @GetMapping("/getServiceDetail")
    public String getExpense(@RequestParam("serviceName") String serviceName){
        log.info("--------------/deleteExpense-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=servicedao.getService(serviceName,null);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }

    @GetMapping("/getAllService")
    public String getAllExpense(){
        log.info("--------------/deleteExpense-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=servicedao.getService(null,null);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }
}
