package com.expense.jwt.api;


//import com.expense.jwt.api.entity.User;
//import com.expense.jwt.api.repository.UserRepository;
import com.expense.jwt.api.beans.User;
import com.expense.jwt.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
public class ExpenseApp {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User(102, "ksheerdham", "ksheerdham.it", "ksheerdham.it@gmail.com"),
                new User(104, "ExpenseApp", "ExpenseApp@123", " expenseapp9123@gmail.com"),
                new User(104, "sumit", "sumit", "15btcse065@gmail.com")
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(ExpenseApp.class, args);
    }

}
