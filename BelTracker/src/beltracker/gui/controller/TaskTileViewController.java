/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Task;
import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class TaskTileViewController implements Initializable {
    
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    @FXML
    private Label lblOrderCurrentDepartment;
    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblOrderCustomerName;
    @FXML
    private Label lblEndDate;
    @FXML
    private JFXProgressBar prgEstimatedProgress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setTaskTile(Task task)
    {
        lblOrderCurrentDepartment.textProperty().bind(task.getOrder().getCurrentDepartment().nameProperty());
        lblOrderCustomerName.textProperty().bind(task.getOrder().customerNameProperty());
        lblOrderNumber.textProperty().bind(task.getOrder().numberProperty());
        prgEstimatedProgress.progressProperty().bind(task.estimatedProgressProperty());
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        lblEndDate.textProperty().bind(Bindings.createStringBinding(() ->
               dtf.format(task.getEndDate()), task.endDateProperty()));
    }
    
}
