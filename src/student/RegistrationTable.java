/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Xurror
 */
public class RegistrationTable {
    
    private final SimpleStringProperty registeredCourseTableDataCode;
    private final SimpleStringProperty registeredCourseTableDataTitle;
    private final SimpleIntegerProperty registeredCourseTableDataCredit;

    public RegistrationTable(String registeredCourseTableDataCode, String registeredCourseTableDataTitle, int registeredCourseTableDataCredit) {
        this.registeredCourseTableDataCode = new SimpleStringProperty(registeredCourseTableDataCode);
        this.registeredCourseTableDataTitle = new SimpleStringProperty(registeredCourseTableDataTitle);
        this.registeredCourseTableDataCredit = new SimpleIntegerProperty(registeredCourseTableDataCredit);
    }
    
    public String getRegisteredCourseTableDataCode() {
        return registeredCourseTableDataCode.get();
    }

    public SimpleStringProperty RegisteredCourseTableDataCodeProperty() {
        return registeredCourseTableDataCode;
    }

    public void setRegisteredCourseTableDataCode(String registeredCourseTableDataCode) {
        this.registeredCourseTableDataCode.set(registeredCourseTableDataCode);
    }

    public String getRegisteredCourseTableDataTitle() {
        return registeredCourseTableDataTitle.get();
    }

    public SimpleStringProperty RegisteredCourseTableDataTitleProperty() {
        return registeredCourseTableDataTitle;
    }

    public void setRegisteredCourseTableDataTitle(String registeredCourseTableDataTitle) {
        this.registeredCourseTableDataTitle.set(registeredCourseTableDataTitle);
    }

    public int getRegisteredCourseTableDataCredit() {
        return registeredCourseTableDataCredit.get();
    }

    public SimpleIntegerProperty RegisteredCourseTableDataCreditProperty() {
        return registeredCourseTableDataCredit;
    }

    public void setRegisteredCourseTableDataCredit(int registeredCourseTableDataCredit) {
        this.registeredCourseTableDataCredit.set(registeredCourseTableDataCredit);
    }
    
}
