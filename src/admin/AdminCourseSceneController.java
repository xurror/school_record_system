/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import about.AboutSceneController;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class AdminCourseSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    private boolean isSetCourseAddCourseClick;
    private boolean isSetCourseEditClick;
    
    private AboutSceneController aboutSceneController = new AboutSceneController();    

    @FXML
    TableView<CoursesTable> adminCourseTableView;
    @FXML
    TableColumn<CoursesTable,String> adminCourseTableCode;
    @FXML
    TableColumn<CoursesTable,String> adminCourseTableTitle;
    @FXML
    TableColumn<CoursesTable,Integer> adminCourseTableCredit;
    @FXML
    TableColumn<CoursesTable,Integer> adminCourseTableSemester;


    @FXML
    private JFXTextField jftfCourseCode;
    @FXML
    private JFXTextField jftfCourseTitle;
    @FXML
    private JFXTextField jftfCourseCredit;
    @FXML
    private JFXTextField jftfCourseSearch;

    @FXML
    private ChoiceBox cbSemester;

    @FXML
    private Button courseCancelClick;
    @FXML
    private Button courseSaveClick;


    private ObservableList getDataFromCourseAndAddToObservableList(String query){
        ObservableList<CoursesTable> courseTableData = FXCollections.observableArrayList();
        try {

            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM cource;"
            
            while(resultSet.next()){
                
                courseTableData.add(new CoursesTable(
                        resultSet.getString("course_code"),
                        resultSet.getString("course_title"),
                        resultSet.getInt("course_credit"),
                        resultSet.getInt("course_semester")

                ));
            }
            
            connection.close();
            statement.close();
            resultSet.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        
        return courseTableData;

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        adminCourseTableCode.setCellValueFactory(new PropertyValueFactory<CoursesTable, String>("courseTableDataCode"));
        adminCourseTableTitle.setCellValueFactory(new PropertyValueFactory<CoursesTable, String>("courseTableDataTitle"));
        adminCourseTableCredit.setCellValueFactory(new PropertyValueFactory<CoursesTable, Integer>("courseTableDataCredit"));
        adminCourseTableSemester.setCellValueFactory(new PropertyValueFactory<CoursesTable, Integer>("courseTableDataSemester"));
        
        adminCourseTableView.setItems(getDataFromCourseAndAddToObservableList("SELECT * FROM public.course;"));
        
    }
    
    @FXML
    private void setCourseAddCourseClick(Event event){
        
        courseSetAllEnable();
        isSetCourseAddCourseClick = true;
        
    }

    private void courseSetAllEnable(){

        jftfCourseCode.setDisable(false);
        jftfCourseTitle.setDisable(false);
        jftfCourseCredit.setDisable(false);
        cbSemester.setDisable(false);

        courseCancelClick.setDisable(false);
        courseSaveClick.setDisable(false);
        
    }

    private void courseSetAllDisable(){
        
        jftfCourseCode.setDisable(true);
        jftfCourseTitle.setDisable(true);
        jftfCourseCredit.setDisable(true);
        cbSemester.setDisable(true);

        courseCancelClick.setDisable(true);
        courseSaveClick.setDisable(true);
        
    }

    @FXML
    private void setCourseEditClick(Event event){
        try{
            CoursesTable getSelectedRow = adminCourseTableView.getSelectionModel().getSelectedItem();

            String sqlQuery = "SELECT * FROM public.course "
                    + "WHERE course_code = '"+getSelectedRow.getCourseTableDataCode()+"';";

            try {

                connection = database.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlQuery);
                courseSetAllEnable();

                while(resultSet.next()) {

                    jftfCourseCode.setText(resultSet.getString("course_code"));
                    jftfCourseTitle.setText(resultSet.getString("course_title"));
                    jftfCourseCredit.setText(resultSet.getString("course_credit"));
                    cbSemester.setValue(resultSet.getString("course_semester"));

                }

                isSetCourseEditClick = true;

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Course");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
        }
        

    }

    @FXML
    private void setCourseDeleteClick(Event event){
        try{                    
            CoursesTable getSelectedRow = adminCourseTableView.getSelectionModel().getSelectedItem();
            String sqlQuery = "DELETE FROM public.course "
                    + "WHERE course_code = '"+getSelectedRow.getCourseTableDataCode()+"';";

            try {

                connection = database.getConnection();
                statement = connection.createStatement();

                int rowsAffected = statement.executeUpdate(sqlQuery);
                adminCourseTableView.setItems(getDataFromCourseAndAddToObservableList("SELECT * FROM public.course;"));

            }
            catch (SQLException e) {
                e.printStackTrace();
                
            }
            
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Course");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
        
    }

    @FXML
    private void setCourseSaveClick(Event event){

        try{
            connection = database.getConnection();
            statement = connection.createStatement();

            if(isSetCourseAddCourseClick){
                
                int rowsAffected = statement.executeUpdate(
                        "INSERT INTO public.course ("
                        + " course_code, course_title,"
                        + " course_credit, course_semester)"
                        + " VALUES ('"+jftfCourseCode.getText()+"',"
                        + " '"+jftfCourseTitle.getText()+"',"
                        + " '"+jftfCourseCredit.getText()+"',"
                        + " '"+cbSemester.getValue().toString()+"');"
                );
                
            }
            else if (isSetCourseEditClick){
                int rowsAffected = statement.executeUpdate(
                        "UPDATE public.course SET"+
                        " course_code = '"+jftfCourseCode.getText()+"',"+
                        " course_title = '"+jftfCourseTitle.getText()+"',"+
                        " course_credit = '"+jftfCourseCredit.getText()+"',"
                        + " course_semester = '"+cbSemester.getValue().toString()+"';"                        
                );
                
            }

            connection.close();
            statement.close();
            resultSet.close();
            
        }
        catch (SQLException e){
            e.printStackTrace();
            
        }
        
        courseSetAllClear();
        courseSetAllDisable();
        
        adminCourseTableView.setItems(getDataFromCourseAndAddToObservableList("SELECT * FROM public.course;"));
        
        isSetCourseAddCourseClick=false;
        isSetCourseEditClick = false;

    }

    private void courseSetAllClear(){
        
        jftfCourseCode.clear();
        jftfCourseTitle.clear();
        jftfCourseCredit.clear();
        
    }

    @FXML
    private void setCourseCancelClick(Event event){
        
        courseSetAllClear();
        courseSetAllDisable();
        isSetCourseEditClick=false;
        isSetCourseAddCourseClick = false;
        
    }

    @FXML
    private void setCourseRefreshClick(Event event){
        adminCourseTableView.setItems(getDataFromCourseAndAddToObservableList("SELECT * FROM public.course;"));//sql Query
        jftfCourseSearch.clear();
    }

    @FXML
    private void setCourseSearchClick(Event event){
        
        String sqlQuery = "SELECT * FROM public.course"
                + " WHERE course_code = '"+jftfCourseSearch.getText()+"';";
        adminCourseTableView.setItems(getDataFromCourseAndAddToObservableList(sqlQuery));
        
    }

    @FXML
    private void setCourseAboutButtonClick(Event event) throws IOException {
        aboutSceneController.about();
    }

    @FXML
    private void setCourseCloseButtonClick(Event event){
        aboutSceneController.close();
    }
    
}
