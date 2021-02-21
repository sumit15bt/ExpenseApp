package com.expense.jwt.api.controller;

import com.expense.jwt.api.beans.Expense;
import com.expense.jwt.api.beans.RequestBean;
import com.expense.jwt.api.repository.Expensedao;
import com.expense.jwt.api.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ExpenseController {

    @Autowired
    private Expensedao expensedao;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/addExpense")
    public String addExpense(@RequestBody Expense expenseDetail){
        log.info("--------------/addExpense-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=expensedao.createExpense(expenseDetail);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }

    @PostMapping("/updateExpense")
    public String updateExpense(@RequestBody Expense expenseDetail){
        log.info("--------------/addExpense-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=expensedao.updateExpense(expenseDetail);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }

    @PostMapping("/deleteExpense")
    public String updateExpense(@RequestBody RequestBean bean){
        log.info("--------------/deleteExpense-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=expensedao.deleteExpense(bean.getExpenseTitle());
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }

    @PostMapping("/getExpenseDetail")
    public String getExpense(@RequestBody RequestBean bean){
        log.info("--------------/deleteExpense-----------");
        JSONObject response=new JSONObject();
        try{
            response=response.put("expenses",expensedao.getExpense(bean.getExpenseTitle(),null,null,null));
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response.toString();
    }

    @PostMapping("/getExpenseSummary") ////yyyy-MM-dd format
    public String getExpenseSummary(@RequestBody RequestBean bean){
        log.trace("getExpenseSummary date -> {}",bean.getDate());
        JSONObject response=new JSONObject();
        return response.put("expenseSummary",expenseService.expenseSummary(bean.getDate())).toString();
    }

    @GetMapping("/getAllExpense")
    public String getAllExpense(){
        log.info("--------------/getAllExpense-----------");
        JSONObject allExpense=new JSONObject();
        try{
            allExpense.put("expenses",expensedao.getExpense(null,null,null,null));
        }catch (Exception ex){
            log.error("ERROR : {}",ex);
        }
        return allExpense.toString();
    }
}
