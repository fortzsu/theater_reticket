package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.list.InitFormDataToPlaySaveDto;
import com.reticket.reticket.dto.list.ListPlaysDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.ContributorsSaveForPlaySaveDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
import com.reticket.reticket.dto.update.UpdatePlayDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.exception.AuditoriumNotFoundException;
import com.reticket.reticket.exception.PlayNotFoundException;
import com.reticket.reticket.repository.PlayContributorTypeRepository;
import com.reticket.reticket.repository.PlayRepository;
import com.reticket.reticket.repository.PriceRepository;
import com.reticket.reticket.service.mapper.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlayService {


    private final PlayRepository playRepository;
    private final AuditoriumService auditoriumService;
    private final PriceRepository priceRepository;
    private final ContributorService contributorService;
    private final PlayContributorTypeRepository playContributorTypeRepository;

    public void save(PlaySaveDto playSaveDto) throws AuditoriumNotFoundException {
        Play play = new Play();
        updateValues(play, playSaveDto);
        this.playRepository.save(play);
        savePricesOfPlay(play.getId(), playSaveDto.getPrices());
        connectContributorWithPlayAndContributorType(play.getId(), playSaveDto.getContributorsSaveForPlaySaveDtoList());
    }

    private void updateValues(Play play, PlaySaveDto playSaveDto) throws AuditoriumNotFoundException {
        Auditorium auditorium = this.auditoriumService.findAuditoriumById(playSaveDto.getAuditoriumId());
        play.setAuditorium(auditorium);
        play.setIsArchived(false);
        play.setPlayType(playSaveDto.getPlayType());
        MapperService.playDtoToEntity(playSaveDto, play);
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

    public ListWrapper<ListPlaysDto> listPlays(PageableDto dto) {
        ListWrapper<ListPlaysDto> result = new ListWrapper<>();
        result.addAll(this.playRepository.findAllPlay(PageRequest.of(dto.getPage(), dto.getPageSize())));
        return result;
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

    public void updatePlay(UpdatePlayDto updatePlayDto, Long id) {
        Play play = findById(id);
        updatePlayData(updatePlayDto, play);
    }

    public void deletePlay(Long id) {
        Play play = findById(id);
        play.setIsArchived(true);
    }

    public Play findById(Long id) {
        Optional<Play> opt = this.playRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new PlayNotFoundException();
        }
    }

    public InitFormDataToPlaySaveDto fillInitData(String auditoriumId) {
        InitFormDataToPlaySaveDto initData = new InitFormDataToPlaySaveDto();
        initData.setContributorsList(this.contributorService.findContributorsToPlay());
        Auditorium auditorium = this.auditoriumService.findAuditoriumById(Long.valueOf(auditoriumId));
        initData.setNumberOfPriceCategories(auditorium.getNumberOfPriceCategories());
        return initData;
    }
}
