package com.expense.jwt.api.repository;

import com.expense.jwt.api.beans.MServices;
import com.expense.jwt.api.beans.KasherdhamUser;
import com.expense.jwt.api.controller.ExpenseController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private MerchantDao merchantDao;

    public KasherdhamUser findByEmail(String email) {
        String query = "SELECT * FROM KasherdhamUser WHERE email=?";
        Object[] parameters = new Object[]{email};
        RowMapper<KasherdhamUser> rowMapper = new BeanPropertyRowMapper<>(KasherdhamUser.class);
        return jdbcTemplate.queryForObject(query, rowMapper, parameters);
    }

    public Integer getServiceId(String name) {
        Map<Integer,String> map = new HashMap<>();
        String query = "SELECT id FROM Service where name='"+name+"'";
        return jdbcTemplate.queryForObject(query,Integer.class);
    }

    public Map<String,List<MServices>> findAllMerchant() {
        Map<String,List<MServices>> merchantServiceMap=new HashMap<>();
        Map<Integer,String> merchantMap= merchantDao.getMerchantIdNameMap();
        String query = "SELECT * FROM service";
        List<MServices> servicesList=jdbcTemplate.queryForList(query,MServices.class);
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

    public List<MServices> findAllServices_Of_A_Merchant(String merchantname) {
        Integer merchantid=merchantDao.getMerchantId(merchantname);
        String query = "SELECT * FROM service where merchantid=:merchantid";
        MapSqlParameterSource parameters=new MapSqlParameterSource();
        parameters.addValue("merchantid",merchantid);
        return namedParameterJdbcTemplate.queryForList(query, parameters, MServices.class);
    }

    public List<ExpenseController> findAllExpense_Of_A_Service(String serviceName) {
        Integer serviceId=getServiceId(serviceName);
        String query = "SELECT * FROM expense where serviceid=:serviceid";
        MapSqlParameterSource parameters=new MapSqlParameterSource();
        parameters.addValue("serviceid",serviceId);
        return namedParameterJdbcTemplate.queryForList(query, parameters, ExpenseController.class);
    }



}
