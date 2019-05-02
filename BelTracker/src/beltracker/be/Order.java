/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.be;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    
    public Order(String orderNumber, String customerName, String departmentName, double realizedProgress, double estimatedProgress)
    {
        this.orderNumber.set(orderNumber);
        this.customerName.set(customerName);
        this.departmentName.set(departmentName);
        this.realizedProgress.set(realizedProgress);
        this.estimatedProgress.set(estimatedProgress);
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
    
}
