/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class SplashSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ImageView logoLabel;
    @FXML
    private Pane spinnerPane;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label helloLabel;
    @FXML
    private Label nameLabel;


    Main main;
    Stage stage;
    Stage loginStage;

    public void main(Stage stage,Main main){
        this.main=main;
        this.stage=stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        TranslateTransition translateTransition =new TranslateTransition(Duration.seconds(0.5),logoLabel);
        translateTransition.setByY(700);
        translateTransition.play();

        TranslateTransition translateTransition0 =new TranslateTransition(Duration.seconds(0.5),nameLabel);
        translateTransition0.setByY(700);
        translateTransition0.play();

        TranslateTransition translateTransition00 =new TranslateTransition(Duration.seconds(0.5),helloLabel);
        translateTransition00.setByY(700);
        translateTransition00.play();


        translateTransition.setOnFinished(event ->{
            TranslateTransition translateTransition1 =new TranslateTransition(Duration.seconds(1),logoLabel);
            translateTransition1.setByY(-700);
            translateTransition1.play();

            translateTransition1.setOnFinished(event1 -> {


                nameLabel.setVisible(true);

                TranslateTransition translateTransition11 =new TranslateTransition(Duration.seconds(1),nameLabel);
                translateTransition11.setByY(-700);
                translateTransition11.play();

                translateTransition11.setOnFinished(event2 -> {

                    helloLabel.setVisible(true);
                    TranslateTransition translateTransition111 =new TranslateTransition(Duration.seconds(1),helloLabel);
                    translateTransition111.setByY(-700);
                    translateTransition111.play();

                    translateTransition111.setOnFinished(event3 -> {
                        spinnerPane.setVisible(true);

                        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(1),spinnerPane);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(1);
                        fadeTransition.play();

                        fadeTransition.setOnFinished(event4 -> {

                            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(4),rootPane);
                            fadeTransition1.setFromValue(1);
                            fadeTransition1.setToValue(0.1);
                            fadeTransition1.play();

                            fadeTransition1.setOnFinished(event5 -> {
                                main.closeStage();
                                System.out.println("------- splash screen is closed --------");
                                loadLoginScene();
                            });

                                }
                        );

                    });

                });
            });

        });                
        
    }
    
    public void loadLoginScene(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Login Panel");
            Pane myPane = null;
            myPane = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SplashSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
