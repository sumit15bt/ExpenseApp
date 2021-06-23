package com.expense.jwt.api.repository;

import com.expense.jwt.api.beans.Expense;
import com.expense.jwt.api.beans.MServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
public class Servicedao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private MerchantDao merchantDao;

    public Map<Integer,String> getServiceIdNameMap() {
        Map<Integer,String> map = new HashMap<>();
        String query = "SELECT id,name FROM service";
        jdbcTemplate.query(query, (rs)->{
            map.put(rs.getInt("ID"),rs.getString("NAME"));
        });
        return map;
    }

    public Integer getServiceId(String name) {
        Map<Integer,String> map = new HashMap<>();
        String query = "SELECT id FROM Service where name='"+name+"'";
        List<Integer> serviceId=jdbcTemplate.queryForList(query,Integer.class);
        if(serviceId.size()>0){
            return serviceId.get(0);
        }
        return null;
    }


    public JSONArray getService(String serviceName,String merchantName){
        JSONArray allServices = new JSONArray();
        try{
            Map<Integer,String> merchantmap=merchantDao.getMerchantIdNameMap();
            String serviceQuery="SELECT * from service";
            if(serviceName !=null && !serviceName.equals("")){
                serviceQuery+=" where name='"+serviceName+"'";
            }
            if(merchantName !=null && !merchantName.equals("")){
                Integer merchanId=merchantDao.getMerchantId(merchantName);
                log.trace("merchantname merchantid {}",merchantName ,merchanId);
                serviceQuery+=" where merchantid='"+merchanId+"'";
            }
            log.trace(serviceQuery);
            List<MServices> expensesDone=jdbcTemplate.query(serviceQuery, new BeanPropertyRowMapper<>(MServices.class));

            log.debug("expensesDone {}",expensesDone);
            log.debug("expensesDone {}",expensesDone.size());
            expensesDone.forEach((service)->{
                JSONObject serviceData = new JSONObject();
                serviceData.put("name", service.getName());
                serviceData.put("merchantname", merchantmap.getOrDefault(service.getMerchantid(),"SERVICE NOT AVAILABLE"));
                serviceData.put("price", service.getPrice());
                serviceData.put("tax_gst_percentage", service.getTax_gst_percentage());
                serviceData.put("description",service.getDescription());
                serviceData.put("otherdetails", service.getOtherdetails());
                serviceData.put("entrydate", service.getEntrydate().toString());
                if(service.getLastupdated()!=null) {
                    serviceData.put("lastupdated", service.getLastupdated().toString());
                }
                allServices.put(serviceData);
            });
        }catch(Exception ex){
            log.error("ERROR : {}",ex);
        }
        return allServices;
    }


    public String createService(MServices services){
        String response="SOMETHING WENT WRONG .!";
        try{
            Integer merchantid=merchantDao.getMerchantId(services.getMerchantname());
            if(merchantid == null){
                return "Merchand not fornd .!";
            }
            String checkAvailablity="SELECT NAME FROM service WHERE name=:name AND merchantid=:merchantid";
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            parameterSource.addValue("name",services.getName());
            parameterSource.addValue("merchantid",merchantid);
            List<Map<String, Object>> nameAvailable = namedParameterJdbcTemplate.queryForList(checkAvailablity,parameterSource);
            if(nameAvailable!=null && nameAvailable.size() > 0){
                return "This service Already Avaialable For given merchant .!";
            }
            String createService="INSERT INTO service(\n" +
                    "             name, merchantid, price,  tax_gst_percentage, \n" +
                    "            description, otherDetails,entrydate )\n" +
                    "    VALUES (:name, :merchantId, :price, :tax_gst_percentage, :description,:otherDetails, :entrydate)";
            parameterSource.addValue("name",services.getName());
            parameterSource.addValue("merchantId",merchantid);
            parameterSource.addValue("price",services.getPrice());
            parameterSource.addValue("tax_gst_percentage",services.getTax_gst_percentage());
            parameterSource.addValue("description",services.getDescription());
            parameterSource.addValue("otherDetails",services.getOtherdetails());
            parameterSource.addValue("entrydate", new Date(), Types.TIMESTAMP);
            int inserted=namedParameterJdbcTemplate.update(createService,parameterSource);
            log.trace("inserted {}",inserted);
            if(inserted > 0){
                response= "Service Added Succesfully .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }


    public String updateService(MServices services){
        String response="SOMETHING WENT WRONG .!";
        try{
//            Integer merchantid=merchantDao.getMerchantId(services.getMerchantName());
//            Integer serviceid=getServicetId(services.getName());

            String updateService="UPDATE  service SET " +
                    "price=:price, tax_gst_percentage=:tax_gst_percentage, description=:description,otherDetails=:otherDetails, lastupdated=:lastUpdated where name=:name ";
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
//            parameterSource.addValue("id",serviceid);
            parameterSource.addValue("name",services.getName());
//            parameterSource.addValue("merchantId",merchantid);
            parameterSource.addValue("price",services.getPrice());
            parameterSource.addValue("tax_gst_percentage",services.getTax_gst_percentage());
            parameterSource.addValue("description",services.getDescription());
            parameterSource.addValue("otherDetails",services.getOtherdetails());
            parameterSource.addValue("lastUpdated", new Date(), Types.TIMESTAMP);
            log.trace("updateService {}",updateService);
            log.trace("parameterSource {}",parameterSource);
            int inserted=namedParameterJdbcTemplate.update(updateService,parameterSource);
            log.trace("inserted {}",inserted);
            if(inserted > 0){
                response= "Service Updated Succesfully .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }

    public String deleteService(String serviceName){
        String response="SOMETHING WENT WRONG .!";
        try{
            String deleteExpense="DELETE FROM service WHERE name='"+serviceName+"'";
            int inserted=jdbcTemplate.update(deleteExpense);
            log.trace("inserted {}",inserted);
            if(inserted > 0){
                response= "service Deleted .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }

}
