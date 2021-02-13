package com.expense.jwt.api.service;

import com.expense.jwt.api.beans.Expense;
import com.expense.jwt.api.repository.Expensedao;
import com.expense.jwt.api.repository.MerchantDao;
import com.expense.jwt.api.repository.Servicedao;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class ExpenseService {

    @Autowired
    Expensedao expensedao;

    @Autowired
    private Servicedao servicedao;

    @Autowired
    private MerchantDao merchantDao;



    public String expenseSummary(String extenseDate){
        JsonArray allExpenseSummary = new JsonArray();
        try{
            List<Expense> expenses=expensedao.getExpenseList(null,null,null,extenseDate); //yyyy-MM-dd format
            if(expenses!=null && expenses.size()>0){
                Map<Integer,String> servicemap=servicedao.getServiceIdNameMap();
                Map<Integer,String> merchnatmap=merchantDao.getMerchantIdNameMap();
                expenses.forEach((expensee) -> {
                    JsonObject expense = new JsonObject();
                    expense.addProperty("title", expensee.getTitle());
                    expense.addProperty("merchantname", merchnatmap.getOrDefault(expensee.getMerchantid(), "MERCHANT NOT AVAILABLE"));
                    expense.addProperty("servicename", servicemap.getOrDefault(expensee.getServiceid(), "SERVICE NOT AVAILABLE"));
                    expense.addProperty("cost",expensee.getCost());
                    JsonObject timeDifference = new JsonObject();
                    long diff = new Date().getTime() - expensee.getExpenseentrydate().getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    timeDifference.addProperty("days",diffDays);
                    timeDifference.addProperty("Hours",diffHours);
                    timeDifference.addProperty("minutes",diffMinutes);
                    timeDifference.addProperty("seconds",diffSeconds);
                    expense.addProperty("timeDifference",timeDifference.toString());
                    allExpenseSummary.add(expense);
                });
            }else{
                allExpenseSummary.add("NO Expense Found !!");
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return allExpenseSummary.toString();
    }
}
