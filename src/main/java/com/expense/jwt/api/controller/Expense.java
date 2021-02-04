package com.expense.jwt.api.controller;

import com.expense.jwt.api.beans.ExpenseDetail;
import com.expense.jwt.api.service.ExpenseServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Expense {

    @Autowired
    ExpenseServices expenseServices;

    @PostMapping("/addExpense")
    public String addExpense(@RequestBody ExpenseDetail expenseDetail){
        log.info("--------------/addExpense-----------");
        String response="SOMETHING WENT WRONG ..!";
        try{
            response=expenseServices.addExpense(expenseDetail);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        log.trace(response);
        return response;
    }
}
