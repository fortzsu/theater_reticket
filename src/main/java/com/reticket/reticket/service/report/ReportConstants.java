package com.reticket.reticket.service.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReportConstants {

    ORIGINAL_DATE_TIME("originalPerformanceDateTime"),
    TICKET_CONDITION("ticketCondition"),
    ID("id"),
    THEATER("theater"),
    AUDITORIUM("auditorium");

    private final String displayName;

}
