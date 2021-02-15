package com.expense.jwt.api.repository;

import com.expense.jwt.api.beans.Expense;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
public class Expensedao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private Servicedao servicedao;

    @Autowired
    private MerchantDao merchantDao;


    public String createExpense(Expense expense){
        String response="SOMETHING WENT WRONG .!";
        try{
            Integer serviceId=servicedao.getServiceId(expense.getServicename());
            Integer merchantId=merchantDao.getMerchantId(expense.getMerchantname());
            if(serviceId == null){
                return "Service Not found";
            }
            if(merchantId == null){
                return "Merchant Not found";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String checkAvailablity="SELECT title FROM expense WHERE title=:title ";
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            parameterSource.addValue("title",expense.getTitle());
            List<Map<String, Object>> nameAvailable = namedParameterJdbcTemplate.queryForList(checkAvailablity,parameterSource);
            log.info("nameAvailable   ----> {} {}",nameAvailable,nameAvailable.size());
            if(nameAvailable !=null && nameAvailable.size() > 0){
                return "This Expense Already Added Before .!";
            }
            String createExpense="INSERT INTO expense(\n" +
                    "             title, serviceid,merchantid,  paymenttype, cost, \n" +
                    "            selectpaymentmode, amountpaid,balancelefttopaid,paymentrefnumber,expensedate,expenseentrydate) "+
                    "    VALUES (:title, :serviceid, :merchantId, :paymenttype, :cost,  :selectpaymentmode,:amountpaid,:balancelefttopaid ,:paymentrefnumber," +
                    " :expensedate, :expenseentrydate)";
//            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            parameterSource.addValue("title",expense.getTitle());
            parameterSource.addValue("serviceid",serviceId);
            parameterSource.addValue("merchantId",merchantId);
            parameterSource.addValue("paymenttype",expense.getPaymenttype());
            parameterSource.addValue("cost",expense.getCost());
            parameterSource.addValue("selectpaymentmode",expense.getSelectpaymentmode());
            parameterSource.addValue("amountpaid",expense.getAmountpaid());
            parameterSource.addValue("balancelefttopaid",expense.getBalancelefttopaid());
            parameterSource.addValue("paymentrefnumber",expense.getPaymentrefnumber());
            parameterSource.addValue("expensedate",sdf.parse(expense.getExdate().trim()),Types.TIMESTAMP);
            parameterSource.addValue("expenseentrydate",sdf.parse(expense.getExentrydate().trim()),Types.TIMESTAMP);
            log.info(createExpense);
            log.debug(parameterSource);
            int inserted=namedParameterJdbcTemplate.update(createExpense,parameterSource);
            log.trace("inserted {}",inserted);
            if(inserted > 0){
                response= "Expense Added .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }

    public String updateExpense(Expense expense){
        String response="SOMETHING WENT WRONG .!";
        try{
            Integer serviceId=servicedao.getServiceId(expense.getServicename());
            Integer merchantId=merchantDao.getMerchantId(expense.getMerchantname());
            if(serviceId == null){
                return "Service Not found";
            }
            if(merchantId == null){
                return "Merchant Not found";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updatedExpense="UPDATE  expense SET serviceid=:serviceid,merchantid=:merchantid ,paymenttype=:paymenttype, cost=:cost, selectpaymentmode=:selectpaymentmode, paymentrefnumber=:paymentrefnumber, lastupdated=:lastupdated "+
                    ", expenseentrydate=:expenseentrydate , expensedate=:expensedate where title=:title ";
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            parameterSource.addValue("title",expense.getTitle());
            parameterSource.addValue("serviceid",serviceId);
            parameterSource.addValue("merchantid",merchantId);
            parameterSource.addValue("paymenttype",expense.getPaymenttype());
            parameterSource.addValue("cost",expense.getCost());
            parameterSource.addValue("selectpaymentmode",expense.getSelectpaymentmode());
            parameterSource.addValue("paymentrefnumber",expense.getPaymentrefnumber());
            parameterSource.addValue("expensedate",sdf.parse(expense.getExdate().trim()),Types.TIMESTAMP);
            parameterSource.addValue("expenseentrydate",sdf.parse(expense.getExentrydate().trim()),Types.TIMESTAMP);
            parameterSource.addValue("lastupdated",new Date());
            int updated=namedParameterJdbcTemplate.update(updatedExpense,parameterSource);
            log.trace("inserted {}",updated);
            if(updated > 0){
                response= "Expense Updated Sucessfuly .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }



    public String getExpense(String expenseTitle,String servicename,String merchantname,String expensedate){
        JsonArray allExpense = new JsonArray();
        try{
            Map<Integer,String> servicemap=servicedao.getServiceIdNameMap();
            Map<Integer,String> merchnatmap=merchantDao.getMerchantIdNameMap();
            List<Expense> expensesDone=getExpenseList(expenseTitle,servicename,merchantname,expensedate);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(expensesDone!=null && expensesDone.size()>0) {
                log.trace(expensesDone);
                expensesDone.forEach((expensee) -> {
                    JsonObject expense = new JsonObject();
                    expense.addProperty("title", expensee.getTitle());
                    expense.addProperty("merchantname", merchnatmap.getOrDefault(expensee.getMerchantid(), "MERCHANT NOT AVAILABLE"));
                    expense.addProperty("servicename", servicemap.getOrDefault(expensee.getServiceid(), "SERVICE NOT AVAILABLE"));
                    expense.addProperty("expensedate", dateFormatter.format( expensee.getExpensedate()));
                    expense.addProperty("paymenttype", expensee.getPaymenttype());
                    expense.addProperty("cost", expensee.getCost());
                    expense.addProperty("selectpaymentmode", expensee.getSelectpaymentmode());
                    expense.addProperty("paymentrefnumber", expensee.getPaymentrefnumber());
                    expense.addProperty("amountpaid", expensee.getAmountpaid());
                    expense.addProperty("balancelefttopaid", expensee.getBalancelefttopaid());
                    expense.addProperty("expenseentrydate", dateFormatter.format(expensee.getExpenseentrydate()));
                    if (expensee.getLastupdated() != null) {
                        expense.addProperty("lastupdated", dateFormatter.format(expensee.getLastupdated()));
                    }
                    allExpense.add(expense);
                });
            }
        }catch(Exception ex){
            log.error("ERROR : {}",ex.getMessage());
        }
        return allExpense.toString();
    }
    public List<Expense> getExpenseList(String expenseTitle,String servicename,String merchantname,String expensedate){
        List<Expense> expensesDone=null;
        try{
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            String expenseQuery="SELECT * from expense";
            if(expenseTitle !=null && !expenseTitle.equals("")){
                expenseQuery+=" WHERE title=:expenseTitle";
                parameterSource.addValue("expenseTitle",expenseTitle);
            }
            if(servicename!=null && !servicename.equals("")){
                Integer serviceId=servicedao.getServiceId(servicename); // for a/c to service only
                expenseQuery+=" WHERE serviceid=:serviceId";
                parameterSource.addValue("serviceId",serviceId);
            }
            if(merchantname!=null && !merchantname.equals("")){
                Integer merchgantId=merchantDao.getMerchantId(merchantname); // for a/c to service only
                expenseQuery+=" WHERE merchantid=:merchgantId";
                parameterSource.addValue("servimerchgantIdceId",merchgantId);
            }
            if( expensedate!=null && !expensedate.equals("")){
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                expenseQuery+=" WHERE expenseentrydate between :morning AND :midnight";
                parameterSource.addValue("morning",sdf2.parse(expensedate.trim()+" 00:00:00"));
                parameterSource.addValue("midnight",sdf2.parse(expensedate.trim()+" 23:59:59"));
            }
            log.trace("expenseQuery {}",expenseQuery);
            log.trace("parameterSource {}",parameterSource);
            expensesDone=namedParameterJdbcTemplate.query(expenseQuery,parameterSource, new BeanPropertyRowMapper<>(Expense.class));
            log.trace(expensesDone.size());
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return expensesDone;
    }

    public String deleteExpense(String expenseTitle){
        String response="SOMETHING WENT WRONG .!";
        try{
            String deleteExpense="DELETE FROM expense WHERE title=:title";
            MapSqlParameterSource parameterSource=new MapSqlParameterSource();
            parameterSource.addValue("title",expenseTitle);
            int inserted=namedParameterJdbcTemplate.update(deleteExpense,parameterSource);
            log.trace("deleted {}",inserted);
            if(inserted > 0){
                response= "Expense Deleted .!";
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return response;
    }



}
