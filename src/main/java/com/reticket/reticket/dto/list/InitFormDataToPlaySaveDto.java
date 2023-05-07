package com.reticket.reticket.dto.list;

import java.util.List;

public class InitFormDataToPlaySaveDto {

        private Integer numberOfPriceCategories;

        private List<ListContributorsDto> contributorsList;

        public InitFormDataToPlaySaveDto() {
        }


        public Integer getNumberOfPriceCategories() {
                return numberOfPriceCategories;
        }

        public void setNumberOfPriceCategories(Integer numberOfPriceCategories) {
                this.numberOfPriceCategories = numberOfPriceCategories;
        }

        public List<ListContributorsDto> getContributorsList() {
                return contributorsList;
        }

        public void setContributorsList(List<ListContributorsDto> contributorsList) {
                this.contributorsList = contributorsList;
        }

        public void addContributorToList(ListContributorsDto dto) {
                this.contributorsList.add(dto);
        }
}
