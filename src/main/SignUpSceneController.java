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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class SignUpSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXTextField tfFName;   
    @FXML
    private JFXTextField tfLName;            
    @FXML
    private JFXTextField tfEmail;            
    @FXML
    private JFXTextField tfPhone;
    
    @FXML
    private JFXPasswordField pfPassword1;
    @FXML
    private JFXPasswordField pfPassword2;
    
    @FXML
    private DatePicker dpDOB;
            
    @FXML
    private ComboBox chooseFaculty;
    @FXML
    private ComboBox chooseDepartment;
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    final ObservableList comboOptions1 = FXCollections.observableArrayList();
    final ObservableList comboOptions2 = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillComboBox();
        chooseFaculty.setItems(comboOptions1);
        
    }
    
    public void fillComboBox(){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT school_faculty" 
                    + " FROM public.school;");
            
            while(resultSet.next()){
                comboOptions1.add(resultSet.getString("school_faculty"));                
            }
            connection.close();
            statement.close();           
            
        } catch (SQLException ex) {
            Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }                
        
    }        
    
    @FXML
    private void setLoadClick(Event event){
        try{
            chooseDepartment.getItems().clear();
            try {
                connection = database.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT *" 
                        + " FROM public.school"
                        + " WHERE school_faculty = '"+chooseFaculty.getValue().toString()+"';");

                while(resultSet.next()){
                    comboOptions2.add(resultSet.getString("school_department1"));
                    comboOptions2.add(resultSet.getString("school_department2"));
                    comboOptions2.add(resultSet.getString("school_department3"));
                }
                connection.close();
                statement.close();           

            } catch (SQLException ex) {
                Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            chooseDepartment.setItems(comboOptions2);
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Faculty");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
    }

    @FXML
    private void setAboutClick(Event event){
        try {
            Stage aboutStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/about/AboutScene.fxml"));
            
            Scene scene = new Scene(root);
            
            aboutStage.setScene(scene);
            aboutStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void setSignInClick(Event event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainScene.fxml"));
            loader.load();
            Parent mainParent = loader.getRoot();

            Scene mainScene = new Scene(mainParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();                                                        

            mainStage.hide();
            mainStage.setScene(mainScene);

            mainStage.setTitle("Login Panel");                            
            mainStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    @FXML
    private void setSignUpClick(Event event){
        try {
            if (pfPassword1.getText().equals(pfPassword2.getText())){
                try{
                    connection = database.getConnection();
                    statement = connection.createStatement();
                    statement.executeUpdate(
                        "INSERT INTO public.student("
                                + "student_fname, student_lname,"
                                + " student_password, student_faculty,"
                                + " student_department, student_email,"
                                + " student_phone, student_dob)"
                                + " VALUES ( '"+tfFName.getText()+"', '"+tfLName.getText()+"',"
                                        + " '"+pfPassword1.getText()+"', '"+chooseFaculty.getValue().toString()+"',"
                                        + " '"+chooseDepartment.getValue().toString()+"', '"+tfEmail.getText()+"',"
                                        + " '"+tfPhone.getText()+"', '"+dpDOB.getValue().toString()+"');"
                        );
                    
                    NotificationType notificationType = NotificationType.SUCCESS;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("CONGRATULATIONS");
                    tray.setMessage("Your Request Has Been Properly Executed.");
                    tray.setMessage("Please Wait For Confirmation From The Administration.");
                    tray.setNotificationType(notificationType);
                    tray.showAndDismiss(Duration.millis(3000));
                                        
                    connection.close();
                    statement.close();
                    
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("MainScene.fxml"));
                        loader.load();
                        Parent mainParent = loader.getRoot();

                        Scene mainScene = new Scene(mainParent);
                        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();                                                        

                        mainStage.hide();
                        mainStage.setScene(mainScene);

                        mainStage.setTitle("Login Panel");                            
                        mainStage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }catch (NullPointerException e){
                    NotificationType notificationType = NotificationType.ERROR;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("WARNING!!!");
                    tray.setMessage("You Must Pick A Faculty And Department");
                    tray.setNotificationType(notificationType);
                    tray.showAndDismiss(Duration.millis(3000));
                    
                }
                
            }
            else{
                NotificationType notificationType = NotificationType.ERROR;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("WARNING!!!");
                tray.setMessage("Passwords Do Not Match");
                tray.setNotificationType(notificationType);
                tray.showAndDismiss(Duration.millis(3000));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }    
    
}
