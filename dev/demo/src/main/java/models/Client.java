package models;



import java.io.Serializable;
import java.sql.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.sql.Connection;


public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String birthDate;
    private String gender;
    private HealthIssues healthIssues;
    private final Date dateOfCreation = new Date();
    private Date dateOfUpdate = new Date();

    public Client(String name, String birthDate, String gender, HealthIssues healthIssues) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.healthIssues = healthIssues;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HealthIssues getHealthIssues() {
        return healthIssues;

    }

    public void setHealthIssues(HealthIssues healthIssues) {
        this.healthIssues = healthIssues;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Date getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(Date dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }
}




