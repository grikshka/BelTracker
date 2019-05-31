/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.util.animation;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

/**
 *
 * @author Acer
 */
public class AnimationCreator {
    
    public static SequentialTransition createSwitchViewAnimation(Scene currentScene, Parent oldView, Parent newView)
    {    
        FadeTransition oldViewFade = new FadeTransition(Duration.millis(200), oldView);
        oldViewFade.setFromValue(1);
        oldViewFade.setToValue(0);
        oldViewFade.setOnFinished((e) -> currentScene.setRoot(newView));
              
        FadeTransition newViewFade = new FadeTransition(Duration.millis(2000), newView);
        newViewFade.interpolatorProperty().set(Interpolator.EASE_IN);
        newViewFade.setFromValue(0);
        newViewFade.setToValue(1);
        
        SequentialTransition mainTransition = new SequentialTransition(oldViewFade, newViewFade);

        return mainTransition;
    }
    
    
    public static SequentialTransition createHorizontalStretchAnimation(Node element1, Node label)
    {
        ScaleTransition buttonScale = new ScaleTransition(Duration.millis(100), element1);
        buttonScale.interpolatorProperty().set(Interpolator.EASE_IN);
        buttonScale.setByX(1.25f);

        FadeTransition labelFade = new FadeTransition(Duration.millis(100), label);
        labelFade.setFromValue(1);
        labelFade.setToValue(0);

        PauseTransition pause = new PauseTransition(Duration.millis(200));

        SequentialTransition mainTransition = new SequentialTransition(buttonScale, labelFade, pause);

        return mainTransition;
    }
     
     public static ParallelTransition createVerticalStretchAnimation(Node button, Node label, Node pane)
     {
        TranslateTransition buttonSlide = new TranslateTransition(Duration.millis(200), button);
        buttonSlide.interpolatorProperty().set(Interpolator.EASE_IN);
        buttonSlide.setToY(180);
        
        TranslateTransition labelSlide = new TranslateTransition(Duration.millis(200), label);
        labelSlide.setToY(180);
         
        FadeTransition labelFade = new FadeTransition(Duration.millis(100), label);
        labelFade.setFromValue(0);
        labelFade.setToValue(1);
        
        ScaleTransition paneScale = new ScaleTransition(Duration.millis(200), pane);
        paneScale.interpolatorProperty().set(Interpolator.EASE_IN);
        paneScale.setFromY(0);
        paneScale.setToY(350);
        
        ParallelTransition mainTransition = new ParallelTransition(buttonSlide, labelSlide, labelFade, paneScale);
         
        return mainTransition;
     }
     
     public static ParallelTransition createVerticalShrinkAnimation(Node button, Node label, Node pane)
     {
        TranslateTransition buttonSlide = new TranslateTransition(Duration.millis(200), button);
        buttonSlide.setToY(0);
        
        TranslateTransition labelSlide = new TranslateTransition(Duration.millis(200), label);
        labelSlide.setToY(0);
         
        FadeTransition labelFade = new FadeTransition(Duration.millis(100), label);
        labelFade.setFromValue(1);
        labelFade.setToValue(0);
        
        ScaleTransition scalePane = new ScaleTransition(Duration.millis(200), pane);
        scalePane.setToY(0);
        
        ParallelTransition mainTransition = new ParallelTransition(buttonSlide, labelSlide, labelFade, scalePane);
         
        return mainTransition;
     }
    
     public static SequentialTransition createHorizontalShrinkAnimation(Node button, Node label)
     {
        ScaleTransition buttonSlide = new ScaleTransition(Duration.millis(100), button);
        buttonSlide.setToX(0);
        
        PauseTransition pauseFirst = new PauseTransition(Duration.millis(200));
        PauseTransition pauseSecond = new PauseTransition(Duration.millis(350));
        
        SequentialTransition mainTransition = new SequentialTransition(pauseFirst, buttonSlide, pauseSecond);
         
        return mainTransition;
     }
     
     public static SequentialTransition createPopupAnimation(Node element)
     {
        ScaleTransition imageFirstScale = new ScaleTransition(Duration.millis(225), element);
        imageFirstScale.setFromX(0.4);
        imageFirstScale.setFromY(0.4);
        imageFirstScale.setToX(1);
        imageFirstScale.setToY(1);
        
        ScaleTransition imageSecondScale = new ScaleTransition(Duration.millis(175), element);
        imageSecondScale.interpolatorProperty().set(Interpolator.EASE_OUT);
        imageSecondScale.setFromX(1.5);
        imageSecondScale.setFromY(1.5);
        imageSecondScale.setToX(1);
        imageSecondScale.setToY(1);
        
        PauseTransition pause = new PauseTransition(Duration.millis(200));
        
        SequentialTransition mainTransition = new SequentialTransition(imageFirstScale, imageSecondScale, pause);
        
        return mainTransition;
     }
     
     public static SequentialTransition createTaskSubmittedAnimation(Node stackPane,Node stackPanePopup, Node pane)
    {
        PauseTransition pauseFirst = new PauseTransition(Duration.millis(200));
        
        FadeTransition stackPaneFade = new FadeTransition(Duration.millis(350), stackPane);
        stackPaneFade.setFromValue(1);
        stackPaneFade.setToValue(0);
        
        
        FadeTransition popupFadeIn = new FadeTransition(Duration.millis(225), stackPanePopup);
        popupFadeIn.setFromValue(0);
        popupFadeIn.setToValue(1);
        
        ScaleTransition paneScale = new ScaleTransition(Duration.millis(200), pane);
        paneScale.setFromY(1);
        paneScale.setToY(0.35);
        
        PauseTransition pauseSecond = new PauseTransition(Duration.millis(780));
        
        FadeTransition paneFadeOut = new FadeTransition(Duration.millis(150), pane);   
        paneFadeOut.setToValue(0);
        FadeTransition popupFadeOut = new FadeTransition(Duration.millis(150), stackPanePopup);
        popupFadeOut.setToValue(0);
        ParallelTransition closeWindowFade = new ParallelTransition(paneFadeOut, popupFadeOut);
         
        SequentialTransition mainTransition = new SequentialTransition(pauseFirst, stackPaneFade, paneScale, popupFadeIn , pauseSecond, closeWindowFade);
         
        return mainTransition;
    }

    
}
