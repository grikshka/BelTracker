/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Order;
import beltracker.dal.DALFacadeFactory;
import beltracker.dal.IDALFacade;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade facade;
    
    public BLLManager()
    {
        facade = DALFacadeFactory.getInstance().createFacade(DALFacadeFactory.FacadeType.MOCK);
    }

    @Override
    public List<Order> getOrders(String departmentName) {
        return facade.getOrders(departmentName);
    }
    
    
    
}
