package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.SeatConditions;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {

    private TicketRepository ticketRepository;
    private AuditoriumService auditoriumService;
    private SeatService seatService;
    private PerformanceService performanceService;
    private PlayService playService;
    private PriceService priceService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, AuditoriumService auditoriumService,
                         SeatService seatService,
                         PerformanceService performanceService, PlayService playService, PriceService priceService) {
        this.ticketRepository = ticketRepository;
        this.auditoriumService = auditoriumService;
        this.seatService = seatService;
        this.performanceService = performanceService;
        this.playService = playService;
        this.priceService = priceService;
    }

    public void generateTicketsToPerformance(Long performanceId, PerformanceSaveDto performanceSaveDto) {
        Play play = this.playService.findById(performanceSaveDto.getPlayId());
        Performance performance = this.performanceService.findPerformanceById(performanceId);
        Auditorium auditorium = this.auditoriumService.findAuditoriumByAuditoriumName(performance.getPlay().getAuditorium().getAuditoriumName());
        List<Seat> seats = this.seatService.findAllByAuditoriumId(auditorium);
        for (Seat seat : seats) {
            if (seat.getSeatCondition().equals(SeatConditions.AVAILABLE)) {
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
    private Price findPriceAmount(Integer priceCategory, List<Price> prices) {
        return prices.get(priceCategory - 1);
    }

    public List<Ticket> findTicketByAppUserId(AppUser appUser) {
        return this.ticketRepository.findAllByAppUser(appUser);
    }

    public Ticket findByTicketId(Long id) {
        Optional<Ticket> opt = this.ticketRepository.findById(id);
        return opt.orElse(null);
    }


}
