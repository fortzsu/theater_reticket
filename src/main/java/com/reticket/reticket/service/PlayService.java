package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.list.ListPlaysDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.ContributorsSaveForPlaySaveDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
import com.reticket.reticket.dto.update.UpdatePlayDto;
import com.reticket.reticket.exception.AuditoriumNotFoundException;
import com.reticket.reticket.repository.PlayContributorTypeRepository;
import com.reticket.reticket.repository.PlayRepository;
import com.reticket.reticket.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayService {

    private final PlayRepository playRepository;
    private final AuditoriumService auditoriumService;
    private final PriceRepository priceRepository;
    private final ContributorService contributorService;
    private final PlayContributorTypeRepository playContributorTypeRepository;

    @Autowired
    public PlayService(PlayRepository playRepository, AuditoriumService auditoriumService,
                       PriceRepository priceRepository, ContributorService contributorService,
                       PlayContributorTypeRepository playContributorTypeRepository) {
        this.playRepository = playRepository;
        this.auditoriumService = auditoriumService;
        this.priceRepository = priceRepository;
        this.contributorService = contributorService;
        this.playContributorTypeRepository = playContributorTypeRepository;
    }

    public void save(List<PlaySaveDto> playSaveDtoList) throws AuditoriumNotFoundException {
        for (PlaySaveDto dto : playSaveDtoList) {
            Play play = new Play();
            updateValues(play, dto);
            this.playRepository.save(play);
            savePricesOfPlay(play.getId(), dto.getPrices());
            connectContributorWithPlayAndContributorType(play.getId(), dto.getContributorsSaveForPlaySaveDtoList());
        }
    }

    private void updateValues(Play play, PlaySaveDto playSaveDto) throws AuditoriumNotFoundException {
        Auditorium auditorium = this.auditoriumService.findAuditoriumById(playSaveDto.getAuditoriumId());
        updateData(playSaveDto, play, auditorium);
    }

    private void updateData(PlaySaveDto playSaveDto, Play play, Auditorium auditorium) {
        play.setAuditorium(auditorium);
        play.setPlayName(playSaveDto.getPlayName());
        play.setPlot(playSaveDto.getPlot());
        play.setPremiere(playSaveDto.getPremiere().plusHours(1));
        play.setIsArchived(false);
        play.setPlayType(playSaveDto.getPlayType());
    }

    private void connectContributorWithPlayAndContributorType(Long playId, List<ContributorsSaveForPlaySaveDto> contributorsSaveForPlaySaveDtoList) {
        for (ContributorsSaveForPlaySaveDto dto : contributorsSaveForPlaySaveDtoList) {
            Contributor contributor = this.contributorService.findById(dto.getContributorId());
            Play play = this.findById(playId);
            PlayContributorTypes playContributorTypes = new PlayContributorTypes();
            playContributorTypes.setContributor(contributor);
            playContributorTypes.setPlay(play);
            playContributorTypes.setContributorType(dto.getContributorType());
            this.playContributorTypeRepository.save(playContributorTypes);
        }
    }

    private void savePricesOfPlay(Long id, List<Integer> prices) {
        Play play = findById(id);
        for (Integer price : prices) {
            Price newPrice = new Price();
            newPrice.setAmount(price);
            newPrice.setPlay(play);
            this.priceRepository.save(newPrice);
        }
    }

    public Play findById(Long id) {
        Optional<Play> opt = this.playRepository.findById(id);
        return opt.orElse(null);
    }

    public List<ListPlaysDto> listPlays(PageableDto dto) {
        return this.playRepository.findAllPlay(PageRequest.of(dto.getPage(), dto.getPageSize()))
                .stream().map(ListPlaysDto::new).collect(Collectors.toList()); //TODO
    }

    public boolean updatePlay(UpdatePlayDto updatePlayDto, Long id) {
        Play play = findPlayById(id);
        if (play != null) {
            updatePlayData(updatePlayDto, play);
            return true;
        } else {
            return false;
        }
    }

    private void updatePlayData(UpdatePlayDto updatePlayDto, Play play) {
        if (updatePlayDto.getPlayName() != null) {
            play.setPlayName(updatePlayDto.getPlayName());
        }
        if (updatePlayDto.getPlot() != null) {
            play.setPlot(updatePlayDto.getPlot());
        }
        if (!play.getAuditorium().getId().equals(updatePlayDto.getAuditoriumId())) {
            Auditorium auditorium = this.auditoriumService.findAuditoriumById(updatePlayDto.getAuditoriumId());
            play.setAuditorium(auditorium);
        }
    }

    public boolean deletePlay(Long id) {
        Play play = findPlayById(id);
        if (play != null) {
            play.setIsArchived(true);
            return true;
        } else {
            return false;
        }
    }

    private Play findPlayById(Long id) {
        return this.findById(id);
    }
}
