/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.be;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Acer
 */
public class Order {
    
    public enum OrderStatus {
        OVERDUE, ON_SCHEDULE, DELAYED
    }
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty orderNumber = new SimpleStringProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> deliveryDate = new SimpleObjectProperty<>();
    private Department currentDepartment;
    private Task departmentTask;
    private OrderStatus status;
    
    public Order(int id, String orderNumber, String customerName, LocalDate deliveryDate, Department currentDepartment, Task departmentTask)
    {
        this.id.set(id);
        this.orderNumber.set(orderNumber);
        this.customerName.set(customerName);
        this.deliveryDate.set(deliveryDate);
        this.currentDepartment = currentDepartment;
        this.departmentTask = departmentTask;
    }    
    
    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }    

    public String getOrderNumber() {
        return orderNumber.get();
    }

    public void setOrderNumber(String value) {
        orderNumber.set(value);
    }

    public StringProperty orderNumberProperty() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String value) {
        customerName.set(value);
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }
    
    public Department getCurrentDepartment() {
        return currentDepartment;
    }

    public void setCurrentDepartment(Department department) {
        this.currentDepartment = department;
    } 

    public Task getDepartmentTask() {
        return departmentTask;
    }

    public void setDepartmentTask(Task departmentTask) {
        this.departmentTask = departmentTask;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
