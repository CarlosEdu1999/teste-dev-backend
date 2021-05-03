package models;



import java.io.Serializable;
import java.sql.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.sql.Connection;


public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private LocalDate birthDate;
    private String gender;
    private HealthIssues healthIssues;
    private LocalDate dateOfCreation = LocalDate.now();
    private LocalDate dateOfUpdate = LocalDate.now();

    public Client(String name, LocalDate birthDate, String gender, HealthIssues healthIssues,LocalDate dateOfCreation,LocalDate dateOfUpdate) {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public LocalDate getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(LocalDate dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }
    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}


