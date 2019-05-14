/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.be;

import java.time.LocalDate;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id.get());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.id.get(), other.id.get())) {
            return false;
        }
        return true;
    }
    
    
    
}
