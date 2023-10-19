package com.reticket.reticket.service;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Seat;
import com.reticket.reticket.domain.enums.SeatConditions;
import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SeatService {

    private final SeatRepository seatRepository;

    public void generateSeats(Auditorium auditorium, AuditoriumSaveDto auditoriumSaveDto) {
        for (int i = 1; i <= auditoriumSaveDto.getNumberOfRows(); i++) {
            for (int j = 1; j <= auditoriumSaveDto.getSeatNumberPerAuditoriumRow(); j++) {
                Seat seat = new Seat();
                fillSeatData(i, j, auditoriumSaveDto, auditorium, seat);
            }
        }
    }

    private void fillSeatData(int i, int j, AuditoriumSaveDto auditoriumSaveDto, Auditorium auditorium, Seat seat) {
        seat.setSeatConditions(SeatConditions.AVAILABLE);
        seat.setAuditoriumRowNumber(i);
        seat.setSeatNumber(j);
        seat.setPriceCategoryNumber(i, auditoriumSaveDto.getAuditoriumPriceCategorySaveDtoList());
        seat.setAuditorium(auditorium);
        seatRepository.save(seat);
    }

    public List<Seat> findAllByAuditoriumId(Auditorium auditorium) {
        return this.seatRepository.findAllByAuditorium(auditorium);
    }
}
