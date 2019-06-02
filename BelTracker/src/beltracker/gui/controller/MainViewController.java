/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Task;
import beltracker.bll.IBLLFacade.SortingType;
import beltracker.gui.exception.ModelException;
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
import beltracker.gui.util.taskobserver.TaskObserver;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class MainViewController implements Initializable, TaskObserver {
    
    private static final Logger LOGGER = Logger.getLogger(MainViewController.class);

    private static final String TASK_TILE_VIEW_PATH = "/beltracker/gui/view/TaskTileView.fxml";     
    private static final String TASK_ON_SCHEDULE_TILE_VIEW_STYLE_CLASS = "vboxTileTaskOnSchedule"; 
    private static final String TASK_DELAYED_TILE_VIEW_STYLE_CLASS = "vboxTileTaskDelayed";
    private static final String TASK_OVERDUE_TILE_VIEW_STYLE_CLASS = "vboxTileTaskOverdue";
    
    private static final String TASK_ON_SCHEDULE_FULL_VIEW_PATH = "/beltracker/gui/view/taskfullview/TaskOnScheduleFullView.fxml";         
    private static final String TASK_DELAYED_FULL_VIEW_PATH = "/beltracker/gui/view/taskfullview/TaskDelayedFullView.fxml";    
    private static final String TASK_OVERDUE_FULL_VIEW_PATH = "/beltracker/gui/view/taskfullview/TaskOverdueFullView.fxml";
    
    private IMainModel model;
    private HashMap<Integer, Node> taskTiles = new HashMap<>();
    
    @FXML
    private TilePane tilTasks;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private StackPane stcDarken;
    @FXML
    private ComboBox<SortingType> cmbSort;
    
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
    
    public void initializeView() throws ModelException
    {
        initializeComboBox();
        model.loadTasks();
        List<Task> tasks = model.getTasks();
        loadTaskTiles(tasks); 
    }
    
    private void initializeComboBox()
    {
        cmbSort.getItems().setAll(FXCollections.observableArrayList(model.getTaskSortingTypes()));
    }
    
    private void loadTaskTiles(List<Task> tasks)
    {
        tilTasks.getChildren().clear();
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
            
            tilTasks.getChildren().add(root);
            taskTiles.put(task.getId(), root);
            
            root.setOnMouseClicked((e) -> displayTaskFullView(task));
        }
        catch(IOException ex)
        {
            LOGGER.error(ex);
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
        tilTasks.getChildren().remove(tileToRemove);
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
    
    private FXMLLoader getTaskFullViewFXML(Task.Status status) throws IOException
    {
        switch(status)
        {
            case DELAYED:       
                return new FXMLLoader(getClass().getResource(TASK_DELAYED_FULL_VIEW_PATH));

            case OVERDUE:       
                return new FXMLLoader(getClass().getResource(TASK_OVERDUE_FULL_VIEW_PATH)); 

            case ON_SCHEDULE: 
                return new FXMLLoader(getClass().getResource(TASK_ON_SCHEDULE_FULL_VIEW_PATH));

            default:
                return new FXMLLoader(getClass().getResource(TASK_ON_SCHEDULE_FULL_VIEW_PATH));
        }
    }
    
    private void displayTaskFullView(Task task)
    {
        try
        {
            FXMLLoader fxmlLoader = getTaskFullViewFXML(task.getStatus());
            Parent root = fxmlLoader.load();
            
            ITaskModel taskModel = ModelCreator.getInstance().createTaskModel();
            taskModel.setTask(task);
            
            TaskFullViewController controller = fxmlLoader.getController();
            controller.injectModel(taskModel);

            Stage currentStage = (Stage) tilTasks.getScene().getWindow();
            Stage newStage = new Stage();
            Scene newScene = new Scene(root);
            newStage.setScene(newScene);
            
            dimCurrentStage();
            setFullTaskViewStage(currentStage, newStage, newScene);
            newStage.showAndWait();
            enlightCurrentStage();
            
        }
        catch(IOException ex)
        {
            LOGGER.error(ex);
        }
        
    }
    
    private void setFullTaskViewStage(Stage currentStage, Stage newStage, Scene newScene)
    {
        setFullTaskViewStageCentering(currentStage, newStage);
        setFullTaskViewStageMode(newStage, newScene);
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
    
    private void setFullTaskViewStageMode(Stage newStage, Scene newScene)
    {
        newStage.initStyle(StageStyle.TRANSPARENT);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newScene.setFill(Color.TRANSPARENT);
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
    private void clickSort(ActionEvent event) {
        filterOrders();
    }

    @FXML
    private void inputSearch(KeyEvent event) {
        filterOrders();
    }
    
    private void filterOrders()
    {
        List<Task> allTasks = model.getTasks();
        
        String searchKey = txtSearchBar.getText();
        List<Task> results = model.searchTasks(allTasks, searchKey);
        if(cmbSort.getValue() != null)
        {
            results = model.sortTasks(results, cmbSort.getValue());
            
        }
        loadTaskTiles(results);
    }
    
}
