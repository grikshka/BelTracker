/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.concrete;

import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.bll.IBLLFacade;
import beltracker.gui.model.interfaces.IMainModel;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public class MainModel implements IMainModel {
     
    private IBLLFacade bllFacade;
    private Department selectedDepartment;
    private ObservableList<Order> orders;
    
    public MainModel(IBLLFacade facade)
    {
        this.bllFacade = facade;
    }
    
    @Override
    public void setDepartment(Department department) {
        selectedDepartment = department;
    }
    
    @Override
    public void loadOrders()
    {
        List<Order> departmentOrders = bllFacade.getOrders(selectedDepartment);
        orders = FXCollections.observableArrayList(departmentOrders);
    }
    
    @Override
    public ObservableList<Order> getOrders()
    {
        return orders;
    }
    
}
