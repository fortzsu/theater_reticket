package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.SeatConditions;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.exception.TicketNotFoundException;
import com.reticket.reticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketService {


    private final TicketRepository ticketRepository;
    private final PlayService playService;
    private final AuditoriumService auditoriumService;
    private final SeatService seatService;
    private final PriceService priceService;

    public List<Ticket> findTicketByAppUserId(AppUser appUser) {
        return this.ticketRepository.findAllByAppUser(appUser);
    }

    public Ticket findByTicketId(Long id) {
        Optional<Ticket> opt = this.ticketRepository.findById(id);
        if(opt.isPresent()) {
            return opt.get();
        } else {
            throw new TicketNotFoundException();
        }
    }

    public void searchSeatsForPerformance(Performance performance, PerformanceSaveDto performanceSaveDto) {
        Play play = this.playService.findById(performanceSaveDto.getPlayId());
        if (play != null) {
            String auditoriumName = performance.getPlay().getAuditorium().getAuditoriumName();
            Auditorium auditorium = this.auditoriumService.findAuditoriumByAuditoriumName(auditoriumName);
            List<Seat> seats = this.seatService.findAllByAuditoriumId(auditorium);
            generateTicketsToPerformance(seats, performance, play);
        }
    }

    private void generateTicketsToPerformance(List<Seat> seats, Performance performance, Play play) {
        for (Seat seat : seats) {
            if (seat.getSeatConditions().equals(SeatConditions.AVAILABLE)) {
                Ticket ticket = new Ticket();
                ticket.setPerformance(performance);
                ticket.setSeat(seat);
                List<Price> prices = this.priceService.findAllByPlay(play);
                Price price = findPriceAmount(seat.getPriceCategoryNumber(), prices);
                ticket.setPrice(price);
                ticket.setTicketCondition(TicketCondition.FOR_SALE);
                ticket.setGeneratedAt(LocalDateTime.now());
                this.ticketRepository.save(ticket);
            }
        }
    }

    private Price findPriceAmount(Integer seatPriceCategory, List<Price> prices) {
        return prices.get(seatPriceCategory);
    }


}
