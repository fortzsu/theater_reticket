package com.reticket.reticket;

import com.reticket.reticket.controller.*;
import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.domain.enums.SeatConditions;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import com.reticket.reticket.dto.update.UpdatePlayDto;
import com.reticket.reticket.repository.*;
import com.reticket.reticket.security.repository_service.UserRoleRepository;
import com.reticket.reticket.service.*;
import com.reticket.reticket.service.performance.GenerateTicketToPerformanceService;
import com.reticket.reticket.service.performance.PerformanceService;
import com.reticket.reticket.service.report.ReportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ReticketTest {

    @Autowired
    private GenerateTicketToPerformanceService generateTicketToPerformanceService;
    @Autowired
    private TicketActionService ticketActionService;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private AuditoriumController auditoriumController;
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private AuditoriumRepository auditoriumRepository;
    @Autowired
    private AddressController addressController;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContributorController contributorController;
    @Autowired
    private ContributorService contributorService;
    @Autowired
    private ContributorRepository contributorRepository;
    @Autowired
    private PlayController playController;
    @Autowired
    private PlayService playService;
    @Autowired
    private PlayRepository playRepository;
    @Autowired
    private PerformanceController performanceController;
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AppUserController appUserController;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private TicketActionController ticketActionController;
    @Autowired
    private ReportService reportService;
    @Autowired
    private TicketActionFollowerRepository ticketActionFollowerRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private AppUserActionService appUserActionService;


    private static boolean init = true;

    @Before
    public void init() {
        if (init) {
            SetDatabaseForTest.init(generateTicketToPerformanceService, ticketActionService,
                    theaterService, auditoriumService, addressService, contributorService,
                    appUserService, playService);
            init = false;
        }
    }

    // -----------------------  USERROLE TESTS  -----------------------



    @Test
    public void testUserRole_findAll() {
        Assert.assertEquals(4, this.userRoleRepository.findAll().size());
    }

    @Test
    public void testUserRole_findFirst_authoritiesList() {
        Assert.assertEquals(5, this.userRoleRepository.findAll().get(1).getAuthorities().size());
    }


    // -----------------------  USERROLE TESTS  -----------------------

    // -----------------------  GOOGLE TESTS  -----------------------

    @Test
    public void testReportPerformances_Google_soldTickets_byTheatre() {
        FilterReportDto reportFilterDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, true);
        Assert.assertEquals(3, this.reportService.report(reportFilterDto).getReportResultPerformancesDtos().size());
    }

    @Test
    public void testReport_Google_soldTickets_byTheatre_ticketAmount() {
        FilterReportDto reportFilterDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, true);
        Assert.assertEquals(1940, (long) this.reportService.report(reportFilterDto).getTicketAmount());
    }

    // -----------------------  GOOGLE TESTS  -----------------------
    // -----------------------  REPORT TESTS  -----------------------
    @Test
    public void testReport_soldTickets_byTheater_ticketAmount() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
        Assert.assertEquals(1940, (long) this.reportService.report(filterReportDto).getTicketAmount());
    }

    @Test
    public void testReportPerformances_soldTickets_byTheater() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(3, this.reportService.report(filterReportDto).getReportResultPerformancesDtos().size());
    }

    @Test
    public void testReportPerformances_soldTickets_byTheater_testFirst_amount() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(1030, (long) this.reportService.report(filterReportDto).
                getReportResultPerformancesDtos().get(0).getSumOfTickets());
    }

    @Test
    public void testReportPerformances_soldTickets_byTheater_testFirst_returned() {
        FilterReportDto filterReportDto = new FilterReportDto("RETURNED", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(200, (long) this.reportService.report(filterReportDto).
                getReportResultPerformancesDtos().get(0).getSumOfTickets());
    }


    @Test
    public void testReportPerformances_soldTickets_byTheater_testFirst_count() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(14, (long)
                this.reportService.report(filterReportDto).getReportResultPerformancesDtos().get(0).getCountOfTicket());
    }

    @Test
    public void testReportPerformances_soldTickets_byTheater_testSecond_amount() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(580, (long) this.reportService.report(filterReportDto).
                getReportResultPerformancesDtos().get(1).getSumOfTickets());
    }

    @Test
    public void testReportPerformances_soldTickets_byTheater_testSecond_count() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(8, (long) this.reportService.report(filterReportDto).
                getReportResultPerformancesDtos().get(1).getCountOfTicket());
    }

    @Test
    public void testReportPerformances_soldTickets_byTheater_testThird_amount() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(330, (long) this.reportService.report(filterReportDto).getReportResultPerformancesDtos().get(2).getSumOfTickets());
    }

    @Test
    public void testReportPerformances_soldTickets_byTheater_testThird_count() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
        Assert.assertEquals(6, (long) this.reportService.report(filterReportDto).getReportResultPerformancesDtos().get(2).getCountOfTicket());
    }

    @Test
    public void testReport_soldTickets_byTheater_ticketCount() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
        Assert.assertEquals(28, (long) this.reportService.report(filterReportDto).getTicketCount());
    }


    @Test
    public void testReport_soldTickets_byTheater_outOfDate() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), false, false);
        Assert.assertNull(this.reportService.report(filterReportDto).getTicketAmount());
    }

    @Test
    public void testReport_soldTickets_byTheater_testStartDate() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1, 0, 0);
        Assert.assertEquals(startDate, this.reportService.report(filterReportDto).getStart());
    }

    @Test
    public void testReport_soldTickets_byTheater_testEndDate() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10, 23, 59);
        Assert.assertEquals(endDate, this.reportService.report(filterReportDto).getEnd());
    }

    @Test
    public void testReport_soldTickets_byTheater_theaterName() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), false, false);
        Assert.assertEquals("The Royal National Theater", this.reportService.report(filterReportDto).getSearchPathName());
    }

    @Test
    public void testReport_soldTickets_byAuditorium_auditoriumName() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "auditorium", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
        Assert.assertEquals("Oliver Theater", this.reportService.report(filterReportDto).getSearchPathName());
    }

    @Test
    public void testReport_soldTickets_byPlay_playName() {
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "play", 1L,
                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), false, false);
        Assert.assertEquals("Standing at the Sky's Edge", this.reportService.report(filterReportDto).getSearchPathName());
    }


    // -----------------------  REPORT TESTS  -----------------------

    // -----------------------  SEARCH PERFORMANCES TESTS  -----------------------


    @Test
    public void testSearchFilteredPerformances_byPlayType_Comedy_findAll() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "Comedy", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 10));
        Assert.assertEquals(2, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlayType_Comedy_withWrongDate() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "Comedy", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 9),
                new PageableDto(0, 10));
        Assert.assertEquals(0, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlayType_Drama_findAll() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "Drama", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 16));
        Assert.assertEquals(4, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlayType_Drama_wrongDate() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "Drama", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 15,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 16));
        Assert.assertEquals(0, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlayType_Drama_emptyPage() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "Drama", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(1, 8));
        Assert.assertEquals(0, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlayType_Musical_findAll() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "Musical", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 16));
        Assert.assertEquals(2, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlayType_Musical_forOneDate() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "Musical", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 3,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 3),
                new PageableDto(0, 16));
        Assert.assertEquals(1, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlay_firstPlay() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "play", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 10));
        Assert.assertEquals(2, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlay_firstPlay_withWrongDate() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "play", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 10));
        Assert.assertEquals(0, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlay_lastPlay() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "play", 8L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 10));
        Assert.assertEquals(2, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byPlay_lastPlay_withWrongDate() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "play", 8L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10),
                new PageableDto(0, 10));
        Assert.assertEquals(0, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }


    @Test
    public void testSearchFilteredPerformances_byAuditorium_findAll() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 10));
        Assert.assertEquals(4, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byAuditorium_firstPage() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 2));
        Assert.assertEquals(2, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byAuditorium_filterForOneDay() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1),
                new PageableDto(0, 2));
        Assert.assertEquals(1, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byAuditorium_filterForOneDay_EmptySecondPage() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1),
                new PageableDto(1, 2));
        Assert.assertEquals(0, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byLastAuditorium_findAll() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "auditorium", 4L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 16));
        Assert.assertEquals(6, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byLastAuditorium_findAll_checkFirst() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "auditorium", 4L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 16));
        Assert.assertEquals("Lemons Lemons Lemons Lemons Lemons",
                this.performanceService.searchFilteredPerformances(dto).get().toList().get(0).getPlayName());
    }


    @Test
    public void testSearchFilteredPerformances_byLastAuditorium_findAll_checkLast() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "auditorium", 4L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(2, 2));
        Assert.assertEquals("Dr. Semmelweis",
                this.performanceService.searchFilteredPerformances(dto).get().toList().get(1).getPlayName());
    }


    @Test
    public void testSearchFilteredPerformances_byTheatre_firstPage() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 5),
                new PageableDto(0, 5));
        Assert.assertEquals(5, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byTheatre_secondPage() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 5),
                new PageableDto(1, 2));
        Assert.assertEquals(2, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byTheatre_first() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 5),
                new PageableDto(0, 5));
        Assert.assertEquals("Standing at the Sky's Edge",
                this.performanceService.searchFilteredPerformances(dto).get().toList().get(0).getPlayName());
    }

    @Test
    public void testSearchFilteredPerformances_byTheatre_last() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(1, 5));
        Assert.assertEquals("Romeo and Julie",
                this.performanceService.searchFilteredPerformances(dto).get().toList().get(4).getPlayName());
    }

    @Test
    public void testSearchFilteredPerformances_byTheatre_checkEmptyPage() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(3, 5));
        Assert.assertEquals(0,
                this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    @Test
    public void testSearchFilteredPerformances_byTheatre_checkAuditorium() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(1, 5));
        Assert.assertEquals("Dorfman Theater",
                this.performanceService.searchFilteredPerformances(dto).get().toList().get(4).getAuditoriumName());
    }

    @Test
    public void testSearchFilteredPerformances_byTheatre_AllInOnePage() {
        FilterPerformancesDto dto = new FilterPerformancesDto(
                "theater", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
                LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
                new PageableDto(0, 16));
        Assert.assertEquals(10, this.performanceService.searchFilteredPerformances(dto).get().toList().size());
    }

    // -----------------------  SEARCH PERFORMANCES TESTS  -----------------------

    // -----------------------  TICKET ACTIONS TESTS  -----------------------

    @Test
    public void testCreateFollower_allFromRepository() {
        Assert.assertEquals(33, this.ticketActionFollowerRepository.findAll().size());
    }

    @Test
    public void testCreateFollower_testUser() {
        Assert.assertEquals(5, (long) this.ticketActionFollowerRepository.findAll().get(0).getAppUser().getId());
    }

    @Test
    public void testCreateFollower_testTicketCondition() {
        Assert.assertEquals(TicketCondition.RESERVED, this.ticketActionFollowerRepository.findAll().get(3).getTicketCondition());
    }

    @Test
    public void testCreateFollower_testTicketCondition_RETURNED() {
        Assert.assertEquals(TicketCondition.RETURNED, this.ticketActionFollowerRepository.findAll().get(10).getTicketCondition());
    }

    @Test
    public void testReturnTest_withAppUser() {
        Assert.assertEquals(TicketCondition.RETURNED, this.ticketRepository.findAll().get(10).getTicketCondition());
    }

    @Test
    public void testReturnTest_withAppUser_listTickets() {
        Assert.assertEquals(3, this.appUserActionService.listTickets("guestThree").size());
    }

    @Test
    public void testReserveTest_withAppUser_ticketList() {
        Assert.assertEquals(6, this.appUserActionService.listTickets("guestTwo").size());
    }

    @Test
    public void testReserveTest_withAppUser_sold() {
        Assert.assertEquals("Sold", this.appUserActionService.listTickets("guestTwo").get(0).getTicketCondition());
    }

    @Test
    public void testReserveTest_withAppUser_reserved() {
        Assert.assertEquals("Reserved", this.appUserActionService.listTickets("guestTwo").get(3).getTicketCondition());
    }

    @Test
    public void testReserveTest_withAppUser_playName() {
        Assert.assertEquals("Standing at the Sky's Edge", this.appUserActionService.listTickets("guestTwo").get(0).getPlayName());
    }

    @Test
    public void testReserveTest_withAppUser_theatreName() {
        Assert.assertEquals("The Royal National Theater", this.appUserActionService.listTickets("guestTwo").get(0).getTheatreName());
    }

    @Test
    public void testReserveTest_withAppUser_ticketPrice() {
        Assert.assertEquals(100, (long) this.appUserActionService.listTickets("guestTwo").get(0).getTicketPrice());
    }


    // -----------------------  TICKET ACTIONS TESTS  -----------------------

    // -----------------------  APPUSER TESTS  -----------------------

    @Test
    public void testSavedAppUser_fromRepository_findAll() {
        List<AppUser> list = this.appUserRepository.findAll();
        for (AppUser appUser : list) {
            System.out.println(appUser.getUsername());
        }
        Assert.assertEquals(7, this.appUserRepository.findAll().size());
    }

    @Test
    public void testSavedAppUser_fromRepository_checkUserName() {
        Assert.assertEquals("super", this.appUserRepository.findAll().get(0).getUsername());
    }

    @Test
    public void testSavedAppUser_fromRepository_checkUserName_Last() {
        Assert.assertEquals("guestFour", this.appUserRepository.findAll().get(6).getUsername());
    }

    @Test
    public void testSaveAppUser_listTickets_Empty() {
        Assert.assertEquals(0, this.appUserActionService.listTickets("guestOne").size());
    }

    @Test
    public void testSaveAppUser_likedPlays() {
        Assert.assertEquals(0, this.appUserActionService.listLikedPlays("guestOne").size());
        this.appUserActionService.likePlay("guestOne", 1L);
        Assert.assertEquals(1, this.appUserActionService.listLikedPlays("guestOne").size());
    }


    // -----------------------  APPUSER TESTS  -----------------------

    // -----------------------  TICKET TESTS  -----------------------

    @Test
    public void testSavedTickets() {
        Assert.assertEquals(5678, this.ticketRepository.findAll().size());
    }

    @Test
    public void testSavedTickets_TicketCondition() {
        Assert.assertEquals(TicketCondition.SOLD, this.ticketRepository.findAll().get(100).getTicketCondition());
    }

    @Test
    public void testSavedTickets_TicketAmount_firstAuditorium_checkFirstCategory() {
        Assert.assertEquals(100, (long) this.ticketRepository.findAll().get(89).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_firstAuditorium_checkSecondCategory() {
        Assert.assertEquals(85, (long) this.ticketRepository.findAll().get(540).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_firstAuditorium_checkThirdCategory() {
        Assert.assertEquals(85, (long) this.ticketRepository.findAll().get(1080).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_firstAuditorium_checkFourthCategory() {
        Assert.assertEquals(50, (long) this.ticketRepository.findAll().get(1170).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_firstAuditorium_checkFifthCategory() {
        Assert.assertEquals(20, (long) this.ticketRepository.findAll().get(1799).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_secondAuditorium_checkFirstCategory() {
        Assert.assertEquals(20, (long) this.ticketRepository.findAll().get(2107).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_secondAuditorium_checkSecondCategory() {
        Assert.assertEquals(50, (long) this.ticketRepository.findAll().get(2290).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_secondAuditorium_checkThirdCategory() {
        Assert.assertEquals(70, (long) this.ticketRepository.findAll().get(2514).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_secondAuditorium_checkFourthCategory() {
        Assert.assertEquals(95, (long) this.ticketRepository.findAll().get(2724).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_thirdAuditorium_checkFirstCategory() {
        Assert.assertEquals(80, (long) this.ticketRepository.findAll().get(3241).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_thirdAuditorium_checkSecondCategory() {
        Assert.assertEquals(55, (long) this.ticketRepository.findAll().get(3092).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_thirdAuditorium_checkThirdCategory() {
        Assert.assertEquals(25, (long) this.ticketRepository.findAll().get(3142).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_fourthAuditorium_checkFirstCategory() {
        Assert.assertEquals(85, (long) this.ticketRepository.findAll().get(3332).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_fourthAuditorium_checkSecondCategory() {
        Assert.assertEquals(70, (long) this.ticketRepository.findAll().get(4998).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_fourthAuditorium_checkThirdCategory() {
        Assert.assertEquals(60, (long) this.ticketRepository.findAll().get(4810).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_TicketAmount_fourthAuditorium_checkFourthCategory() {
        Assert.assertEquals(25, (long) this.ticketRepository.findAll().get(4420).getPrice().getAmount());
    }

    @Test
    public void testSavedTickets_checkPerformanceId() {
        Assert.assertEquals(1, (long) this.ticketRepository.findAll().get(0).getPerformance().getId());
    }

    // -----------------------  TICKET TESTS  -----------------------

    // -----------------------  PRICE TESTS  -----------------------

    @Test
    public void testSavedPrices() {
        Assert.assertEquals(33, this.priceRepository.findAll().size());
    }

    @Test
    public void testSavedPrices_checkFirst() {
        Assert.assertEquals(100, (long) this.priceRepository.findAll().get(0).getAmount());
    }

    @Test
    public void testSavedPrices_checkLast() {
        Assert.assertEquals(25, (long) this.priceRepository.findAll().get(32).getAmount());
    }


    // -----------------------  PRICE TESTS  -----------------------

    // -----------------------  SEAT TESTS  -----------------------


    @Test
    public void testGenerateSeats() {
        Assert.assertEquals(1299, this.seatRepository.findAll().size());
    }

    @Test
    public void testGenerateSeats_seatCondition() {
        Assert.assertEquals(SeatConditions.AVAILABLE, this.seatRepository.findAll().get(0).getSeatConditions());
    }

    @Test
    public void testGenerateSeats_seatNumber() {
        Assert.assertEquals(1, (long) this.seatRepository.findAll().get(0).getSeatNumber());
        Assert.assertEquals(2, (long) this.seatRepository.findAll().get(1).getSeatNumber());
        Assert.assertEquals(3, (long) this.seatRepository.findAll().get(2).getSeatNumber());
    }

    @Test
    public void testGenerateSeats_auditoriumRowNumber() {
        Assert.assertEquals(1, (long) this.seatRepository.findAll().get(0).getAuditoriumRowNumber());
        Assert.assertEquals(1, (long) this.seatRepository.findAll().get(2).getAuditoriumRowNumber());
        Assert.assertEquals(2, (long) this.seatRepository.findAll().get(19).getAuditoriumRowNumber());
        Assert.assertEquals(5, (long) this.seatRepository.findAll().get(87).getAuditoriumRowNumber());
    }

    // -----------------------  SEAT TESTS  -----------------------

    // -----------------------  PERFORMANCE TESTS  -----------------------

    @Test
    public void testUpdatePerformance() {
        PerformanceSaveDto original = new PerformanceSaveDto(LocalDateTime.of(
                LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 17, 20, 0), 1L);
        this.performanceService.save(List.of(original));
        UpdatePerformanceDto update = new UpdatePerformanceDto(LocalDateTime.of(
                LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 18, 20, 0), false, false, true);
        this.performanceService.updatePerformance(update, 17L);
        Assert.assertEquals(LocalDateTime.of(
                LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 18, 20, 0), this.performanceService.findPerformanceById(17L).getNewDateTime());
        this.performanceService.findPerformanceById(17L).setPlay(null);
        this.performanceRepository.deleteById(17L);
    }

    @Test
    public void testSavePerformance_checkDateTime_fromService() {
        Assert.assertEquals(19, this.performanceService.findPerformanceById(8L).getOriginalPerformanceDateTime().getHour());
    }

    @Test
    public void testSavePerformance_findByIdLast_fromService() {
        Assert.assertEquals(8, (long) this.performanceService.findPerformanceById(16L).getPlay().getId());
    }

    @Test
    public void testSavePerformance_findByIdFirst_fromService() {
        Assert.assertEquals(1, (long) this.performanceService.findPerformanceById(1L).getPlay().getId());
    }

    @Test
    public void testSavePerformance_checkListSize_fromRepository() {
        Assert.assertEquals(16, this.performanceRepository.findAll().size());
    }

    // -----------------------  PERFORMANCE TESTS  -----------------------

    // -----------------------  PLAY TESTS  -----------------------

    @Test
    public void testDeletePlay() {
        this.playService.deletePlay(8L);
        Assert.assertEquals(true, this.playService.findById(8L).getIsArchived());
    }


    @Test
    public void testUpdatePlay() {
        UpdatePlayDto updatePlayDto = new UpdatePlayDto(null, "This is a new plot for the play.", 1L);
        this.playService.updatePlay(updatePlayDto, 1L);
        Assert.assertEquals("This is a new plot for the play.", this.playService.findById(1L).getPlot());
        Assert.assertEquals("Standing at the Sky's Edge", this.playService.findById(1L).getPlayName());
        Assert.assertEquals(1L, (long) this.playService.findById(1L).getAuditorium().getId());
    }

    @Test
    public void testListPlays() {
        Assert.assertEquals(8, this.playService.listPlays(new PageableDto(0, 8)).size());
    }

    @Test
    public void testSavePlay_findById_fromService_false() {
        Assert.assertNull(this.playService.findById(12L));
    }

    @Test
    public void testSavePlay_findByIdLast_fromService() {
        Assert.assertEquals("Dr. Semmelweis", this.playService.findById(8L).getPlayName());
    }

    @Test
    public void testSavePlay_findByIdFirst_fromService() {
        Assert.assertEquals("Standing at the Sky's Edge", this.playService.findById(1L).getPlayName());
    }

    @Test
    public void testSavePlay_checkListSize_fromRepository() {
        Assert.assertEquals(8, this.playRepository.findAll().size());
    }

    // -----------------------  CONTRIBUTOR TESTS  -----------------------

    @Test
    public void testUpdateContributor() {
        ContributorSaveDto original = new ContributorSaveDto("testContrFirst", "testContrLast", "Intro");
        this.contributorService.save(List.of(original));
        ContributorSaveDto updated = new ContributorSaveDto("ContrFirst", "testContrLast", "Intro");
        this.contributorService.update(updated, 17L);
        Assert.assertEquals("ContrFirst", this.contributorRepository.findAll().get(16).getFirstName());
        this.contributorRepository.deleteById(17L);
    }

    @Test
    public void testListContributors_listSize() {
        Assert.assertEquals(16, this.contributorService.listContributors().size());
    }

    @Test
    public void testListContributors_listSize_firstPlays() {
        Assert.assertEquals(3, this.contributorService.listContributors().get(0).getPlayNames().size());
    }

    @Test
    public void testListContributors_names() {
        Assert.assertEquals("HaroldPTheatre", this.contributorService.listContributors().get(0).getContributorFirstName());
    }


    @Test
    public void testSaveContributors_checkLastByName_fromService() {
        Assert.assertEquals("HaroldPTheatre", this.contributorService.findById(16L).getFirstName());
    }

    @Test
    public void testSaveContributors_findById_fromService_found() {
        Assert.assertFalse(this.contributorService.findById(1L).getFirstName().isEmpty());
    }

    @Test
    public void testSaveContributors_checkListSize_fromService() {
        Assert.assertEquals(16, this.contributorService.findContributorsToPlay().size());
    }

    @Test
    public void testSaveContributors_checkListSize_fromRepository() {
        Assert.assertEquals(16, this.contributorRepository.findAll().size());
    }

    // -----------------------  ADDRESS  TESTS  -----------------------

    @Test
    public void testUpdateAddress() {
        AddressSaveDto original = new AddressSaveDto("testCode", "testCity", "testStreet", 1234, 1L);
        this.addressService.save(List.of(original));
        AddressSaveDto updated = new AddressSaveDto("Code", "City", "testStreet", 1234, 1L);
        this.addressService.update(updated, 5L);
        Assert.assertEquals("City", this.addressRepository.findAll().get(4).getCity());
        updated.setAuditoriumId(null);
        this.addressRepository.deleteById(5L);
    }

    @Test
    public void testSaveAddress_checkLast_fromService() {
        Assert.assertEquals(4, (long) this.addressService.findById(4L).getHouseNumber());
    }

    @Test
    public void testSaveAddress_checkFirst_fromService() {
        Assert.assertEquals("London", this.addressService.findById(1L).getCity());
    }

    @Test
    public void testSaveAddress_listSizeCheck_fromRepository() {
        Assert.assertEquals(4, this.addressRepository.findAll().size());
    }

    // -----------------------  AUDITORIUM TESTS  -----------------------

    @Test
    public void testDeleteAuditorium_formService_checkAuditoriumIsActive_false() {
        this.auditoriumService.deleteAuditorium(1L);
        Assert.assertFalse(this.auditoriumService.findAuditoriumById(1L).getIsActive());
    }

    @Test
    public void testDeleteAuditorium_formService_false() {
        Assert.assertFalse(this.auditoriumService.deleteAuditorium(10L));
    }

    @Test
    public void testDeleteAuditorium_formService_true() {
        Assert.assertTrue(this.auditoriumService.deleteAuditorium(1L));
    }

    @Test
    public void testSaveAuditorium_listSize_fromService() {
        Assert.assertEquals(4, this.auditoriumService.listAuditoriums().getWrapperList().size());
    }

    @Test
    public void testSaveAuditorium_listSize_fromRepo() {
        Assert.assertEquals(4, this.auditoriumRepository.findAll().size());
    }

    @Test
    public void testSaveAuditorium_findThird_fromService() {
        Assert.assertEquals(150, (long) this.auditoriumService.findAuditoriumByAuditoriumName("Dorfman Theater").getCapacity());
    }

    @Test
    public void testSaveAuditorium_findSecond_fromService() {
        Assert.assertEquals("Lyttelton Theater", this.auditoriumService.findAuditoriumById(2L).getAuditoriumName());
    }

    @Test
    public void testSaveAuditorium_findLast_fromRepository() {
        Assert.assertEquals("Harold Pinter Theater", this.auditoriumRepository.findById(4L).get().getAuditoriumName());
    }

    @Test
    public void testSaveAuditorium_findFirst_fromRepository() {
        Assert.assertEquals(1, (long) this.auditoriumRepository.findAuditoriumByAuditoriumName("Oliver Theater").getId());
    }

    @Test
    public void testSaveAuditorium_allSize_fromRepository() {
        Assert.assertEquals(4, this.auditoriumRepository.findAll().size());
    }

    // -----------------------  THEATRE TESTS  -----------------------

    @Test
    public void testUpdateTheater() {
        TheaterSaveDto original = new TheaterSaveDto("TestTheaterOriginal", "Story.");
        this.theaterService.save(original);
        TheaterSaveDto updated = new TheaterSaveDto("TestTheaterUpdated", "Story.");
        this.theaterService.update(updated, "TestTheaterOriginal");
        Assert.assertEquals("TestTheaterUpdated", this.theaterRepository.findTheaterByTheaterName("TestTheaterUpdated").getTheaterName());
        this.theaterRepository.deleteById(3L);
    }

    @Test
    public void testDeleteTheater() {
        TheaterSaveDto theater = new TheaterSaveDto("testDelete", "Story.");
        this.theaterService.save(theater);
        this.theaterService.delete("testDelete");
        Assert.assertTrue(this.theaterRepository.findTheaterByTheaterName("testDelete").getIsArchived());
        this.theaterRepository.deleteById(3L);
    }

    @Test
    public void testListTheaters() {
        Assert.assertEquals(2, this.theaterService.listTheaters(new PageableDto(0, 10)).size());
    }

    @Test
    public void testTheaterCapacity_second() {
        Assert.assertEquals(908, (long) this.theaterService.findById(1L).getCapacity());
    }

    @Test
    public void testTheaterCapacity_first() {
        Assert.assertEquals(391, (long) this.theaterService.findById(2L).getCapacity());
    }

    @Test
    public void testSaveTheater_service_checkIfTheatreNameIsNotTaken_false() {
        Assert.assertFalse(this.theaterService.checkIfTheatreNameIsNotTaken("Harold Pinter Theater"));
    }

    @Test
    public void testSaveTheater_service_checkIfTheatreNameIsNotTaken_true() {
        Assert.assertTrue(this.theaterService.checkIfTheatreNameIsNotTaken("The Royal National Theater!"));
    }

    @Test
    public void testSaveTheater_checkFSecond_id_fromRepository_findByTheatreName() {
        Assert.assertEquals(2, (long) this.theaterRepository.findTheaterByTheaterName("Harold Pinter Theater").getId());
    }

    @Test
    public void testSaveTheater_checkFirst_id_fromRepository_findByTheatreName() {
        Assert.assertEquals(1, (long) this.theaterRepository.findTheaterByTheaterName("The Royal National Theater").getId());
    }

    @Test
    public void testSaveTheater_checkListSize_fromRepository() {
        Assert.assertEquals(2, this.theaterRepository.findAll().size());
    }

    @Test
    public void testSaveTheater_checkSecond_name_fromRepository() {
        Assert.assertEquals("Harold Pinter Theater", this.theaterRepository.findAll().get(1).getTheaterName());
    }

    @Test
    public void testSaveTheater_checkFirst_name_fromRepository() {
        Assert.assertEquals("The Royal National Theater", this.theaterRepository.findAll().get(0).getTheaterName());
    }

}

