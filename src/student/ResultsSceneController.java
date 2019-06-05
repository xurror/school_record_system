/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class ResultsSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TableView<ResultTable> studentTableView;
    @FXML
    TableColumn<ResultTable, String> studentColumnCourse;    
    @FXML
    TableColumn<ResultTable, Double> studentColumnCA;
    @FXML
    TableColumn<ResultTable, Double> studentColumnExams;
    @FXML
    TableColumn<ResultTable, Double> studentColumnTotal;
    
    @FXML
    Text sName;
    @FXML
    Text sMatricule;
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    private String matricule;
    
    public void setCurrentInfo(String matricule) {                
        this.matricule = matricule;
        System.out.println("passed in matricule = "+this.matricule);
        
        try {
            System.out.println("Hayaa!!! matricule again = "+this.matricule);
            connection = database.getConnection();
            statement = connection.createStatement();                        
            
            resultSet = statement.executeQuery("SELECT * FROM public.student"
                    + " WHERE student_matricule = '"+this.matricule+"';");
            
            while(resultSet.next()){                
                sName.setText(resultSet.getString("student_fname")+" "+resultSet.getString("student_lname"));
                sMatricule.setLayoutX(sName.getLayoutX()+10+sName.getBoundsInParent().getWidth());
                sMatricule.setText("("+resultSet.getString("student_matricule")+")");
            }
            
            connection.close();
            statement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
        studentTableView.setItems(getDataFromResultTableDataAndAddToObservableList(
                    "SELECT * FROM public.mycourses"
                            + " WHERE mycourses_matricule = '"+this.matricule+"';"
            ));                
        
        //return matricule;
        
    }
    
    private ObservableList getDataFromResultTableDataAndAddToObservableList(String query){
        ObservableList<ResultTable> studentTableData = FXCollections.observableArrayList();
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                studentTableData.add(new ResultTable(
                        resultSet.getString("mycourses_code"),
                        resultSet.getDouble("mycourses_ca"),                        
                        resultSet.getDouble("mycourses_exams"),
                        resultSet.getDouble("mycourses_total")
                ));
                
            }
            
            connection.close();
            statement.close();
            resultSet.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }        
        
        return studentTableData;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        studentColumnCourse.setCellValueFactory(new PropertyValueFactory<ResultTable, String>("studentTableDataCourse"));
        studentColumnCA.setCellValueFactory(new PropertyValueFactory<ResultTable, Double>("studentTableDataCA"));  
        studentColumnExams.setCellValueFactory(new PropertyValueFactory<ResultTable, Double>("studentTableDataExams"));
        studentColumnTotal.setCellValueFactory(new PropertyValueFactory<ResultTable, Double>("studentTableDataTotal"));
    }    
    
}
