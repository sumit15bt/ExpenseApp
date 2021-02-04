package com.expense.jwt.api.service;

import com.expense.jwt.api.beans.ExpenseDetail;
import com.expense.jwt.api.beans.MerchantServiceBean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class ExpenseServices {
    public static final String EXPENSE="ALLExpense";

    public String addExpense(ExpenseDetail expenseDetail) throws ExecutionException, InterruptedException {
        String response="SOMETHING WENT WRONG ..!";
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(EXPENSE).document(expenseDetail.getExpenseTitle()).set(expenseDetail);
            response=collectionsApiFuture.get().getUpdateTime().toString();
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
        log.trace(response);
        return response;
    }
}
