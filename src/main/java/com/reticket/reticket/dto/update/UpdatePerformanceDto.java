package com.reticket.reticket.dto.update;

import java.time.LocalDateTime;

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

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public boolean isAvailableOnline() {
        return isAvailableOnline;
    }

    public void setAvailableOnline(boolean availableOnline) {
        isAvailableOnline = availableOnline;
    }

    public boolean isSeenOnline() {
        return isSeenOnline;
    }

    public void setSeenOnline(boolean seenOnline) {
        isSeenOnline = seenOnline;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
