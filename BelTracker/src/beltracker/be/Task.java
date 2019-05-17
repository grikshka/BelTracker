/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.be;

import java.time.LocalDate;
import java.util.Objects;
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
    
    public void update(Task updatedTask)
    {
        this.id.set(updatedTask.getId());
        this.startDate.set(updatedTask.getStartDate());
        this.endDate.set(updatedTask.getEndDate());
        this.estimatedProgress.set(updatedTask.getEstimatedProgress());
        this.department = updatedTask.getDepartment();
        this.order = updatedTask.getOrder();
        this.status = updatedTask.getStatus();
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id.get());
        hash = 53 * hash + Objects.hashCode(this.startDate.get());
        hash = 53 * hash + Objects.hashCode(this.endDate.get());
        hash = 53 * hash + Objects.hashCode(this.estimatedProgress.get());
        hash = 53 * hash + Objects.hashCode(this.department);
        hash = 53 * hash + Objects.hashCode(this.order);
        hash = 53 * hash + Objects.hashCode(this.status);
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
        final Task other = (Task) obj;
        if (!Objects.equals(this.id.get(), other.id.get())) {
            return false;
        }
        if (!Objects.equals(this.startDate.get(), other.startDate.get())) {
            return false;
        }
        if (!Objects.equals(this.endDate.get(), other.endDate.get())) {
            return false;
        }
        if (!Objects.equals(this.estimatedProgress.get(), other.estimatedProgress.get())) {
            return false;
        }
        if (!Objects.equals(this.department, other.department)) {
            return false;
        }
        if (!Objects.equals(this.order, other.order)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }
    
    
    
}
