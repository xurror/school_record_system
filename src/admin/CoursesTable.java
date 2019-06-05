/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Xurror
 */
public class CoursesTable {
    
    private final SimpleStringProperty courseTableDataCode;
    private final SimpleStringProperty courseTableDataTitle;
    private final SimpleIntegerProperty courseTableDataCredit;
    private final SimpleIntegerProperty courseTableDataSemester;
    
    
    public CoursesTable(String courseTableDataCode, String courseTableDataTitle, int courseTableDataCredit, int courseTableDataSemester) {        
        this.courseTableDataCode = new SimpleStringProperty(courseTableDataCode);
        this.courseTableDataTitle = new SimpleStringProperty(courseTableDataTitle);
        this.courseTableDataCredit = new SimpleIntegerProperty(courseTableDataCredit);
        this.courseTableDataSemester = new SimpleIntegerProperty(courseTableDataSemester);
        
    }
    
    public String getCourseTableDataCode() {
        return courseTableDataCode.get();
    }

    public SimpleStringProperty courseTableDataCodeProperty() {
        return courseTableDataCode;
    }

    public void setCourseTableDataCode(String courseTableDataCode) {
        this.courseTableDataCode.set(courseTableDataCode);
    }

    public String getCourseTableDataTitle() {
        return courseTableDataTitle.get();
    }

    public SimpleStringProperty courseTableDataTitleProperty() {
        return courseTableDataTitle;
    }

    public void setCourseTableDataTitle(String courseTableDataTitle) {
        this.courseTableDataTitle.set(courseTableDataTitle);
    }

    public int getCourseTableDataCredit() {
        return courseTableDataCredit.get();
    }

    public SimpleIntegerProperty courseTableDataCreditProperty() {
        return courseTableDataCredit;
    }

    public void setCourseTableDataCredit(int courseTableDataCredit) {
        this.courseTableDataCredit.set(courseTableDataCredit);
    }
        
    public int getCourseTableDataSemester() {
        return courseTableDataSemester.get();
    }

    public SimpleIntegerProperty courseTableDataSemesterProperty() {
        return courseTableDataSemester;
    }

    public void setCourseTableDataSemester(int courseTableDataSemester) {
        this.courseTableDataSemester.set(courseTableDataSemester);
    }
    
}
