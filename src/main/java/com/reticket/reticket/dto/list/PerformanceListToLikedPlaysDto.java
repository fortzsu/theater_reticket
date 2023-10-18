package com.reticket.reticket.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PerformanceListToLikedPlaysDto {

    private LocalDateTime performanceDateTime;

}
