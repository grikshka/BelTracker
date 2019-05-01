/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model;

import beltracker.be.Order;
import beltracker.exception.BeltrackerException;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public interface IMainModel {
    
    void loadOrders() throws BeltrackerException;
    
    ObservableList<Order> getOrders() throws BeltrackerException;
    
    
}
