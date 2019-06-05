/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Xurror
 */
public class TeacherTable {
    
    private final SimpleStringProperty teacherTableDataMatricule;    
    private final SimpleDoubleProperty teacherTableDataCA;
    private final SimpleDoubleProperty teacherTableDataExams;
    private final SimpleDoubleProperty teacherTableDataTotal;

    public TeacherTable(String teacherTableDataMatricule, Double teacherTableDataCA, Double teacherTableDataExams, Double teacherTableDataTotal) {
        this.teacherTableDataMatricule = new SimpleStringProperty(teacherTableDataMatricule);
        this.teacherTableDataCA = new SimpleDoubleProperty(teacherTableDataCA);
        this.teacherTableDataExams = new SimpleDoubleProperty(teacherTableDataExams);
        this.teacherTableDataTotal = new SimpleDoubleProperty(teacherTableDataTotal);
    }

    public String getTeacherTableDataMatricule() {
        return teacherTableDataMatricule.get();
    }

    public SimpleStringProperty teacherTableDataMatriculeProperty() {
        return teacherTableDataMatricule;
    }

    public void setTeacherTableDataMatricule(String teacherTableDataMatricule) {
        this.teacherTableDataMatricule.set(teacherTableDataMatricule);
    }
    
    public Double getTeacherTableDataCA() {
        return teacherTableDataCA.get();
    }

    public SimpleDoubleProperty teacherTableDataCAProperty() {
        return teacherTableDataCA;
    }

    public void setTeacherTableDataCA(Double teacherTableDataCA) {
        this.teacherTableDataCA.set(teacherTableDataCA);
    }
    
    public Double getTeacherTableDataExams() {
        return teacherTableDataExams.get();
    }

    public SimpleDoubleProperty teacherTableDataExamsProperty() {
        return teacherTableDataExams;
    }

    public void setTeacherTableDataExams(Double teacherTableDataExams) {
        this.teacherTableDataExams.set(teacherTableDataExams);
    }
    
    public Double getTeacherTableDataTotal() {
        return teacherTableDataTotal.get();
    }

    public SimpleDoubleProperty teacherTableDataTotalProperty() {
        return teacherTableDataTotal;
    }

    public void setTeacherTableDataTotal(Double teacherTableDataTotal) {
        this.teacherTableDataTotal.set(teacherTableDataTotal);
    }
    
}
