package com.expense.jwt.api.controller;

import com.expense.jwt.api.beans.Expense;
import com.expense.jwt.api.beans.MServices;
import com.expense.jwt.api.beans.RequestBean;
import com.expense.jwt.api.repository.Expensedao;
import com.expense.jwt.api.repository.MerchantDao;
import com.expense.jwt.api.repository.Servicedao;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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

    @PostMapping("/deleteService")
    public String deleteService(@RequestBody RequestBean bean){
        log.info("--------------/deleteService-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=servicedao.deleteService(bean.getServiceName());
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }

    @PostMapping("/getExpensesOfService")
    public String getExpensesOfService(@RequestBody RequestBean bean){
        JSONObject jsonObject=new JSONObject();
        return jsonObject.put("expenses",expensedao.getExpense(null,bean.getServiceName(),null,null)).toString();
    }

    @PostMapping("/getServiceDetail")
    public String getExpense(@RequestBody RequestBean bean){
        log.info("--------------/deleteExpense-----------");
        JSONObject response=new JSONObject();
        try{
            response=response.put("services",servicedao.getService(bean.getServiceName(),null));
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response.toString();
    }

    @GetMapping("/getAllService")
    public String getAllExpense(){
        log.info("--------------/deleteExpense-----------");
        JSONObject response=new JSONObject();
        try{
            response.put("services",servicedao.getService(null,null));
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response.toString();
    }
}
