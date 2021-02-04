package com.expense.jwt.api.service;

import com.expense.jwt.api.beans.Merchant;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class MerchantService {

    public static final String COL_NAME="ALLMerchants";

    public String saveMerchantDetails(Merchant merchantDetailBean) throws InterruptedException, ExecutionException {
        String response="SOMETHING WENT WRONG .!";
        try{
            merchantDetailBean.setId(String.valueOf(new Date().getTime()));
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(merchantDetailBean.getId()).set(merchantDetailBean);
            response=collectionsApiFuture.get().getUpdateTime().toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    public Merchant getMerchantDetails(String merchantId) throws InterruptedException, ExecutionException {
        Merchant merchant = null;
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(merchantId);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();
            if(document.exists()) {
                merchant = document.toObject(Merchant.class);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }

    public String updateMerchantDetails(Merchant merchant) throws InterruptedException, ExecutionException {
        String response="";
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(merchant.getId()).set(merchant);
            response= collectionsApiFuture.get().getUpdateTime().toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    public String deleteMerchant(String merchantId) {
        String response="";
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(merchantId).delete();
            response= "Document with Patient ID "+merchantId+" has been deleted";
        }catch (Exception ex){
           ex.printStackTrace(System.out);
        }
        return response;
    }

}

