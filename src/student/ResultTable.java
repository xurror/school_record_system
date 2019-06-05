/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Xurror
 */
public class ResultTable {
    private final SimpleStringProperty studentTableDataCourse;    
    private final SimpleDoubleProperty studentTableDataCA;
    private final SimpleDoubleProperty studentTableDataExams;
    private final SimpleDoubleProperty studentTableDataTotal;

    public ResultTable(String studentTableDataMatricule, Double studentTableDataCA, Double studentTableDataExams, Double studentTableDataTotal) {
        this.studentTableDataCourse = new SimpleStringProperty(studentTableDataMatricule);
        this.studentTableDataCA = new SimpleDoubleProperty(studentTableDataCA);
        this.studentTableDataExams = new SimpleDoubleProperty(studentTableDataExams);
        this.studentTableDataTotal = new SimpleDoubleProperty(studentTableDataTotal);
    }

    public String getStudentTableDataCourse() {
        return studentTableDataCourse.get();
    }

    public SimpleStringProperty studentTableDataCourseProperty() {
        return studentTableDataCourse;
    }

    public void setStudentTableDataCourse(String studentTableDataCourse) {
        this.studentTableDataCourse.set(studentTableDataCourse);
    }
    
    public Double getStudentTableDataCA() {
        return studentTableDataCA.get();
    }

    public SimpleDoubleProperty studentTableDataCAProperty() {
        return studentTableDataCA;
    }

    public void setStudentTableDataCA(Double studentTableDataCA) {
        this.studentTableDataCA.set(studentTableDataCA);
    }
    
    public Double getStudentTableDataExams() {
        return studentTableDataExams.get();
    }

    public SimpleDoubleProperty StudentTableDataExamsProperty() {
        return studentTableDataExams;
    }

    public void setStudedentTableDataExams(Double studentTableDataExams) {
        this.studentTableDataExams.set(studentTableDataExams);
    }
    
    public Double getStudentTableDataTotal() {
        return studentTableDataTotal.get();
    }

    public SimpleDoubleProperty studentTableDataTotalProperty() {
        return studentTableDataTotal;
    }

    public void setStudentTableDataTotal(Double studentTableDataTotal) {
        this.studentTableDataTotal.set(studentTableDataTotal);
    }
}

