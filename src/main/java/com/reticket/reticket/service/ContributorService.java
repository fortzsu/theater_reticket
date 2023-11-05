package com.reticket.reticket.service;

import com.reticket.reticket.domain.Contributor;
import com.reticket.reticket.dto.list.ListContributorsDto;
import com.reticket.reticket.dto.list.ListDetailedContributorsDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.exception.ContributorNotFoundException;
import com.reticket.reticket.repository.ContributorRepository;
import com.reticket.reticket.repository.PlayContributorTypeRepository;
import com.reticket.reticket.service.mapper.MapStructService;
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
    private final MapStructService mapStructService;


    public void save(ContributorSaveDto contributorSaveDto) {
        Contributor contributor = mapStructService.createContributorEntityFromDto(contributorSaveDto);
        this.contributorRepository.save(contributor);
    }

    public void update(ContributorSaveDto contributorSaveDto, Long id) {
        Contributor contributor = this.findById(id);
        if (contributor != null) {
            this.mapStructService.updateContributorEntityFromDto(contributor, contributorSaveDto);
        }
    }

    public List<Contributor> findAllOrderByLastName() {
        return this.contributorRepository.findByOrderByLastName();
    }

    public Contributor findById(Long id) {
        Optional<Contributor> opt = this.contributorRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ContributorNotFoundException();
        }
    }

    public List<ListContributorsDto> findContributorsToPlay() {
        List<ListContributorsDto> contributors = new ArrayList<>();
        for (Contributor contributor : findAllOrderByLastName()) {
            ListContributorsDto dto = new ListContributorsDto();
            dto.setContributorFirstName(contributor.getFirstName());
            dto.setContributorLastName(contributor.getLastName());
            contributors.add(dto);
        }
        return contributors;
    }

    public ListWrapper<ListDetailedContributorsDto> listContributors() {
        List<ListDetailedContributorsDto> contributors = this.playContributorTypeRepository.findAllContributors();
        ListWrapper<ListDetailedContributorsDto> result = new ListWrapper<>();
        for (ListDetailedContributorsDto dto : contributors) {
            dto.setPlayNames(
                    this.playContributorTypeRepository.findAllPlaysByContributors(dto.getContributorFirstName(),
                            dto.getContributorLastName()));
            result.addItem(dto);
        }
        return result;
    }
}
