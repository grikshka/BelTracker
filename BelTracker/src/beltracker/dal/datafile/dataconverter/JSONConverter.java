/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.datafile.dataconverter;

import beltracker.dal.datatransfer.DataTransfer;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Acer
 */
public class JSONConverter {
    
    private final String AVAILABLE_WORKERS_RECORDS = "AvailableWorkers";
    private final String PRODUCTION_ORDERS_RECORDS = "ProductionOrders";
    
    public DataTransfer convertFileData(String filePath) throws IOException, ParseException
    {
        DataTransfer data = new DataTransfer();
        
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader(filePath))
        {
            JSONObject results = (JSONObject) jsonParser.parse(reader);  
            if(results.containsKey(AVAILABLE_WORKERS_RECORDS))
            {
                JSONArray availableWorkers = (JSONArray) results.get(AVAILABLE_WORKERS_RECORDS);
                addEmployees(data, availableWorkers);                
            }
            if(results.containsKey(PRODUCTION_ORDERS_RECORDS))
            {
                JSONArray productionOrders = (JSONArray) results.get(PRODUCTION_ORDERS_RECORDS);
                addOrders(data, productionOrders);              
            }
        }
        return data;
    }
    
    private void addEmployees(DataTransfer data, JSONArray workersArray)
    {
        workersArray.forEach( record -> {
            JSONObject employeeObject = (JSONObject) record;
            String initials = (String) employeeObject.get("Initials");
            String name = (String) employeeObject.get("Name");
            String salaryNumber = employeeObject.get("SalaryNumber").toString();
            data.addEmployee(initials, name, salaryNumber);
        });
    }
    
    private void addOrders(DataTransfer data, JSONArray productionOrders)
    {
        productionOrders.forEach(record -> {
            JSONObject productionOrder = (JSONObject) record;
            String number = (String) ((JSONObject) productionOrder.get("Order")).get("OrderNumber");
            String customerName = (String) ((JSONObject) productionOrder.get("Customer")).get("Name");
            LocalDate deliveryDate = parseDate((String) ((JSONObject) productionOrder.get("Delivery")).get("DeliveryTime"));
            JSONArray departmentTasks = (JSONArray) productionOrder.get("DepartmentTasks");
            DataTransfer.Order order = data.addOrder(number, customerName, deliveryDate);
            addDepartmentTasks(data, order, departmentTasks);
        });
    }
    
    private void addDepartmentTasks(DataTransfer data, DataTransfer.Order order, JSONArray departmentTasks)
    {
        departmentTasks.forEach(record -> {
            JSONObject task = (JSONObject) record;
            String departmentName = (String) ((JSONObject) task.get("Department")).get("Name");
            Boolean isFinished = (Boolean) task.get("FinishedOrder");
            LocalDate startDate = parseDate((String) task.get("StartDate"));
            LocalDate endDate = parseDate((String) task.get("EndDate"));
            data.addDepartmentTask(order, departmentName, isFinished, startDate, endDate);
        });
    }
    
    private LocalDate parseDate(String date)
    {
        date = date.replace("/", "");
        date = date.replace("Date(", "");
        date = date.replace("+0200)", "");
        Long epochDate = Long.parseLong(date);
        return Instant.ofEpochMilli(epochDate).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
}
