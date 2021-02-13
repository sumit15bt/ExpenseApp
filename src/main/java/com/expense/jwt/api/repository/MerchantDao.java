package com.expense.jwt.api.repository;

import com.expense.jwt.api.beans.Expense;
import com.expense.jwt.api.beans.MServices;
import com.expense.jwt.api.beans.Merchant;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class MerchantDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Map<Integer,String> getMerchantIdNameMap() {
        Map<Integer,String> map = new HashMap<>();
        String query = "SELECT id,name FROM Merchant";
        jdbcTemplate.query(query, (rs)->{
            map.put(rs.getInt("ID"),rs.getString("NAME"));
        });
        return map;
    }

    public Integer getMerchantId(String name) {
        Map<Integer,String> map = new HashMap<>();
        String query = "SELECT id FROM Merchant where name='"+name+"'";
        return jdbcTemplate.queryForObject(query,Integer.class);
    }

    public String getAllMerchants(){
        JsonArray allExpense = new JsonArray();
        String expenseQuery="SELECT * from merchant";
        return jdbcTemplate.query(expenseQuery, new BeanPropertyRowMapper<>(Merchant.class)).toString();
    }

    public Map<String, List<MServices>> getAllMerchantAndServices() {
        Map<String,List<MServices>> merchantServiceMap=new HashMap<>();
        Map<Integer,String> merchantMap= getMerchantIdNameMap();
        if(merchantMap.keySet().size() == 0) {
            return  merchantServiceMap;
        }
        String query = "SELECT * FROM service WHERE merchantid IN (:merchanids)";
        MapSqlParameterSource parameterSource=new MapSqlParameterSource();
        parameterSource.addValue("merchanids", merchantMap.keySet());
        List<MServices> servicesList=namedParameterJdbcTemplate.query(query,parameterSource,new BeanPropertyRowMapper<>(MServices.class));
        log.trace(servicesList);
        log.trace(servicesList.size());
        servicesList.forEach((services)->{
            String merchant=merchantMap.getOrDefault(services.getMerchantid(),"");
            if(merchantServiceMap.containsKey(merchant)){
                List<MServices> serviceList=merchantServiceMap.get(merchant);
                serviceList.add(services);
                merchantServiceMap.put(merchant,serviceList);
            }else{
                List<MServices> serviceList=new ArrayList<>();
                serviceList.add(services);
                merchantServiceMap.put(merchant,serviceList);
            }
        });
        return merchantServiceMap;
    }


    public String createMerchant(Merchant merchant){
        String response="SOMETHING WENT WRONG .!";
        try{
            String checkAvailablity="SELECT NAME FROM merchant WHERE name=:name";
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            parameterSource.addValue("name",merchant.getName());
            List<Map<String, Object>> nameAvailable = namedParameterJdbcTemplate.queryForList(checkAvailablity,parameterSource);
            log.info("nameAvailable   ----> {} {}",nameAvailable,nameAvailable.size());
            if(nameAvailable !=null && nameAvailable.size() > 0){
                return "This merchant Already Avaialable .!";
            }
            String createMerchant="INSERT INTO merchant(\n" +
                    "             name, primarycontactno, secondarycontactno, gstno, address, \n" +
                    "            state, cityname, pincode, accountholdername, accountno, bankname, \n" +
                    "            ifsccode, entrydate)\n" +
                    "    VALUES (:name, :primarycontactno, :secondarycontactno, :gstno, :address,:state, :cityname, :pincode, :accountholdername, :accountno, :bankname, :ifsccode, :entrydate)";
            parameterSource.addValue("name",merchant.getName());
            parameterSource.addValue("primarycontactno",merchant.getPrimarycontactno());
            parameterSource.addValue("secondarycontactno",merchant.getSecondarycontactno());
            parameterSource.addValue("gstno",merchant.getGstno());
            parameterSource.addValue("address",merchant.getAddress());
            parameterSource.addValue("state",merchant.getState());
            parameterSource.addValue("cityname",merchant.getCityname());
            parameterSource.addValue("pincode",merchant.getPincode());
            parameterSource.addValue("accountholdername",merchant.getAccountholdername());
            parameterSource.addValue("accountno",merchant.getAccountno());
            parameterSource.addValue("bankname",merchant.getBankname());
            parameterSource.addValue("ifsccode",merchant.getIfsccode());
            parameterSource.addValue("entrydate", new Date(), Types.TIMESTAMP);
            int inserted=namedParameterJdbcTemplate.update(createMerchant,parameterSource);
            log.trace("inserted {}",inserted);
            if(inserted > 0){
                response= "Merchant Added Succesfully .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }

    public String updateMerchant(Merchant merchant){
        String response="SOMETHING WENT WRONG .!";
        try{
//            Integer merchantid=getMerchantId(merchant.getName());
            String updateMerchant="UPDATE merchant set primarycontactno=:primarycontactno, secondarycontactno=:secondarycontactno," +
                    " gstno=:gstno, address=:address,state=:state, cityname=:cityname, pincode=:pincode, accountholdername=:accountholdername," +
                    " accountno=:accountno, bankname=:bankname, ifsccode=:ifsccode ,lastupdated=:lastUpdated where name=:name";
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            parameterSource.addValue("name",merchant.getName());
//            parameterSource.addValue("id",merchantid);
            parameterSource.addValue("primarycontactno",merchant.getPrimarycontactno());
            parameterSource.addValue("secondarycontactno",merchant.getSecondarycontactno());
            parameterSource.addValue("gstno",merchant.getGstno());
            parameterSource.addValue("address",merchant.getAddress());
            parameterSource.addValue("state",merchant.getState());
            parameterSource.addValue("cityname",merchant.getCityname());
            parameterSource.addValue("pincode",merchant.getPincode());
            parameterSource.addValue("accountholdername",merchant.getAccountholdername());
            parameterSource.addValue("accountno",merchant.getAccountno());
            parameterSource.addValue("bankname",merchant.getBankname());
            parameterSource.addValue("ifsccode",merchant.getIfsccode());
            parameterSource.addValue("lastUpdated", new Date(), Types.TIMESTAMP);
            int inserted=namedParameterJdbcTemplate.update(updateMerchant,parameterSource);
            log.trace("inserted {}",inserted);
            if(inserted > 0){
                response= "Merchant Updated Succesfully .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }

    public String deleteMerchant(String merchantName){
        String response="SOMETHING WENT WRONG .!";
        try{
            String deleteExpense="DELETE FROM merchant WHERE name='"+merchantName+"'";
            int deleted=jdbcTemplate.update(deleteExpense);
            log.trace("deleted {}",deleted);
            if(deleted > 0){
                response= "Merchant Deleted .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }
}
