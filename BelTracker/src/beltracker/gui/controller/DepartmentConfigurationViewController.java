/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.be.Department;
import beltracker.gui.exception.ModelException;
import beltracker.gui.model.ModelCreator;
import beltracker.gui.model.interfaces.IDepartmentModel;
import beltracker.gui.util.AlertManager;
import beltracker.gui.util.animation.AnimationCreator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Kiddo
 */

public class DepartmentConfigurationViewController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(LoadingViewController.class);
    
    static final String LOADING_VIEW_PATH = "/beltracker/gui/view/LoadingView.fxml";
    private AlertManager alertManager;
    private IDepartmentModel model;

    @FXML
    private ComboBox<Department> cmbDepartments;
    @FXML
    private Rectangle rctSelectionMenuBackground;
    @FXML
    private StackPane stcDepartmentSelectionMenu;
    @FXML
    private Label lblStartConfirm;
    @FXML
    private Button btnStartConfirm;
    
    public DepartmentConfigurationViewController()
    {
        model = ModelCreator.getInstance().createDepartmentModel();
        alertManager = new AlertManager();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideStartConfigurationButton();
    }    
    
    private void hideStartConfigurationButton()
    {
        btnStartConfirm.setVisible(false);
        lblStartConfirm.setVisible(false);
    }
    
    private void showStartConfigurationButton()
    {
        try
        {
            cmbDepartments.setItems(FXCollections.observableArrayList(model.getAllDepartments()));
            btnStartConfirm.setVisible(true);
            lblStartConfirm.setVisible(true);
        }
        catch(ModelException ex)
        {
            LOGGER.error(ex);
            alertManager.displayError(ex.getMessage(), true);
        }
    }
    
    public void loadDepartmentInformations()
    {
        Department selectedDepartment = model.loadDepartment();
        if(selectedDepartment != null)
        {
            displayLoadingView(selectedDepartment);
        }
        else
        {
            showStartConfigurationButton();
        }
    }    
    
    @FXML
    private void clickStart(ActionEvent event)
    {        
        btnStartConfirm.setDisable(true);
        showSelectionMenu();
        btnStartConfirm.setOnAction((e) -> clickConfirm());
    }
    
    private void showSelectionMenu()
    {
        SequentialTransition horizontalStretch = AnimationCreator.createHorizontalStretchAnimation(btnStartConfirm, lblStartConfirm);
        horizontalStretch.setOnFinished((e) -> {
            rctSelectionMenuBackground.setVisible(true);
            lblStartConfirm.setText("Confirm");
        });
        
        ParallelTransition verticalStretch = AnimationCreator.createVerticalStretchAnimation(btnStartConfirm, lblStartConfirm, rctSelectionMenuBackground);
        verticalStretch.setOnFinished((e) -> stcDepartmentSelectionMenu.setVisible(true));
        
        SequentialTransition showMenu = new SequentialTransition(horizontalStretch, verticalStretch);
        showMenu.play();
    }
    
    @FXML
    private void clickChooseDepartment(ActionEvent event) 
    {
        if (!cmbDepartments.getSelectionModel().isEmpty())
        {
            btnStartConfirm.setDisable(false);
        }
    }
    
    private void clickConfirm()
    {      
        model.saveDepartment(cmbDepartments.getValue());
        hideSelectionMenu();
    }
    
    private void hideSelectionMenu()
    {
        stcDepartmentSelectionMenu.setVisible(false);
        ParallelTransition verticalShrink = AnimationCreator.createVerticalShrinkAnimation(btnStartConfirm, lblStartConfirm, rctSelectionMenuBackground);
        SequentialTransition horizontalShrink = AnimationCreator.createHorizontalShrinkAnimation(btnStartConfirm, lblStartConfirm);
        SequentialTransition hideMenu = new SequentialTransition(verticalShrink, horizontalShrink);
        hideMenu.setOnFinished((e) -> displayLoadingView(cmbDepartments.getValue()));
        hideMenu.play();
    }
    
    private void displayLoadingView(Department selectedDepartment)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOADING_VIEW_PATH));
            Parent root = fxmlLoader.load();
            
            Scene scene = cmbDepartments.getScene();
            scene.setRoot(root);
            
            LoadingViewController controller = fxmlLoader.getController();
            controller.loadMainView(selectedDepartment);
        
        }
        catch(IOException ex)
        {
            LOGGER.error(ex);
        }
    }
    
}
