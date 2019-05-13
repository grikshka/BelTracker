/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Order;
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
public class OrderTileViewController implements Initializable {
    
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    @FXML
    private Label lblCurrentDepartment;
    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblTaskEndDate;
    @FXML
    private JFXProgressBar prgEstimatedProgress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setOrderTile(Order order)
    {
        lblCurrentDepartment.textProperty().bind(order.getCurrentDepartment().nameProperty());
        lblCustomerName.textProperty().bind(order.customerNameProperty());
        lblOrderNumber.textProperty().bind(order.orderNumberProperty());
        prgEstimatedProgress.progressProperty().bind(order.getDepartmentTask().estimatedProgressProperty());
        
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
       lblTaskEndDate.textProperty().bind(Bindings.createStringBinding(() ->
               dtf.format(order.getDepartmentTask().getEndDate()), order.getDepartmentTask().endDateProperty()));
    }
    
}
