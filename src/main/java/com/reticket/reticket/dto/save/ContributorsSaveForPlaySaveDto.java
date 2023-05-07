package com.reticket.reticket.dto.save;

public class ContributorsSaveForPlaySaveDto {

    private Long contributorId;

    private String contributorType;

    public ContributorsSaveForPlaySaveDto() {
    }

    public ContributorsSaveForPlaySaveDto(Long contributorId, String contributorType) {
        this.contributorId = contributorId;
        this.contributorType = contributorType;
    }

    public String getContributorType() {
        return contributorType;
    }

    public void setContributorType(String contributorType) {
        this.contributorType = contributorType;
    }

    public Long getContributorId() {
        return contributorId;
    }

    public void setContributorId(Long contributorId) {
        this.contributorId = contributorId;
    }
}
