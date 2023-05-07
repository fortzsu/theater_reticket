package com.reticket.reticket.service;

import com.reticket.reticket.domain.Contributor;
import com.reticket.reticket.dto.list.ListContributorsDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.repository.ContributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContributorService {

    private final ContributorRepository contributorRepository;


    @Autowired
    public ContributorService(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    public List<Contributor> save(List<ContributorSaveDto> contributorSaveDtoList) {
        List<Contributor> contributors = new ArrayList<>();
        for (ContributorSaveDto saveDto : contributorSaveDtoList) {
            contributors.add(this.contributorRepository.save(updateValues(new Contributor(), saveDto)));
        }
        return contributors;
    }

    private Contributor updateValues(Contributor contributor, ContributorSaveDto contributorSaveDto) {
        contributor.setFirstName(contributorSaveDto.getFirstName());
        contributor.setLastName(contributorSaveDto.getLastName());
        contributor.setIntroduction(contributorSaveDto.getIntroduction());
        return contributor;
    }

    public List<Contributor> findAllOrderByLastName() {
        return this.contributorRepository.findByOrderByLastName();
    }

    public Contributor findById(Long id) {
            Optional<Contributor> opt = this.contributorRepository.findById(id);
            return opt.orElse(null);
    }

    public List<ListContributorsDto> findContributors() {
        List<ListContributorsDto> contributors = new ArrayList<>();
        for (Contributor contributor : findAllOrderByLastName()) {
            ListContributorsDto dto = new ListContributorsDto();
            dto.setContributorFirstName(contributor.getFirstName());
            dto.setContributorLastName(contributor.getLastName());
            contributors.add(dto);
        }
        return contributors;
    }
}
