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
    private Date birthDate;
    private String gender;
    private HealthIssues healthIssues;
    private  Date dateOfCreation = new Date();
    private Date dateOfUpdate = new Date();

    public Client(String name, Date birthDate, String gender, HealthIssues healthIssues,Date dateOfCreation,Date dateOfUpdate) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.dateOfCreation = dateOfCreation;
        this.dateOfUpdate = dateOfUpdate;
        this.healthIssues = healthIssues;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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
    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}


