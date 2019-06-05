/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import about.AboutSceneController;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.SignUpSceneController;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class AdminTeacherSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet, resultSet1;
    private boolean isAdminTeacherAddClick;
    private boolean isAdminTeacherEditClick;
    private AboutSceneController aboutSceneController = new AboutSceneController();
    private String temp, temp1 ;
    
    @FXML
    TableView<AdminTeacherTable> adminTeacherTableView;
    @FXML
    TableColumn<AdminTeacherTable,Integer> adminTeacherColumnNo;
    @FXML
    TableColumn<AdminTeacherTable,String> adminTeacherColumnName;
    @FXML
    TableColumn<AdminTeacherTable,String> adminTeacherColumnMatricule;
    @FXML
    TableColumn<AdminTeacherTable,String> adminTeacherColumnEmail;
    @FXML
    TableColumn<AdminTeacherTable,String> adminTeacherColumnFaculty;
    @FXML
    TableColumn<AdminTeacherTable,String> adminTeacherColumnDepartment;
    @FXML
    TableColumn<AdminTeacherTable,String> adminTeacherColumnRunningCourse;


    @FXML
    private JFXTextField jftfTeacherFName;
    @FXML
    private JFXTextField jftfTeacherLName;
    @FXML
    private JFXTextField jftfTeacherMatricule;
    @FXML
    private JFXTextField jftfTeacherEmail;
    @FXML
    private JFXTextField jftfTeacherSearch;
        
    @FXML
    private JFXComboBox chooseRunningCourse;
    @FXML
    private JFXComboBox chooseFaculty;
    @FXML
    private JFXComboBox chooseDepartment;
    
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private Pane pane4;

    @FXML
    private Button adminTeacherCancelClick;
    @FXML
    private Button adminTeacherSaveClick;
    @FXML
    private Button TeacherLoadClick;

    final ObservableList comboOptions = FXCollections.observableArrayList();
    final ObservableList comboOptions1= FXCollections.observableArrayList();
    final ObservableList comboOptions2 = FXCollections.observableArrayList();
    
    private ObservableList getDataFromTeacherAndAddToObservableList(String query){
        ObservableList<AdminTeacherTable> adminTeacherTableData = FXCollections.observableArrayList();
        
        try {

            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM teacher;"
            while(resultSet.next()){
                adminTeacherTableData.add(new AdminTeacherTable(                        
                        resultSet.getInt("teacher_id"),
                        resultSet.getString("teacher_fname")+" "+resultSet.getString("teacher_lname"),
                        resultSet.getString("teacher_matricule"),
                        resultSet.getString("teacher_email"),
                        resultSet.getString("teacher_faculty"),
                        resultSet.getString("teacher_department"),
                        resultSet.getString("teacher_course")                        
                ));
                
            }
            
            connection.close();
            statement.close();
            resultSet.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        
        return adminTeacherTableData;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillComboBox();
        
        adminTeacherColumnNo.setCellValueFactory(new PropertyValueFactory<AdminTeacherTable, Integer>("AdminTeacherTableDataNo"));
        adminTeacherColumnName.setCellValueFactory(new PropertyValueFactory<AdminTeacherTable, String>("AdminTeacherTableDataName"));  
        adminTeacherColumnMatricule.setCellValueFactory(new PropertyValueFactory<AdminTeacherTable, String>("AdminTeacherTableDataMatricule"));
        adminTeacherColumnEmail.setCellValueFactory(new PropertyValueFactory<AdminTeacherTable, String>("AdminTeacherTableDataEmail"));        
        adminTeacherColumnFaculty.setCellValueFactory(new PropertyValueFactory<AdminTeacherTable, String>("AdminTeacherTableDataFaculty"));        
        adminTeacherColumnDepartment.setCellValueFactory(new PropertyValueFactory<AdminTeacherTable, String>("AdminTeacherTableDataDepartment"));
        adminTeacherColumnRunningCourse.setCellValueFactory(new PropertyValueFactory<AdminTeacherTable, String>("AdminTeacherTableDataRunningCourse"));

        adminTeacherTableView.setItems(getDataFromTeacherAndAddToObservableList("SELECT * FROM public.teacher;"));
        
        chooseRunningCourse.setItems(comboOptions);
        chooseFaculty.setItems(comboOptions1);
        
        pane1.setStyle("-fx-background-image: url(\"/image/t1.jpg\")");
        pane2.setStyle("-fx-background-image: url(\"/image/t2.jpg\")");
        pane3.setStyle("-fx-background-image: url(\"/image/t3.jpg\")");
        pane4.setStyle("-fx-background-image: url(\"/image/t4.jpg\")");

        backgroundAnimation();
        
    }
    
    public void fillComboBox(){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT course_code" 
                    + " FROM public.course;");            
            
            while(resultSet.next()){
                comboOptions.add(resultSet.getString("course_code"));
            }
            
            resultSet1 = statement.executeQuery("SELECT school_faculty" 
                    + " FROM public.school;");
            
            while(resultSet1.next()){
                comboOptions1.add(resultSet1.getString("school_faculty"));                
            }
            connection.close();
            statement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminTeacherSceneController.class.getName()).log(Level.SEVERE, null, ex);
            
        }        
    }
    
    public void updateTeacherCourses(){
        try {
            String course = chooseRunningCourse.getValue().toString(); 
            String title = null;
            int credit = 0;
            
            resultSet = statement.executeQuery("SELECT * FROM public.course"
                    + " WHERE course_code = '"+course+"'");
            
            while(resultSet.next()){
                title = resultSet.getString("course_title");
                credit = resultSet.getInt("course_credit");
                
            }
            
            statement.executeUpdate(
                        "UPDATE public.tcourses SET"+
                        " tcourses_matricule = '"+jftfTeacherMatricule.getText()+"',"+
                        " tcourses_code = '"+chooseRunningCourse.getValue().toString()+"',"+
                        " tcourses_title = '"+title+"',"+
                        " tcourses_credit = '"+credit+"'"
                        + " WHERE tcourses_matricule = '"+temp+"'"
                                + " AND tcourses_code = '"+temp1+"';"
                );
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminTeacherSceneController.class.getName()).log(Level.SEVERE, null, ex);
            
        }        
        
    }
    
    public void insertTeacherCourses(){
        try{
            try {
                String course = chooseRunningCourse.getValue().toString(); 
                String title = null;
                int credit = 0;

                resultSet = statement.executeQuery("SELECT * FROM public.course"
                        + " WHERE course_code = '"+course+"'");

                while(resultSet.next()){
                    title = resultSet.getString("course_title");
                    credit = resultSet.getInt("course_credit");
                }

                statement.executeUpdate(
                            "INSERT INTO public.tcourses(tcourses_matricule,"
                                    + " tcourses_code, tcourses_title,"
                                    + " tcourses_credit)"
                                    + " VALUES('"+jftfTeacherMatricule.getText()+"',"
                                            + " '"+chooseRunningCourse.getValue().toString()+"',"
                                                    + " '"+title+"', '"+credit+"');"
                    );

            } catch (SQLException ex) {
                Logger.getLogger(AdminTeacherSceneController.class.getName()).log(Level.SEVERE, null, ex);

            }  
        } catch (NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Course");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
        
    }

    @FXML
    private void setTeacherAddClick(Event event){        
        adminTeacherSetAllEnable();
        isAdminTeacherAddClick = true;
        
    }
    
    @FXML
    private void setTeacherLoadClick(Event event){
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

    private void adminTeacherSetAllEnable(){
        jftfTeacherFName.setDisable(false);
        jftfTeacherLName.setDisable(false);
        jftfTeacherMatricule.setDisable(false);
        jftfTeacherEmail.setDisable(false);        
        chooseRunningCourse.setDisable(false);
        chooseFaculty.setDisable(false);
        chooseDepartment.setDisable(false);

        adminTeacherCancelClick.setDisable(false);
        adminTeacherSaveClick.setDisable(false);
        TeacherLoadClick.setDisable(false);
    }

    private void adminTeacherSetAllDisable(){
        
        jftfTeacherFName.setDisable(true);
        jftfTeacherLName.setDisable(true);
        jftfTeacherMatricule.setDisable(true);        
        jftfTeacherEmail.setDisable(true);
        chooseRunningCourse.setDisable(true);
        chooseFaculty.setDisable(true);
        chooseDepartment.setDisable(true);

        adminTeacherCancelClick.setDisable(true);
        adminTeacherSaveClick.setDisable(true);
        TeacherLoadClick.setDisable(true);
        
    }

    private void adminTeacherSetAllClear(){
        
        jftfTeacherFName.clear();
        jftfTeacherLName.clear();
        jftfTeacherMatricule.clear();        
        jftfTeacherEmail.clear();
        //chooseRunningCourse.getItems().clear();
        chooseDepartment.getItems().clear();
        //chooseFaculty.getItems().clear();

    }

    @FXML
    private void setTeacherEditClick(Event event){
        try{
            AdminTeacherTable getSelectedRow = adminTeacherTableView.getSelectionModel().getSelectedItem();
            String tMatricule = getSelectedRow.getAdminTeacherTableDataMatricule();
            temp1 = getSelectedRow.getAdminTeacherTableDataRunningCourse();
            
            String sqlQuery = "SELECT * FROM public.teacher "
                    + "WHERE teacher_matricule = '"+tMatricule+"';";

            try {

                connection = database.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlQuery);

                adminTeacherSetAllEnable();

                while(resultSet.next()) {

                    jftfTeacherFName.setText(resultSet.getString("teacher_fname"));
                    jftfTeacherLName.setText(resultSet.getString("teacher_lname"));
                    jftfTeacherMatricule.setText(resultSet.getString("teacher_matricule"));                
                    jftfTeacherEmail.setText(resultSet.getString("teacher_email"));
                    chooseDepartment.setValue(resultSet.getString("teacher_department"));
                    chooseFaculty.setValue(resultSet.getString("teacher_faculty"));
                    chooseRunningCourse.setValue(resultSet.getString("teacher_course"));

                }

                temp = jftfTeacherMatricule.getText();
                isAdminTeacherEditClick = true;
            }
            catch (SQLException e) {
                e.printStackTrace();

            }

        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Teacher");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
        
    }

    @FXML
    private void setTeacherSaveClick(Event event){
        if(jftfTeacherMatricule.getText().isEmpty()){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("The Matricule Field Cannot Be Empty");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
        else{
            try{            
                connection = database.getConnection();
                statement = connection.createStatement();
                if(isAdminTeacherAddClick){
                    insertTeacherCourses();
                    String checkMatricule = null, getCourse = null;

                    resultSet = statement.executeQuery("SELECT teacher_matricule, teacher_course"
                            + " FROM public.teacher");

                    System.out.println("before = "+checkMatricule);

                    while (resultSet.next()){
                        checkMatricule = resultSet.getString("teacher_matricule");
                        getCourse = resultSet.getString("teacher_course");
                    }

                    System.out.println("after = "+checkMatricule);
                    try{
                        if (checkMatricule.equals(jftfTeacherMatricule.getText())){
                            statement.executeUpdate("UPDATE public.teacher SET"
                                    + " teacher_course = '"+getCourse+","+chooseRunningCourse.getValue().toString()+"'"
                                            + " WHERE teacher_matricule = '"+checkMatricule+"'");

                        }
                        else{                    
                            int rowsAffected = statement.executeUpdate(
                                "INSERT INTO public.teacher ("
                                + "teacher_fname, teacher_lname,"
                                + " teacher_matricule, teacher_email,"
                                + " teacher_faculty, teacher_department,"
                                        + " teacher_course)"
                                + " VALUES ('"+jftfTeacherFName.getText()+"',"
                                        + " '"+jftfTeacherLName.getText()+"',"
                                        + " '"+jftfTeacherMatricule.getText()+"',"
                                        + " '"+jftfTeacherEmail.getText()+"',"
                                        + " '"+chooseFaculty.getValue().toString()+"',"
                                        + " '"+chooseDepartment.getValue().toString()+"',"        
                                        + " '"+chooseRunningCourse.getValue().toString()+"');"
                            );
                        }
                    
                    } catch(NullPointerException e){
                        int rowsAffected = statement.executeUpdate(
                                "INSERT INTO public.teacher ("
                                + "teacher_fname, teacher_lname,"
                                + " teacher_matricule, teacher_email,"
                                + " teacher_faculty, teacher_department,"
                                        + " teacher_course)"
                                + " VALUES ('"+jftfTeacherFName.getText()+"',"
                                        + " '"+jftfTeacherLName.getText()+"',"
                                        + " '"+jftfTeacherMatricule.getText()+"',"
                                        + " '"+jftfTeacherEmail.getText()+"',"
                                        + " '"+chooseFaculty.getValue().toString()+"',"
                                        + " '"+chooseDepartment.getValue().toString()+"',"        
                                        + " '"+chooseRunningCourse.getValue().toString()+"');"
                            );
                    }


                }

                else if (isAdminTeacherEditClick){
                    updateTeacherCourses();
                    int rowsAffected = statement.executeUpdate(
                            "UPDATE public.teacher SET"+
                            " teacher_fname = '"+jftfTeacherFName.getText()+"',"+
                            " teacher_lname = '"+jftfTeacherLName.getText()+"',"+
                            " teacher_matricule = '"+jftfTeacherMatricule.getText()+"',"+
                            " teacher_email = '"+jftfTeacherEmail.getText()+"',"+
                            " teacher_faculty = '"+chooseFaculty.getValue().toString()+"',"+
                            " teacher_department = '"+chooseDepartment.getValue().toString()+"',"+
                            " teacher_course = '"+chooseRunningCourse.getValue().toString()+"'"
                            + " WHERE teacher_matricule = '"+temp+"';"
                    );
                }

                connection.close();
                statement.close();
                resultSet.close();

            }
            catch (SQLException e){
                e.printStackTrace();
            }

            adminTeacherSetAllClear();
            adminTeacherSetAllDisable();

            adminTeacherTableView.setItems(getDataFromTeacherAndAddToObservableList("SELECT * FROM public.teacher;"));

            isAdminTeacherAddClick=false;
            isAdminTeacherEditClick = false;
        }
    }

    @FXML
    private void setTeacherCancelClick(Event event){
        
        adminTeacherSetAllClear();
        adminTeacherSetAllDisable();
        isAdminTeacherAddClick = false;
        isAdminTeacherEditClick = false;
        
    }

    @FXML
    private void setTeacherDeleteClick(Event event){
        try{
            AdminTeacherTable getSelectedRow = adminTeacherTableView.getSelectionModel().getSelectedItem();
            String sqlQuery = "DELETE FROM public.teacher"
                    + " WHERE teacher_matricule = '"+getSelectedRow.getAdminTeacherTableDataMatricule()+"';";

            try {

                connection = database.getConnection();
                statement = connection.createStatement();
                int rowsAffected = statement.executeUpdate(sqlQuery);
                adminTeacherTableView.setItems(getDataFromTeacherAndAddToObservableList("SELECT * FROM public.teacher;"));

            }
            catch (SQLException e) {
                e.printStackTrace();

            }
        
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Teacher");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }    
        
    }

    @FXML
    private void setTeacherRefreshClick(Event event){
       adminTeacherTableView.setItems(getDataFromTeacherAndAddToObservableList("SELECT * FROM public.teacher;"));//sql Query
        jftfTeacherSearch.clear();
    }

    @FXML
    private void setTeacherSearchClick(Event event){
        String sqlQuery = "SELECT * FROM public.teacher"
                + " WHERE teacher_matricule = '"+jftfTeacherSearch.getText()+"'"
                + " OR teacher_fname = '"+jftfTeacherSearch.getText()+"'"
                + " OR teacher_lname = '"+jftfTeacherSearch.getText()+"';";
        adminTeacherTableView.setItems(getDataFromTeacherAndAddToObservableList(sqlQuery));
        jftfTeacherSearch.clear();
    }

    @FXML
    private void setCourseAboutButtonClick(Event event) throws IOException {
        aboutSceneController.about();
    }

    @FXML
    private void setCourseCloseButtonClick(Event event){
        aboutSceneController.close();
    }
    
    @FXML
    private void setTeacherViewClick(Event event){
        aboutSceneController.close();
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
