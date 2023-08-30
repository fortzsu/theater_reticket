package com.reticket.reticket.domain;


import com.reticket.reticket.domain.enums.SeatConditions;
import com.reticket.reticket.dto.save.AuditoriumPriceCategorySaveDto;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_number")
    private Integer seatNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_condition")
    private SeatConditions seatConditions;
    @Column(name = "price_category_number")
    private Integer priceCategoryNumber;
    @Column(name = "auditorium_row_number")
    private Integer auditoriumRowNumber;
    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditoriumId;

    public Seat() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatConditions getSeatCondition() {
        return seatConditions;
    }

    public void setSeatCondition(SeatConditions seatConditions) {
        this.seatConditions = seatConditions;
    }

    public Integer getPriceCategoryNumber() {
        return priceCategoryNumber;
    }


    public void setPriceCategoryNumber(Integer rowNum,
                                       List<AuditoriumPriceCategorySaveDto>
                                               list) {
        int i = 0;
        boolean flag = true;
        while (i < list.size() && flag) {
            if (rowNum <= list.get(i).getRowNumberWherePriceCategoryChanges()) {
                this.priceCategoryNumber = list.get(i).getPriceCategoryNumber();
                flag = false;
            } else if (rowNum > list.get(list.size() - 1).getRowNumberWherePriceCategoryChanges()) {
                this.priceCategoryNumber = list.size() + 1;
                flag = false;
            } else {
                i++;
            }
        }
    }


    public Integer getAuditoriumRowNumber() {
        return auditoriumRowNumber;
    }

    public void setAuditoriumRowNumber(Integer auditoriumRowNumber) {
        this.auditoriumRowNumber = auditoriumRowNumber;
    }

    public Auditorium getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(Auditorium auditoriumId) {
        this.auditoriumId = auditoriumId;
    }
}
