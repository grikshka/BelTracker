/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.datafile;

import beltracker.dal.util.dataobserver.DataSubject;
import beltracker.dal.util.dataobserver.DataObserver;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Acer
 */
public class FolderWatcher implements DataSubject{

    private WatchKey watchKey;
    private Path folderToWatch;
    private List<DataObserver> observers = new ArrayList<>();
    
    public FolderWatcher(String folderToWatchPath)
    {
        try
        {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            folderToWatch = Paths.get(folderToWatchPath);
            watchKey = folderToWatch.register(watcher, 
                    StandardWatchEventKinds.ENTRY_CREATE);
            runFileObserving();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            //TO DO
        }
        
    }
    
    private void runFileObserving()
    {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> checkForNewData(), 2, 2, TimeUnit.SECONDS);
    }
    
    @Override
    public void register(DataObserver o) {
        observers.add(o);
    }

    @Override
    public void unregister(DataObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String pathToNewFile, DataObserver.FileType fileType) {
        for(DataObserver o : observers)
        {
            o.update(pathToNewFile, fileType);
        }
    }
    
    public void checkForNewData()
    {
        String newFilePath = null;
        for(WatchEvent<?> event : watchKey.pollEvents())
        {
            Path newFile = (Path) event.context();
            newFilePath = folderToWatch.resolve(newFile).toString();
            String newFileExtension = getFileExtension(newFile.toString());
            if(newFileExtension.equals(DataObserver.FileType.JSON.getExtension()))
            {
                notifyObservers(newFilePath, DataObserver.FileType.JSON);
            }
            else if(newFileExtension.equals(DataObserver.FileType.CSV.getExtension()))
            {
                notifyObservers(newFilePath, DataObserver.FileType.CSV);                
            }
            else
            {
                notifyObservers(newFilePath, DataObserver.FileType.INVALID_FILE_TYPE);
            }
        }
    }
    
    private String getFileExtension(String filePath)
    {
        String[] filePathArray = filePath.split("\\.");
        return filePathArray[filePathArray.length-1];
    }
}
