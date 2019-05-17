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
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty number = new SimpleStringProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> deliveryDate = new SimpleObjectProperty<>();
    private final ObjectProperty<Department> currentDepartment = new SimpleObjectProperty<>();
    
    public Order(int id, String number, String customerName, LocalDate deliveryDate, Department currentDepartment)
    {
        this.id.set(id);
        this.number.set(number);
        this.customerName.set(customerName);
        this.deliveryDate.set(deliveryDate);
        this.currentDepartment.set(currentDepartment);
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

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String value) {
        number.set(value);
    }

    public StringProperty numberProperty() {
        return number;
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
        return currentDepartment.get();
    }

    public void setCurrentDepartment(Department currentDepartment) {
        this.currentDepartment.set(currentDepartment);
    }
    
    public ObjectProperty currentDepartmentProperty()
    {
        return currentDepartment;
    }
    
    public void setDeliveryDate(LocalDate value)
    {
        this.deliveryDate.set(value);
    }
    
    public LocalDate getDeliveryDate()
    {
        return deliveryDate.get();
    }
    
    public ObjectProperty deliveryDateProperty() {
        return deliveryDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id.get());
        hash = 53 * hash + Objects.hashCode(this.number.get());
        hash = 53 * hash + Objects.hashCode(this.customerName.get());
        hash = 53 * hash + Objects.hashCode(this.deliveryDate.get());
        hash = 53 * hash + Objects.hashCode(this.currentDepartment);
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
        if (!Objects.equals(this.number.get(), other.number.get())) {
            return false;
        }
        if (!Objects.equals(this.customerName.get(), other.customerName.get())) {
            return false;
        }
        if (!Objects.equals(this.deliveryDate.get(), other.deliveryDate.get())) {
            return false;
        }
        if (!Objects.equals(this.currentDepartment, other.currentDepartment)) {
            return false;
        }
        return true;
    }

    
    
}
