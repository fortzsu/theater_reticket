package com.reticket.reticket.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListDetailedContributorsDto {


    private String contributorFirstName;

    private String contributorLastName;

    private String introduction;

    private List<String> playNames;

    public ListDetailedContributorsDto(String contributorFirstName, String contributorLastName,
                                       String introduction) {
        this.contributorFirstName = contributorFirstName;
        this.contributorLastName = contributorLastName;
        this.introduction = introduction;
    }

}
