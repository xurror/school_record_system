/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import admin.CoursesTable;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class RegistrationSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    JFXTextField registrationSearch;

    @FXML
    TableView<RegistrationTable> currentCourseTableView;
    @FXML
    TableColumn<RegistrationTable, String> currentCourseColumnCode;
    @FXML
    TableColumn<RegistrationTable, String> currentCourseColumnTitle;
    @FXML
    TableColumn<RegistrationTable, Integer> currentCourseColumnCredit;
    
    @FXML
    TableView<CoursesTable> allCoursesTableView;
    @FXML
    TableColumn<CoursesTable, String> allCoursesColumnCode;
    @FXML
    TableColumn<CoursesTable, String> allCoursesColumnTitle;
    @FXML
    TableColumn<CoursesTable, Integer> allCoursesColumnCredit;
    
    @FXML
    JFXComboBox selectSemester;
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet,resultSet1;
    //private MenuBarControl menuBarControl = new MenuBarControl();
    
    private String matricule;    
    
    void setInfo(String matricule) {
        this.matricule = matricule;
    }
    
    static Stage stage;   
    
    final ObservableList comboOptions = FXCollections.observableArrayList();
    
    private ObservableList getDataFromAllCoursesAndAddToObservableList(String query){
        ObservableList<CoursesTable> allCoursesTableData = FXCollections.observableArrayList();
        try {

            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM cource;"
            
            while(resultSet.next()){
                allCoursesTableData.add(new CoursesTable(
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
        return allCoursesTableData;
    }

    public ObservableList getDataFromRegisteredCoursesAndAddToObservableList(String query){
        
        ObservableList<RegistrationTable> RegisteredCoursesTableData = FXCollections.observableArrayList();        
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM cource;"

            while(resultSet.next()){
                RegisteredCoursesTableData.add(new RegistrationTable(
                        resultSet.getString("mycourses_code"),
                        resultSet.getString("mycourses_title"),
                        resultSet.getInt("mycourses_credit")
                ));
            }            

            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RegisteredCoursesTableData;
    }
    
    public void fillComboBox(){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT course_semester" 
                    + " FROM public.course;");
            
            while(resultSet.next()){
                comboOptions.add(resultSet.getString("course_semester")+"thsem");
                
            }
            connection.close();
            statement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationSceneController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO                
        fillComboBox();
        selectSemester.setItems(comboOptions);
        
        allCoursesColumnCode.setCellValueFactory(new PropertyValueFactory<CoursesTable, String>("courseTableDataCode"));
        allCoursesColumnTitle.setCellValueFactory(new PropertyValueFactory<CoursesTable, String>("courseTableDataTitle"));
        allCoursesColumnCredit.setCellValueFactory(new PropertyValueFactory<CoursesTable, Integer>("courseTableDataCredit"));        

        //allCoursesTableView.setItems(getDataFromAllCoursesAndAddToObservableList("SELECT * FROM public.course;"));

        currentCourseColumnCode.setCellValueFactory(new PropertyValueFactory<RegistrationTable, String>("registeredCourseTableDataCode"));
        currentCourseColumnTitle.setCellValueFactory(new PropertyValueFactory<RegistrationTable, String>("registeredCourseTableDataTitle"));
        currentCourseColumnCredit.setCellValueFactory(new PropertyValueFactory<RegistrationTable, Integer>("registeredCourseTableDataCredit"));       

        currentCourseTableView.setItems(getDataFromRegisteredCoursesAndAddToObservableList(
                "SELECT * FROM public.mycourses"
                        + " WHERE mycourses_matricule = '"+matricule+"';"
        )); 

    }
    
    @FXML
    private void setRegistrationRefreshClick(Event event){
        currentCourseTableView.setItems(getDataFromRegisteredCoursesAndAddToObservableList(
                "SELECT * FROM public.mycourses"
                        + " WHERE mycourses_matricule = '"+matricule+"';")); 
        
    }
    
    public void studentRegistrationUpdate(
            String Course, String Title, int Credit) throws SQLException {
        connection = database.getConnection();
        statement = connection.createStatement();

        statement.executeUpdate(
                "INSERT INTO public.mycourses("
                        + "mycourses_matricule, mycourses_code,"
                        + " mycourses_title, mycourses_credit,"
                        + " mycourses_semester)"
                        + " VALUES ('"+matricule+"', '"+Course+"',"
                                + " '"+Title+"', '"+Credit+"',"
                                        + " '"+selectSemester.getValue().toString().substring(0,1)+"');"
        );

    }
    
    @FXML
    private void setRegistrationTakeClick(Event event) throws Exception{
        
        try{
            CoursesTable getSelectedCourse = allCoursesTableView.getSelectionModel().getSelectedItem();
        
            String code = getSelectedCourse.getCourseTableDataCode();
            String title = getSelectedCourse.getCourseTableDataTitle();
            int credit = getSelectedCourse.getCourseTableDataCredit();

            studentRegistrationUpdate(code, title, credit);

            currentCourseTableView.setItems(getDataFromRegisteredCoursesAndAddToObservableList(
                    "SELECT * FROM public.mycourses"
                            + " WHERE mycourses_matricule = '"+matricule+"';"
            )); 
            
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
    private void setRegistrationDeleteClick(Event event) throws SQLException {
        try{
            RegistrationTable getSelectedCourse = currentCourseTableView.getSelectionModel().getSelectedItem();
            String code = getSelectedCourse.getRegisteredCourseTableDataCode();

            connection = database.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(
                    "DELETE FROM public.mycourses"
                        + " WHERE mycourses_matricule = '"+matricule+"'"
                            + " AND mycourses_code = '"+code+"';");

            currentCourseTableView.setItems(getDataFromRegisteredCoursesAndAddToObservableList(
                    "SELECT * FROM public.mycourses"
                            + " WHERE mycourses_matricule = '"+matricule+"';"
            ));  

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
    private void setRegistrationDoneClick(Event event){
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void setRegistrationSearchClick(Event event){
        try{
            String sqlQuery = "select * FROM public.course"
                    + " WHERE course_code = '"+registrationSearch.getText()+"'"
                    + " AND course_semester = '"+selectSemester.getValue().toString()+"' ;";

            allCoursesTableView.setItems(getDataFromAllCoursesAndAddToObservableList(sqlQuery));
            registrationSearch.clear();
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING !!!");
            tray.setMessage("You Must Select A Semester");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
    }
    
    @FXML
    private void setRegistrationLoadClick(Event event) throws SQLException {
        try{
            String semester = selectSemester.getValue().toString();
            allCoursesTableView.setItems(getDataFromAllCoursesAndAddToObservableList(
                "SELECT * FROM public.course"
                        + " WHERE course_semester = '"+semester.substring(0,1)+"' ;"));
            
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING !!!");
            tray.setMessage("You Must Select A Semester");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
        
        
    }
    
    @FXML
    private void setCourseAboutButtonClick(Event event) throws IOException {
     //   menuBarControl.about();
    }

    @FXML
    private void setCourseCloseButtonClick(Event event){
       // menuBarControl.close();
    }
    
}
