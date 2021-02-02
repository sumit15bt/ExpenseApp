package com.expense.jwt.api.service;

import com.expense.jwt.api.beans.BankAccount;
import com.expense.jwt.api.beans.Merchant;
import com.expense.jwt.api.beans.MerchantDetailBean;
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

    public static final String COL_NAME="Merchant";

    public String saveMerchantDetails(MerchantDetailBean merchantDetailBean) throws InterruptedException, ExecutionException {
        BankAccount merchantAccountDetail=new BankAccount(
                merchantDetailBean.getAccountHolderName(),
                merchantDetailBean.getAccountNo(),
                merchantDetailBean.getBankName(),
                merchantDetailBean.getIfscCode()
        );
        Merchant merchant=new Merchant(
                String.valueOf(new Date().getTime()),
                merchantDetailBean.getName(),
                merchantDetailBean.getPrimaryContactNo(),
                merchantDetailBean.getSecondaryContactNo(),
                merchantDetailBean.getGstNo(),
                merchantDetailBean.getAddress(),
                merchantDetailBean.getState(),
                merchantDetailBean.getCityName(),
                merchantDetailBean.getPinCode(),
                merchantAccountDetail
        );
        merchant.setName(merchant.getName());
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(merchant.getName()).set(merchant);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Merchant getMerchantDetails(String name) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        Merchant merchant = null;

        if(document.exists()) {
            merchant = document.toObject(Merchant.class);
            return merchant;
        }else {
            return null;
        }
    }

    public String updateMerchantDetails(Merchant merchant) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(merchant.getName()).set(merchant);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteMerchant(String merchantName) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(merchantName).delete();
        return "Document with Patient ID "+merchantName+" has been deleted";
    }

}

