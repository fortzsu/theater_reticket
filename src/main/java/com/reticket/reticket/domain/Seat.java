package com.reticket.reticket.domain;


import com.reticket.reticket.domain.enums.SeatConditions;
import com.reticket.reticket.dto.save.AuditoriumPriceCategorySaveDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
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
    @JoinColumn(name = "auditorium")
    private Auditorium auditorium;

    public void setPriceCategoryNumber(Integer rowNum, List<AuditoriumPriceCategorySaveDto> list) {
        int i = 0;
        boolean flag = true;
        while (i < list.size() && flag) {
            if (rowNum <= list.get(i).getRowNumberWherePriceCategoryChanges()) {
                this.priceCategoryNumber = list.get(i).getPriceCategoryNumber() - 1;
                flag = false;
            } else if (rowNum > list.get(list.size() - 1).getRowNumberWherePriceCategoryChanges()) {
                this.priceCategoryNumber = list.size();
                flag = false;
            } else {
                i++;
            }
        }
    }

}
