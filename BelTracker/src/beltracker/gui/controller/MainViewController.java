/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Order;
import beltracker.be.Order.OrderStatus;
import beltracker.exception.BelTrackerException;
import beltracker.gui.model.ModelCreator;
import beltracker.gui.model.interfaces.IMainModel;
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
    private static final String ORDER_OVERDUE_VIEW_PATH = "/beltracker/gui/view/tileview/OrderOverdueTileView.fxml"; 
    private static final String ORDER_ON_SCHEDULE_VIEW_PATH = "/beltracker/gui/view/tileview/OrderOnScheduleTileView.fxml"; 
    private static final String ORDER_DELAYED_VIEW_PATH = "/beltracker/gui/view/tileview/OrderDelayedTileView.fxml";
    
    @FXML
    private TilePane tilOrders;
    
    public MainViewController()
    {
        model = ModelCreator.getInstance().createMainModel();
    }
    
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
            FXMLLoader fxmlLoader = getOrderTileFXML(order.getOrderStatus());
            Parent root = fxmlLoader.load();
            
            OrderTileViewController controller = fxmlLoader.getController();
            controller.setOrderTile(order);
            
            tilOrders.getChildren().add(root);
        }  
    }
    
    private FXMLLoader getOrderTileFXML(OrderStatus status) throws IOException
    {
        FXMLLoader fxmlLoader;
        switch(status)
        {
            case DELAYED:       
                fxmlLoader = new FXMLLoader(getClass().getResource(ORDER_DELAYED_VIEW_PATH));
                break;

            case OVERDUE:       
                fxmlLoader = new FXMLLoader(getClass().getResource(ORDER_OVERDUE_VIEW_PATH));
                break;  

            case ON_SCHEDULE: 
                fxmlLoader = new FXMLLoader(getClass().getResource(ORDER_ON_SCHEDULE_VIEW_PATH));            
                break;

            default:
                fxmlLoader = new FXMLLoader(getClass().getResource(ORDER_ON_SCHEDULE_VIEW_PATH));            
                break;
        }
        return fxmlLoader;
    }
    
}
