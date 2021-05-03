package models;


import models.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealthIssues {


    private List<String> healthIssuesList = new ArrayList<>();




    public void addToList(String issueName, Integer degree) {
        this.healthIssuesList.add(issueName + "," + degree.toString());
    }

    public List<String> getHealthIssuesList() {
        return healthIssuesList;

    }

    public void setHealthIssuesList(List<String> healthIssuesList) {
        this.healthIssuesList = healthIssuesList;
    }
}
