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

/**
 *
 * @author Acer
 */
public class Task {
    
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final DoubleProperty estimatedProgress = new SimpleDoubleProperty();
    
    public Task(LocalDate startDate, LocalDate endDate)
    {
        this.startDate.set(startDate);
        this.endDate.set(endDate);
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
    
}
