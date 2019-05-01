/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.exception.BeltrackerException;
import beltracker.gui.model.IMainModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    public void loadOrders() throws BeltrackerException
    {
        model.loadOrders();
//        List<Order> orders = model.getOrders();
//        for (Order order : orders) 
//        {
//            
//        }      
    }
    
}
