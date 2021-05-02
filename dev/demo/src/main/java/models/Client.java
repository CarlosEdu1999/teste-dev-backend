package models;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String birthDate;
    private String gender;
    private ArrayList<String> healthIssues;
    private final Date dateOfCreation = new Date();
    private Date dateOfUpdate = new Date();

    public Client(String name, String birthDate, String gender, ArrayList<String> healthIssues) {
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

    public ArrayList<String> getHealthIssues() {
        return healthIssues;
    }

    public void setHealthIssues(ArrayList<String> healthIssues) {
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




