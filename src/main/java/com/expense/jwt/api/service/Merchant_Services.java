package com.expense.jwt.api.service;

import com.expense.jwt.api.beans.MerchantServiceBean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class Merchant_Services {

    public static final String Merchant_Services_Collection="AllServices";

    public String addMerchantService(MerchantServiceBean merchantServiceBean) throws ExecutionException, InterruptedException {
        String response="SOMETHING WENT WRONG ..!";
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(Merchant_Services_Collection).document(merchantServiceBean.getServiceName()).set(merchantServiceBean);
            response = collectionsApiFuture.get().getUpdateTime().toString();
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
        return response;
    }
}
