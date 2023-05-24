package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.Play;

import java.util.List;

public class ListDetailedContributorsDto {


    private String contributorFirstName;

    private String contributorLastName;

    private String introduction;

    private List<String> playNames;


    public String getContributorFirstName() {
        return contributorFirstName;
    }

    public void setContributorFirstName(String contributorFirstName) {
        this.contributorFirstName = contributorFirstName;
    }

    public String getContributorLastName() {
        return contributorLastName;
    }

    public void setContributorLastName(String contributorLastName) {
        this.contributorLastName = contributorLastName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getPlayNames() {
        return playNames;
    }

    public void setPlayNames(List<String> playNames) {
        this.playNames = playNames;
    }
}
