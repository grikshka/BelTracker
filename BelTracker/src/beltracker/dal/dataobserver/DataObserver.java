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
public interface DataObserver {
    
    public enum FileType {
        JSON("txt"), 
        CSV("csv"), 
        INVALID_FILE_TYPE("");
        
        private String extension;
        
        private FileType(String extension)
        {
            this.extension = extension;
        }
        
        public String getExtension()
        {
            return extension;
        }
    }
    
    void update(String pathToNewFile, FileType fileType);
    
}
