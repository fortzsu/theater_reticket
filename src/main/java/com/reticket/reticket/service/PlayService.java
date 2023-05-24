package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.PlayType;
import com.reticket.reticket.dto.list.ListPlaysDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.ContributorsSaveForPlaySaveDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
import com.reticket.reticket.repository.PlayContributorTypeRepository;
import com.reticket.reticket.repository.PlayRepository;
import com.reticket.reticket.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<Play> save(List<PlaySaveDto> playSaveDtoList) {
        List<Play> plays = new ArrayList<>();
        for (PlaySaveDto dto : playSaveDtoList) {
            Play play = new Play();
            this.playRepository.save(updateValues(play, dto));
            savePricesOfPlay(play.getId(), dto.getPrices());
            connectContributorWithPlayAndContributorType(play.getId(), dto.getContributorsSaveForPlaySaveDtoList());
        }
        return plays;
    }

    private Play updateValues(Play play, PlaySaveDto playSaveDto) {
        play.setPlayName(playSaveDto.getPlayName());
        play.setPlot(playSaveDto.getPlot());
        play.setPremiere(playSaveDto.getPremiere().plusHours(1));
        play.setArchived(false);
        play.setAuditorium(this.auditoriumService.findAuditoriumById(playSaveDto.getAuditoriumId()));
        play.setPlayType(playSaveDto.getPlayType());
        return play;
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
        for(Integer price: prices) {
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
                .stream().map(ListPlaysDto::new).collect(Collectors.toList());
    }
}
