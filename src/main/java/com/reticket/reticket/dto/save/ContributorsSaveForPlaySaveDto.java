package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContributorsSaveForPlaySaveDto {

    private Long contributorId;

    private String contributorType;

    public ContributorsSaveForPlaySaveDto() {
    }

    public ContributorsSaveForPlaySaveDto(Long contributorId, String contributorType) {
        this.contributorId = contributorId;
        this.contributorType = contributorType;
    }

}
