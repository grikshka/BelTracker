/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.dataobserver;

/**
 *
 * @author Acer
 */
public interface FileSubject {
    
    void register(FileObserver o);
    
    void unregister(FileObserver o);
    
    void notifyObservers(String pathToNewFile, FileObserver.FileType fileType);
    
}
