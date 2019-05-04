/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Order;
import beltracker.exception.BelTrackerException;
import beltracker.gui.model.IMainModel;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class MainViewController implements Initializable {

    private IMainModel model;
    
    @FXML
    private TilePane tilOrders;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void injectModel(IMainModel model)
    {
        this.model = model;
    }
    
    public void loadOrders() throws BelTrackerException, IOException
    {
        model.loadOrders();
       List<Order> orders = model.getOrders();
        for (Order order : orders) 
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/beltracker/gui/view/OrderTileView.fxml"));
            Parent root = fxmlLoader.load();
            
            OrderTileViewController controller = fxmlLoader.getController();
            controller.setOrderTile(order);
            
            tilOrders.getChildren().add(root);
        }  
    }
    
}
