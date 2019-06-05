/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import student.ProfileSceneController;
import teacher.TeacherSceneController;
//import student.ProfileViewController;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Xurror
 */
public class MainSceneController implements Initializable {
    
    @FXML
    private JFXTextField jtfNameMatricule;
    @FXML
    private JFXPasswordField jpfPassword;
    @FXML
    private ChoiceBox cbUser;
    
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private Pane pane4;

    @FXML
    private void LoginButtonClick(ActionEvent event) throws SQLException {
        
        System.out.println("You clicked me!");
                
        String username = jtfNameMatricule.getText().trim();
        String password = jpfPassword.getText();        
        String userType = cbUser.getValue().toString().trim();
        
        if(isAllFieldFillup()){
            switch(userType){
                case "admin":
                    if (isValidCredentials(userType, username, password, "_name")){

                            try {                            
                                NotificationType notificationType = NotificationType.SUCCESS;
                                TrayNotification tray = new TrayNotification();
                                tray.setTitle("WELCOME");
                                tray.setMessage("You Are Succesfully Logged In");
                                tray.setNotificationType(notificationType);
                                tray.showAndDismiss(Duration.millis(3000));

                                Parent adminParent = FXMLLoader.load(getClass().getResource("/admin/AdminScene.fxml"));
                                Scene adminScene = new Scene(adminParent);
                                Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                adminStage.hide();
                                adminStage.setScene(adminScene);
                                adminStage.setTitle("Admin Panel");
                                adminStage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                case "student":
                    if (isValidCredentials(userType, username, password, "_matricule")){

                            try {                            
                                NotificationType notificationType = NotificationType.SUCCESS;
                                TrayNotification tray = new TrayNotification();
                                tray.setTitle("WELCOME");
                                tray.setMessage("You Are Succesfully Logged In");
                                tray.setNotificationType(notificationType);
                                tray.showAndDismiss(Duration.millis(3000));                         

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/student/ProfileScene.fxml"));
                                loader.load();
                                Parent studentParent = loader.getRoot();

                                Scene studentScene = new Scene(studentParent);
                                Stage studentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();                                                        

                                studentStage.hide();
                                studentStage.setScene(studentScene);

                                ProfileSceneController profileView = loader.getController();
                                profileView.setCurrentInfo(jtfNameMatricule.getText());

                                studentStage.setTitle("Student Panel");                            
                                studentStage.show();                                    

                            } catch (IOException e) {
                                e.printStackTrace();

                            }

                        }
                    break;

                case "teacher":

                    if (isValidCredentials(userType, username, password, "_matricule")){

                            try {                            
                                NotificationType notificationType = NotificationType.SUCCESS;
                                TrayNotification tray = new TrayNotification();
                                tray.setTitle("WELCOME");
                                tray.setMessage("You Are Succesfully Logged In");
                                tray.setNotificationType(notificationType);
                                tray.showAndDismiss(Duration.millis(3000));

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/teacher/TeacherScene.fxml"));
                                loader.load();
                                Parent teacherParent = loader.getRoot();

                                Scene teacherScene = new Scene(teacherParent);
                                Stage teacherStage = (Stage) ((Node) event.getSource()).getScene().getWindow();                                                        

                                teacherStage.hide();
                                teacherStage.setScene(teacherScene);

                                TeacherSceneController teacherSceneController = loader.getController();
                                teacherSceneController.setCurrentInfo(jtfNameMatricule.getText());

                                teacherStage.setTitle("Teacher Panel");                            
                                teacherStage.show(); 

                            } catch (IOException e) {
                                e.printStackTrace();

                            }

                        }
                    break;
                
            }
            
        }
        
        
    }
    
    @FXML
    private void setSignUpButtonClick(Event event){
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SignUpScene.fxml"));
            loader.load();
            Parent signUpParent = loader.getRoot();

            Scene signUpScene = new Scene(signUpParent);
            Stage signUpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            signUpStage.hide();
            signUpStage.setScene(signUpScene);
            
            signUpStage.setTitle("Login Panel");                            
            signUpStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private boolean isValidCredentials(String userType, String username, String password, String loginType) throws SQLException{
        
        boolean userPassOk = false;
        String query = "SELECT COUNT("+userType+loginType+") as total"
            + " FROM public."+userType+""
            + " WHERE "+userType+loginType+" = '"+username+"'"
            + " AND "+userType+"_password"+" = '"+password+"';";
        
        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query); 
        
        System.out.println(userType+loginType+" = "+username);
        System.out.println(userType+"_password"+" = "+password);   
        
        while (resultSet.next()){
            System.out.println("Count in db = "+resultSet.getInt("total"));            
            if(resultSet.getInt("total") == 1){
                userPassOk = true;
            }

        }
        
        if(!userPassOk) {

            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("Incorrect Username or Password");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));

            jtfNameMatricule.clear();
            jpfPassword.clear();

            userPassOk = false;

        }
        
        return userPassOk;
        
    }
    
    private boolean isAllFieldFillup(){
        boolean fillup;
        if(jtfNameMatricule.getText().trim().isEmpty()||jpfPassword.getText().isEmpty()){

            NotificationType notificationType = NotificationType.INFORMATION;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("OUPS!!!");
            tray.setMessage("Email or Password Fields should not Empty.");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));

            fillup = false;
        }
        else fillup = true;
        return fillup;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pane1.setStyle("-fx-background-image: url(\"/image/1.jpg\")");
        pane2.setStyle("-fx-background-image: url(\"/image/2.jpg\")");
        pane3.setStyle("-fx-background-image: url(\"/image/3.jpg\")");
        pane4.setStyle("-fx-background-image: url(\"/image/4.jpg\")");


        backgroundAnimation();
    } 
    
    private void backgroundAnimation() {

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(3),pane4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(3),pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                FadeTransition fadeTransition2=new FadeTransition(Duration.seconds(3),pane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {

                   FadeTransition fadeTransition0 =new FadeTransition(Duration.seconds(3),pane2);
                    fadeTransition0.setToValue(1);
                    fadeTransition0.setFromValue(0);
                    fadeTransition0.play();

                    fadeTransition0.setOnFinished(event3 -> {

                        FadeTransition fadeTransition11 =new FadeTransition(Duration.seconds(3),pane3);
                        fadeTransition11.setToValue(1);
                        fadeTransition11.setFromValue(0);
                        fadeTransition11.play();

                        fadeTransition11.setOnFinished(event4 -> {

                            FadeTransition fadeTransition22 =new FadeTransition(Duration.seconds(3),pane4);
                            fadeTransition22.setToValue(1);
                            fadeTransition22.setFromValue(0);
                            fadeTransition22.play();

                            fadeTransition22.setOnFinished(event5 -> {
                                backgroundAnimation();
                            });

                        });

                    });

                });
            });

        });

    }
    
}
