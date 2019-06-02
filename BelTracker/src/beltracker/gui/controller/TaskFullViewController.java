/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Task;
import beltracker.gui.exception.ModelException;
import beltracker.gui.model.interfaces.ITaskModel;
import beltracker.gui.util.AlertManager;
import beltracker.gui.util.animation.AnimationCreator;
import beltracker.gui.util.animation.AnimationPlayer;
import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Kiddo
 */
public class TaskFullViewController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(TaskFullViewController.class);
    
    private static final String DATE_FORMAT = "dd MMMM yyyy";
    
    private ITaskModel model;
    private AlertManager alertManager;
    private AnimationPlayer animationPlayer;
    
    @FXML
    private Button btnSubmit;
    @FXML
    private Label lblStartDate;
    @FXML
    private Label lblEndDate;
    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblOrderCurrentDepartment;
    @FXML
    private JFXProgressBar prgEstimatedProgress;
    @FXML
    private Label lblOrderCustomerName;
    @FXML
    private Pane pneBackground;
    @FXML
    private StackPane stcConfirmation;
    @FXML
    private Label lblSubmitted;
    @FXML
    private ImageView imgSubmit;
    @FXML
    private StackPane stcDetails;
    @FXML
    private Button btnClose;

    
    public TaskFullViewController()
    {
        alertManager = new AlertManager();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> showStageWithAnimation());
    }    
    
    public void injectModel(ITaskModel model)
    {
        this.model = model;
        setTaskDetails(model.getTask());
    }
    
    private void setTaskDetails(Task task)
    {
        prgEstimatedProgress.progressProperty().bind(task.estimatedProgressProperty());
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        lblStartDate.textProperty().bind(Bindings.createStringBinding(() ->
               dtf.format(task.getStartDate()), task.startDateProperty()));
        lblEndDate.textProperty().bind(Bindings.createStringBinding(() ->
               dtf.format(task.getEndDate()), task.endDateProperty()));
        lblOrderCustomerName.textProperty().bind(Bindings.createStringBinding(() ->
                task.getOrder().getCustomerName(), task.orderProperty()));
        lblOrderNumber.textProperty().bind(Bindings.createStringBinding(() ->
                task.getOrder().getNumber(), task.orderProperty()));
        lblOrderCurrentDepartment.textProperty().bind(Bindings.createStringBinding(() ->
                task.getOrder().getCurrentDepartment().getName(), task.orderProperty()));
    }
    
    public void showStageWithAnimation()
    {
        Stage stage = (Stage) btnSubmit.getScene().getWindow();
        animationPlayer = new AnimationPlayer();
        animationPlayer.playSlideAndShow(stage);
    }    
    

    @FXML
    private void clickClose(ActionEvent event)
    {
        Stage stage = (Stage) btnSubmit.getScene().getWindow();
        animationPlayer = new AnimationPlayer();
        animationPlayer.playSlideAndClose(stage);
    }

    @FXML
    private void clickSubmit(ActionEvent event)
    {        
        Stage stage = (Stage) btnSubmit.getScene().getWindow();
        boolean submit = alertManager.displayConfirmation(stage, "Are you sure you want to submit this task as finished?");
        if(submit)
        {     
            try
            {
                model.submitTask();
                stcConfirmation.setVisible(true);
                SequentialTransition transition = AnimationCreator.createTaskSubmittedAnimation(stcDetails, stcConfirmation, pneBackground);
                transition.play();
                transition.setOnFinished(e -> {
                animationPlayer.playSlideAndClose(stage);
                });
            }
            catch(ModelException ex)
            {
                LOGGER.error(ex);
                alertManager.displayError(ex.getMessage());
            }
        }
    }
    
}
