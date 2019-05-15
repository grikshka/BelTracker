/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.be;

import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Acer
 */
public class Task {
    
    public enum Status {
        OVERDUE, ON_SCHEDULE, DELAYED
    }
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final DoubleProperty estimatedProgress = new SimpleDoubleProperty();
    private Department department;
    private Order order;
    private Status status;
    
    public Task(int id, Department department, LocalDate startDate, LocalDate endDate)
    {
        this.id.set(id);
        this.department = department;
        this.startDate.set(startDate);
        this.endDate.set(endDate);
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
    
    public void setDepartment(Department dept)
    {
        this.department = dept;
    }
    
    public Department getDepartment()
    {
        return department;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    public Order getOrder()
    {
        return order;
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
      
    
    public LocalDate getEndDate() {
        return endDate.get();
    }

    public void setEndDate(LocalDate value) {
        endDate.set(value);
    }

    public ObjectProperty endDateProperty() {
        return endDate;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
