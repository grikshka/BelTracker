/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.util.dataobserver;

/**
 *
 * @author Acer
 */
public interface DataSubject {
    
    void register(DataObserver o);
    
    void unregister(DataObserver o);
    
    void notifyObservers(String pathToNewFile, String newFileName, DataObserver.FileType fileType);
    
}
