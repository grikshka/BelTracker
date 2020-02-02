/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.datatransfer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class DataTransfer {
    
    private final List<Employee> employees = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    
    public class Employee {

        private final String initials;
        private final String name;
        private final Long salaryNumber;

        private Employee(String initials, String name, String salaryNumber) {
            this.initials = initials;
            this.name = name;
            this.salaryNumber = Math.round(Double.parseDouble(salaryNumber));
        }

        public String getInitials() {
            return initials;
        }

        public String getName() {
            return name;
        }

        public long getSalaryNumber() {
            return salaryNumber;
        }
    }
    
    public class Order {

        private final String number;
        private final String customerName;
        private final LocalDate deliveryDate;
        private final List<DepartmentTask> departmentTasks = new ArrayList<>();

        private Order(String number, String customerName, LocalDate deliveryDate) {
            this.number = number;
            this.customerName = customerName;
            this.deliveryDate = deliveryDate;
        }

        public String getNumber() {
            return number;
        }

        public String getCustomerName() {
            return customerName;
        }

        public LocalDate getDeliveryDate() {
            return deliveryDate;
        }

        public List<DepartmentTask> getDepartmentTasks() {
            return departmentTasks;
        }
        
        private void addDepartmentTask(DepartmentTask task) {
            departmentTasks.add(task);
        }
    }
    
    public class DepartmentTask {

        private final String departmentName;
        private final boolean isFinished;
        private final LocalDate startDate;
        private final LocalDate endDate;

        private DepartmentTask(String departmentName, boolean isFinished, LocalDate startDate, LocalDate endDate) {
            this.departmentName = departmentName;
            this.isFinished = isFinished;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public boolean getIsFinished() {
            return isFinished;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

    }
    
    public DepartmentTask addDepartmentTask(Order o, String departmentName, boolean isFinished, LocalDate startTime, LocalDate endTime)
    {
        DepartmentTask task = new DepartmentTask(departmentName, isFinished, startTime, endTime);
        o.addDepartmentTask(task);
        return task;
    }
    
    public Order addOrder(String number, String customerName, LocalDate deliveryDate) 
    {
        Order order = new Order(number, customerName, deliveryDate);
        orders.add(order);
        return order;
    }
    
    public Employee addEmployee(String initials, String name, String salaryNumber) 
    {
        Employee employee = new Employee(initials, name, salaryNumber);
        employees.add(employee);
        return employee;
    }
    
    public List<Employee> getEmployees() 
    {
        return employees;
    }

    public List<Order> getOrders() 
    {
        return orders;
    }

    
}
