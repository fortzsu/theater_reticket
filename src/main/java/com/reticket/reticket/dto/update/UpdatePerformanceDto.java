package com.reticket.reticket.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UpdatePerformanceDto {

    private LocalDateTime modifiedDateTime;

    private boolean isAvailableOnline;

    private boolean isSeenOnline;

    private boolean isCancelled;

    public UpdatePerformanceDto(LocalDateTime modifiedDateTime, boolean isAvailableOnline, boolean isSeenOnline, boolean isCancelled) {
        this.modifiedDateTime = modifiedDateTime;
        this.isAvailableOnline = isAvailableOnline;
        this.isSeenOnline = isSeenOnline;
        this.isCancelled = isCancelled;
    }

}
