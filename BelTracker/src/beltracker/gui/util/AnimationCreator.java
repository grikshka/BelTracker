/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.util;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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
        
        TranslateTransition oldViewSlide = new TranslateTransition(Duration.millis(200), oldView);
//        oldViewSlide.interpolatorProperty().set(Interpolator.EASE_IN);
        oldViewSlide.setToX(20);     
        
        FadeTransition oldViewFade = new FadeTransition(Duration.millis(200), oldView);
        oldViewFade.setFromValue(1);
        oldViewFade.setToValue(0);
        
        ParallelTransition oldViewTransition = new ParallelTransition(oldViewSlide, oldViewFade);
        oldViewTransition.setOnFinished((e) -> currentScene.setRoot(newView));
              
        TranslateTransition newViewSlide = new TranslateTransition(Duration.millis(600), newView);
//        newViewSlide.interpolatorProperty().set(Interpolator.EASE_OUT);
        newViewSlide.setFromX(-20);
        newViewSlide.setToX(0);         
        
        FadeTransition newViewFade = new FadeTransition(Duration.millis(600), newView);
        newViewFade.setFromValue(0);
        newViewFade.setToValue(1);
        
        ParallelTransition newViewTransition = new ParallelTransition(newViewSlide, newViewFade);
        
        SequentialTransition mainTransition = new SequentialTransition(oldViewTransition, newViewTransition);

        return mainTransition;
    }
    
}
