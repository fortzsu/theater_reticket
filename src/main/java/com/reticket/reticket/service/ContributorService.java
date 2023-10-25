package com.reticket.reticket.service;

import com.reticket.reticket.domain.Contributor;
import com.reticket.reticket.dto.list.ListContributorsDto;
import com.reticket.reticket.dto.list.ListDetailedContributorsDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.repository.ContributorRepository;
import com.reticket.reticket.repository.PlayContributorTypeRepository;
import com.reticket.reticket.service.mapper.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContributorService {

    private final ContributorRepository contributorRepository;
    private final PlayContributorTypeRepository playContributorTypeRepository;


    public void save(List<ContributorSaveDto> contributorSaveDtoList) {
        for (ContributorSaveDto saveDto : contributorSaveDtoList) {
            Contributor contributor = new Contributor();
            MapperService.contributorDtoToEntity(contributor, saveDto);
            this.contributorRepository.save(contributor);
        }
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

    public List<ListDetailedContributorsDto> listContributors() {
        List<ListDetailedContributorsDto> resultSet = this.playContributorTypeRepository.findAllContributors();
        for (ListDetailedContributorsDto dto : resultSet) {
            dto.setPlayNames(
                    this.playContributorTypeRepository.findAllPlaysByContributors(dto.getContributorFirstName(), dto.getContributorLastName()));
        }
        return resultSet;
    }

    public void update(ContributorSaveDto contributorSaveDto, Long id) {
        Contributor contributor = this.findById(id);
        if (contributor != null) {
            MapperService.contributorDtoToEntity(contributor, contributorSaveDto);
        }
    }
}
