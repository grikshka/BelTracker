/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Task;
import beltracker.gui.model.ModelCreator;
import beltracker.gui.model.interfaces.IMainModel;
import beltracker.gui.model.interfaces.ITaskModel;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;
import beltracker.gui.util.observer.TaskObserver;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class MainViewController implements Initializable, TaskObserver {

    private static final String ORDER_OVERDUE_VIEW_PATH = "/beltracker/gui/view/tasktileview/TaskOverdueTileView.fxml"; 
    private static final String ORDER_ON_SCHEDULE_VIEW_PATH = "/beltracker/gui/view/tasktileview/TaskOnScheduleTileView.fxml"; 
    private static final String ORDER_DELAYED_VIEW_PATH = "/beltracker/gui/view/tasktileview/TaskDelayedTileView.fxml";
    private IMainModel model;
    private HashMap<Integer, Node> taskTiles = new HashMap<>();
    
    @FXML
    private TilePane tilOrders;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void injectModel(IMainModel model)
    {
        this.model = model;
        model.register(this);
    }
    
    public void loadTaskTiles() throws IOException
    {
        model.loadTasks();
        List<Task> tasks = model.getTasks();
        for(Task task : tasks)
        {
            addTaskTile(task);
        }  
    }
    
    private void addTaskTile(Task task)
    {
        try
        {
            FXMLLoader fxmlLoader = getTaskTileFXML(task.getStatus());
            Parent root = fxmlLoader.load();

            TaskTileViewController controller = fxmlLoader.getController();
            controller.setTaskTile(task);

            tilOrders.getChildren().add(root);
            taskTiles.put(task.getId(), root);
        }
        catch(IOException ex)
        {
            //TO DO
        }
    }    
    
    private void removeTaskTile(Task task)
    {
        Node tileToRemove = taskTiles.get(task.getId());
        tilOrders.getChildren().remove(tileToRemove);
    }
    
    private FXMLLoader getTaskTileFXML(Task.Status status) throws IOException
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
    
    private void displayTaskFullView(Task task)
    {
        ITaskModel taskModel = ModelCreator.getInstance().createTaskModel();
        taskModel.setTask(task);
        
        
    }

    @Override
    public void update(List<Task> newTasks, List<Task> removedTasks) {
        for(Task newTask : newTasks)
        {
            Platform.runLater(() -> addTaskTile(newTask));
        }
        for(Task removedTask : removedTasks)
        {
            Platform.runLater(() -> removeTaskTile(removedTask));
        }
    }
}
