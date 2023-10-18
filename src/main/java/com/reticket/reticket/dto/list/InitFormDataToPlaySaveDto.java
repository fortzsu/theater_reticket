package com.reticket.reticket.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class InitFormDataToPlaySaveDto {

        private Integer numberOfPriceCategories;

        private List<ListContributorsDto> contributorsList;

}
