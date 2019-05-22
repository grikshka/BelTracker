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
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class MainViewController implements Initializable, TaskObserver {

    private static final String TASK_TILE_VIEW_PATH = "/beltracker/gui/view/TaskTileView.fxml"; 
    private static final String TASK_FULL_VIEW_PATH = "/beltracker/gui/view/TaskFullView.fxml"; 
    
    private static final String TASK_ON_SCHEDULE_TILE_VIEW_STYLE_CLASS = "vboxTileTaskOnSchedule"; 
    private static final String TASK_ON_SCHEDULE_FULL_VIEW_STYLE_CLASS = "/beltracker/gui/view/taskfullview/TaskOnScheduleFullView.fxml"; 
    
    private static final String TASK_DELAYED_TILE_VIEW_STYLE_CLASS = "vboxTileTaskDelayed";
    private static final String TASK_DELAYED_FULL_VIEW_STYLE_CLASS = "/beltracker/gui/view/taskfullview/TaskDelayedFullView.fxml";
    
    private static final String TASK_OVERDUE_TILE_VIEW_STYLE_CLASS = "vboxTileTaskOverdue";
    private static final String TASK_OVERDUE_FULL_VIEW_STYLE_CLASS = "/beltracker/gui/view/taskfullview/TaskDelayedFullView.fxml";
    
    private IMainModel model;
    private HashMap<Integer, Node> taskTiles = new HashMap<>();
    
    @FXML
    private TilePane tilOrders;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private ComboBox<?> comboBoxSort;
    @FXML
    private StackPane stcDarken;
    
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(TASK_TILE_VIEW_PATH));
            Parent root = fxmlLoader.load();           

            String styleClass = getTaskTileViewStyleClass(task.getStatus());
            root.getStyleClass().add(styleClass);
            
            TaskTileViewController controller = fxmlLoader.getController();
            controller.setTaskTile(task);
            
            tilOrders.getChildren().add(root);
            taskTiles.put(task.getId(), root);
            
            root.setOnMouseClicked((e) -> displayTaskFullView(task));
        }
        catch(IOException ex)
        {
            //TO DO
        }
    }    
    
    private void updateTaskTile(Task updatedTask)
    {    
        Node tileToUpdate = taskTiles.get(updatedTask.getId());
        String updatedStyleClass = getTaskTileViewStyleClass(updatedTask.getStatus());
        tileToUpdate.getStyleClass().clear();
        tileToUpdate.getStyleClass().add(updatedStyleClass);
    }
    
    private void removeTaskTile(Task task)
    {
        Node tileToRemove = taskTiles.get(task.getId());
        tilOrders.getChildren().remove(tileToRemove);
        taskTiles.remove(task.getId());
    }
    
    private String getTaskTileViewStyleClass(Task.Status status)
    {
        switch(status)
        {
            case DELAYED:       
                return TASK_DELAYED_TILE_VIEW_STYLE_CLASS;

            case OVERDUE:       
                return TASK_OVERDUE_TILE_VIEW_STYLE_CLASS; 

            case ON_SCHEDULE: 
                return TASK_ON_SCHEDULE_TILE_VIEW_STYLE_CLASS;

            default:
                return TASK_ON_SCHEDULE_TILE_VIEW_STYLE_CLASS;
        }
    }
    
    private String getTaskFullViewStyleClass(Task.Status status) throws IOException
    {
        switch(status)
        {
            case DELAYED:       
                return TASK_DELAYED_FULL_VIEW_STYLE_CLASS;

            case OVERDUE:       
                return TASK_OVERDUE_FULL_VIEW_STYLE_CLASS; 

            case ON_SCHEDULE: 
                return TASK_ON_SCHEDULE_FULL_VIEW_STYLE_CLASS;

            default:
                return TASK_ON_SCHEDULE_FULL_VIEW_STYLE_CLASS;
        }
    }
    
    private void displayTaskFullView(Task task)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(TASK_FULL_VIEW_PATH));
            Parent root = fxmlLoader.load();
            
            ITaskModel taskModel = ModelCreator.getInstance().createTaskModel();
            taskModel.setTask(task);
            
            TaskFullViewController controller = fxmlLoader.getController();
            controller.injectModel(taskModel);

            Stage currentStage = (Stage) tilOrders.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            
            dimCurrentStage();
            setFullTaskViewStage(currentStage, stage);
            stage.showAndWait();
            enlightCurrentStage();
            
        }
        catch(IOException ex)
        {
            //TO DO
            ex.printStackTrace();
        }
        
    }
     private void setFullTaskViewStage(Stage currentStage, Stage newStage)
    {
        setFullTaskViewStageCentering(currentStage, newStage);
        setFullTaskViewStageMode(newStage);
    }
    
    private void setFullTaskViewStageCentering(Stage currentStage, Stage newStage)
    {
        double centerXPosition = currentStage.getX() + currentStage.getWidth()/2d;
        double centerYPosition = currentStage.getY() + currentStage.getHeight()/2d;
        newStage.setOnShowing(e -> newStage.hide());
        newStage.setOnShown(e -> {
                newStage.setX(centerXPosition - newStage.getWidth()/2d);
                newStage.setY(centerYPosition - newStage.getHeight()/2d );
                newStage.show();});
    }
    
    private void setFullTaskViewStageMode(Stage newStage)
    {
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.initModality(Modality.APPLICATION_MODAL);
    } 
    
    
    private void dimCurrentStage()
    {
        FadeTransition transition = new FadeTransition(Duration.millis(300), stcDarken);
        transition.setToValue(0.5);
        transition.play();
        
    }
    
    private void enlightCurrentStage()
    {      
        FadeTransition transition = new FadeTransition(Duration.millis(300), stcDarken);
        transition.setToValue(0);
        transition.play();
    }

    @Override
    public void update(List<Task> newTasks, List<Task> modifiedTasks, List<Task> removedTasks) {
        for(Task newTask : newTasks)
        {
            Platform.runLater(() -> addTaskTile(newTask));
        }
        for(Task modifiedTask : modifiedTasks)
        {
            Platform.runLater(() -> updateTaskTile(modifiedTask));
        }
        for(Task removedTask : removedTasks)
        {
            Platform.runLater(() -> removeTaskTile(removedTask));
        }
    }

    @FXML
    private void searchTasks(KeyEvent event) {
        String searchKey = txtSearchBar.getText();
        
    }
}
