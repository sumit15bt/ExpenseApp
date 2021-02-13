package com.expense.jwt.api;


//import com.expense.jwt.api.entity.User;
//import com.expense.jwt.api.repository.UserRepository;
import com.expense.jwt.api.beans.KasherdhamUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.PostConstruct;


@SpringBootApplication
@Slf4j
public class ExpenseApp {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


//    @PostConstruct
//    public void initUsers() {
//        log.trace("init------------------>");
//        String insert="Insert INTO KasherdhamUser(username,email,password) VALUES ('ksheerdham','ksheerdham.it@gmail.com','ksheerdham.it')";
//        String insert2="Insert INTO KasherdhamUser(username,email,password) VALUES ('ksheerdham','expenseapp9123@gmail.com','ExpenseApp@123')";
//        int a=jdbcTemplate.update(insert);
//        int B=jdbcTemplate.update(insert2);
//        log.debug("a  ######################## {} {}",a,B);
//    }

    public static void main(String[] args) {
        SpringApplication.run(ExpenseApp.class, args);
    }

}
