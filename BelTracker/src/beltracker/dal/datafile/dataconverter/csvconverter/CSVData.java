/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.datafile.dataconverter.csvconverter;

import com.opencsv.bean.CsvBindByName;

/**
 *
 * @author Acer
 */
public class CSVData {
    
    //Employee
    @CsvBindByName(column = "AvailableWorkers__Initials")
    private String initials;

    @CsvBindByName(column = "AvailableWorkers__Name")
    private String name;

    @CsvBindByName(column = "AvailableWorkers__SalaryNumber")
    private String salary;

    //Order
    @CsvBindByName(column = "ProductionOrders__Customer__Name")
    private String customerName;

    @CsvBindByName(column = "ProductionOrders__Delivery__DeliveryTime")
    private String deliveryTime;

    @CsvBindByName(column = "ProductionOrders__Order__OrderNumber")
    private String orderNumber;

    //OrderTasks
    @CsvBindByName(column = "ProductionOrders__DepartmentTasks__Department__Name")
    private String departmentName;

    @CsvBindByName(column = "ProductionOrders__DepartmentTasks__StartDate")
    private String startDate;

    @CsvBindByName(column = "ProductionOrders__DepartmentTasks__EndDate")
    private String endDate;

    @CsvBindByName(column = "ProductionOrders__DepartmentTasks__FinishedOrder")
    private boolean isFinished;

    public String getInitials() {
        return initials;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }
    
}
