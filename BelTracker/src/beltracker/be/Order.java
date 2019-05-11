/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.be;

import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    
    private Department department;
    private final StringProperty orderNumber = new SimpleStringProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final DoubleProperty estimatedProgress = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> deliveryDate = new SimpleObjectProperty<>();
    private OrderStatus status = OrderStatus.ON_SCHEDULE;
    
    public Order(Department department, String orderNumber, String customerName, LocalDate startDate, LocalDate deliveryDate)
    {
        this.department = department;
        this.orderNumber.set(orderNumber);
        this.customerName.set(customerName);
        this.startDate.set(startDate);
        this.deliveryDate.set(deliveryDate);
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
    
    public Department getDepartment() {
        return department;
    }

    public void setDepartmentName(Department department) {
        this.department = department;
    } 
    
    public double getEstimatedProgress() {
        return estimatedProgress.get();
    }

    public void setEstimatedProgress(double value) {
        estimatedProgress.set(value);
    }

    public DoubleProperty estimatedProgressProperty() {
        return estimatedProgress;
    }
    
    public LocalDate getStartDate() {
        return startDate.get();
    }

    public void setStartDate(LocalDate value) {
        startDate.set(value);
    }

    public ObjectProperty startDateProperty() {
        return startDate;
    }
      
    
    public LocalDate getDeliveryDate() {
        return deliveryDate.get();
    }

    public void setDeliveryDate(LocalDate value) {
        deliveryDate.set(value);
    }

    public ObjectProperty deliveryDateProperty() {
        return deliveryDate;
    }
    
    public void setOrderStatus(OrderStatus status)
    {
        this.status = status;
    }
    
    public OrderStatus getOrderStatus()
    {
        return status;
    }
    
}
