/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Order;
import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class OrderTileViewController implements Initializable {

    @FXML
    private Label lblDepartment;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblOrderNumber;
    @FXML
    private JFXProgressBar prgEstimatedProgress;
    @FXML
    private JFXProgressBar prgRealizedProgress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setOrderTile(Order order)
    {
        lblDepartment.textProperty().bind(order.departmentNameProperty());
        lblCustomerName.textProperty().bind(order.customerNameProperty());
        lblOrderNumber.textProperty().bind(order.orderNumberProperty());
        prgEstimatedProgress.progressProperty().bind(order.estimatedProgressProperty());
        prgRealizedProgress.progressProperty().bind(order.realizedProgressProperty());
    }
    
}
