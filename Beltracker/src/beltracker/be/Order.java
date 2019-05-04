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
    
    private final StringProperty orderNumber = new SimpleStringProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final StringProperty departmentName = new SimpleStringProperty();
    private final DoubleProperty realizedProgress = new SimpleDoubleProperty();
    private final DoubleProperty estimatedProgress = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> deliveryDate = new SimpleObjectProperty<>();
    
    public Order(String orderNumber, String customerName, String departmentName, double realizedProgress, double estimatedProgress, LocalDate deliveryDate)
    {
        this.orderNumber.set(orderNumber);
        this.customerName.set(customerName);
        this.departmentName.set(departmentName);
        this.realizedProgress.set(realizedProgress);
        this.estimatedProgress.set(estimatedProgress);
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
    
    public String getDepartmentName() {
        return departmentName.get();
    }

    public void setDepartmentName(String value) {
        departmentName.set(value);
    }

    public StringProperty departmentNameProperty() {
        return departmentName;
    }   

    public double getRealizedProgress() {
        return realizedProgress.get();
    }

    public void setRealizedProgress(double value) {
        realizedProgress.set(value);
    }

    public DoubleProperty realizedProgressProperty() {
        return realizedProgress;
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
    
    public LocalDate getDeliveryDate() {
        return deliveryDate.get();
    }

    public void setDeliveryDate(LocalDate value) {
        deliveryDate.set(value);
    }

    public ObjectProperty deliveryDateProperty() {
        return deliveryDate;
    }
    
}
