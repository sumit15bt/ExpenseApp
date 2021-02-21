package com.expense.jwt.api.service;

import com.expense.jwt.api.beans.Expense;
import com.expense.jwt.api.repository.Expensedao;
import com.expense.jwt.api.repository.MerchantDao;
import com.expense.jwt.api.repository.Servicedao;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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



    public JSONArray expenseSummary(String extenseDate){
        JSONArray allExpenseSummary = new JSONArray();
        try{
            List<Expense> expenses=expensedao.getExpenseList(null,null,null,extenseDate); //yyyy-MM-dd format
            if(expenses!=null && expenses.size()>0){
                Map<Integer,String> servicemap=servicedao.getServiceIdNameMap();
                Map<Integer,String> merchnatmap=merchantDao.getMerchantIdNameMap();
                expenses.forEach((expensee) -> {
                    JSONObject expense = new JSONObject();
                    expense.put("title", expensee.getTitle());
                    expense.put("merchantname", merchnatmap.getOrDefault(expensee.getMerchantid(), "MERCHANT NOT AVAILABLE"));
                    expense.put("servicename", servicemap.getOrDefault(expensee.getServiceid(), "SERVICE NOT AVAILABLE"));
                    expense.put("cost",expensee.getCost());
                    long diff = new Date().getTime() - expensee.getExpenseentrydate().getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) ;
                    long diffHours = diff / (60 * 60 * 1000) ;
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    if(diffHours >= 24l){
                        expense.put("time",diffDays+" days");
                    }else if(diffMinutes >= 60l){
                        expense.put("time",diffHours+" hours");
                    }else if(diffSeconds >= 60l){
                        expense.put("time",diffMinutes+" minutes");
                    }else {
                        expense.put("time",diffSeconds+" hours");
                    }
                    allExpenseSummary.put(expense);
                });
            }else{
                allExpenseSummary.put("NO Expense Found !!");
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return allExpenseSummary;
    }
}
