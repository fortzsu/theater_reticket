package com.reticket.reticket;

import com.reticket.reticket.controller.*;
import com.reticket.reticket.domain.enums.AppUserType;
import com.reticket.reticket.domain.enums.SeatConditions;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.ReportFilterDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.*;
import com.reticket.reticket.repository.*;
import com.reticket.reticket.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReticketTest {

	@Autowired
	private TheatreController theatreController;
	@Autowired
	private TheatreService theatreService;
	@Autowired
	private TheatreRepository theatreRepository;
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
	private SearchPerformanceService searchPerformanceService;
	@Autowired
	private ReportService reportService;

	private static boolean init = true;

	@Before
	public void init() {
		if (init) {

			// ************* THEATRE *************
			this.theatreService.save(new TheatreSaveDto("The Royal National Theatre"));
			this.theatreService.save(new TheatreSaveDto("Harold Pinter Theatre"));

			// ************* THEATRE *************

			// ************* AUDITORIUM *************
			List<AuditoriumSaveDto> auditoriumSaveDtoList = new ArrayList<>();

			List<AuditoriumPriceCategorySaveDto> auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
					new AuditoriumPriceCategorySaveDto(1, 5),
					new AuditoriumPriceCategorySaveDto(2, 10),
					new AuditoriumPriceCategorySaveDto(3, 15),
					new AuditoriumPriceCategorySaveDto(4, 20)));
			auditoriumSaveDtoList.add(new AuditoriumSaveDto(
					"Oliver Theatre", 1L, 25, 18, auditoriumPriceCategorySaveDtoList)); //C:450

			auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
					new AuditoriumPriceCategorySaveDto(1, 7),
					new AuditoriumPriceCategorySaveDto(2, 13),
					new AuditoriumPriceCategorySaveDto(3, 18)));
			auditoriumSaveDtoList.add(new AuditoriumSaveDto(
					"Lyttelton Theatre", 1L, 22, 14, auditoriumPriceCategorySaveDtoList)); //C:308

			auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
					new AuditoriumPriceCategorySaveDto(1, 6),
					new AuditoriumPriceCategorySaveDto(2, 11)));
			auditoriumSaveDtoList.add(new AuditoriumSaveDto(
					"Dorfman Theatre", 1L, 15, 10, auditoriumPriceCategorySaveDtoList)); //C:150

			auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
					new AuditoriumPriceCategorySaveDto(1, 6),
					new AuditoriumPriceCategorySaveDto(2, 11),
					new AuditoriumPriceCategorySaveDto(3, 18)));
			auditoriumSaveDtoList.add(new AuditoriumSaveDto(
					"Harold Pinter Theatre", 2L, 23, 17, auditoriumPriceCategorySaveDtoList)); //C:391

			this.auditoriumController.createAuditorium(auditoriumSaveDtoList);

			// ************* AUDITORIUM *************

			// ************* ADDRESS *************

			List<AddressSaveDto> addressSaveDtoList = new ArrayList<>();
			addressSaveDtoList.add(new AddressSaveDto("SE1 9PX", "London", "Upper Ground, South Bank", 0, 1L));
			addressSaveDtoList.add(new AddressSaveDto("SE1 9PX", "London", "Upper Ground, South Bank", 0, 2L));
			addressSaveDtoList.add(new AddressSaveDto("SE1 9PX", "London", "Upper Ground, South Bank", 0, 2L));
			addressSaveDtoList.add(new AddressSaveDto("SW1Y 4SW", "London", "Panton", 4, 4L));
			this.addressController.save(addressSaveDtoList);

			// ************* ADDRESS *************

			// ************* CONTRIBUTOR *************

			List<ContributorSaveDto> contributorSaveDtoList = new ArrayList<>();
			String national = "NationalTheatre";
			String harold = "HaroldPTheatre";
			String introduction = "This is an introduction.";
			for (int i = 1; i <= 10; i++) {
				contributorSaveDtoList.add(new ContributorSaveDto(national, String.valueOf(i), introduction));
			}
			for (int i = 1; i <= 6; i++) {
				contributorSaveDtoList.add(new ContributorSaveDto(harold, String.valueOf(i), introduction));
			}

			this.contributorController.save(contributorSaveDtoList);

			// ************* CONTRIBUTOR *************

			// ************* CONTRIBUTORS FOR PLAY SAVE *************

			List<ContributorsSaveForPlaySaveDto> cForP = new ArrayList<>();

			for (int i = 1; i <= 16; i++) {
				cForP.add(new ContributorsSaveForPlaySaveDto((long) i, "Actor"));
			}


			// ************* CONTRIBUTORS FOR PLAY SAVE *************

			// ************* PLAY *************
			List<PlaySaveDto> playSaveDtoList = new ArrayList<>();

			PlaySaveDto playSaveDto_1 = new PlaySaveDto("Standing at the Sky's Edge", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					1L, new ArrayList<>(Arrays.asList(100, 85, 65, 40, 25)), "Drama");
			playSaveDto_1.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(0), cForP.get(1), cForP.get(2), cForP.get(3)));
			playSaveDtoList.add(playSaveDto_1);

			PlaySaveDto playSaveDto_2 = new PlaySaveDto("Dancing at Lughnasa", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					1L, new ArrayList<>(Arrays.asList(125, 95, 85, 50, 20)), "Musical");
			playSaveDto_2.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(4), cForP.get(1), cForP.get(2), cForP.get(3)));
			playSaveDtoList.add(playSaveDto_2);

			PlaySaveDto playSaveDto_3 = new PlaySaveDto("Phaedra", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					2L, new ArrayList<>(Arrays.asList(95, 70, 50, 20)), "Tragedy");
			playSaveDto_3.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(3), cForP.get(4), cForP.get(5), cForP.get(6), cForP.get(7), cForP.get(8)));
			playSaveDtoList.add(playSaveDto_3);

			PlaySaveDto playSaveDto_4 = new PlaySaveDto("The Motive and the Cue", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					2L, new ArrayList<>(Arrays.asList(95, 70, 50, 20)), "Crime");
			playSaveDto_4.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(5), cForP.get(6), cForP.get(7), cForP.get(8)));
			playSaveDtoList.add(playSaveDto_4);

			PlaySaveDto playSaveDto_5 = new PlaySaveDto("Romeo and Julie", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					3L, new ArrayList<>(Arrays.asList(80, 55, 25)), "Tragedy");
			playSaveDto_5.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(0), cForP.get(1), cForP.get(2), cForP.get(3), cForP.get(5), cForP.get(6), cForP.get(7), cForP.get(8),cForP.get(9)));
			playSaveDtoList.add(playSaveDto_5);

			PlaySaveDto playSaveDto_6 = new PlaySaveDto("Lemons Lemons Lemons Lemons Lemons", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					4L, new ArrayList<>(Arrays.asList(85, 65, 40, 25)), "Comedy");
			playSaveDto_6.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(10), cForP.get(11), cForP.get(12)));
			playSaveDtoList.add(playSaveDto_6);

			PlaySaveDto playSaveDto_7 = new PlaySaveDto("A Little Life", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					4L, new ArrayList<>(Arrays.asList(110, 90, 60, 25)), "Drama");
			playSaveDto_7.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(10), cForP.get(11), cForP.get(12),cForP.get(13), cForP.get(14), cForP.get(15)));
			playSaveDtoList.add(playSaveDto_7);

			PlaySaveDto playSaveDto_8 = new PlaySaveDto("Dr. Semmelweis", "This is the plot.",
					LocalDateTime.of(2023, 3, 1, 19, 0),
					4L, new ArrayList<>(Arrays.asList(80, 70, 50, 25)), "Crime");
			playSaveDto_8.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(10), cForP.get(11), cForP.get(12),cForP.get(13), cForP.get(14), cForP.get(15)));
			playSaveDtoList.add(playSaveDto_8);

			this.playController.save(playSaveDtoList);
			// ************* PLAY *************
			// ************* PERFORMANCE *************

			List<PerformanceSaveDto> performanceSaveDtoList = new ArrayList<>();
			for (int i = 1, j = 1; i <= 16; i++) {
				performanceSaveDtoList.add(new PerformanceSaveDto(
						LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), i, 20, 0),
						(long) j));
				if (i % 2 == 0) {
					j++;
				}
			}
			this.performanceController.save(performanceSaveDtoList);
			// ************* PERFORMANCE *************

			// ************* APPUSER *************

			List<AppUserSaveDto> appUserSaveDtos = new ArrayList<>();
			appUserSaveDtos.add(new AppUserSaveDto("Zsuzsi", "Fort", "fortzsu", "test1234", "Super Admin"));
			appUserSaveDtos.add(new AppUserSaveDto("Zsuzsanna", "Bozso", "bozsofortzsu", "test1234", "Super Admin"));
			appUserSaveDtos.add(new AppUserSaveDto("Daniel", "Bozso", "danielb", "test1234", "Super Admin"));
			this.appUserController.save(appUserSaveDtos);
			appUserSaveDtos.clear();

			for (int i = 1; i <= 15; i++) {
				appUserSaveDtos.add(new AppUserSaveDto("_" + i, "AppUser", i + "_" + "username", "test1234", "Guest"));
			}
			this.appUserController.save(appUserSaveDtos);

			List<TicketActionDto> ticketActionDtoList = new ArrayList<>();

			TicketActionDto ticketActionDto_buy = new TicketActionDto("buy", "bozsofortzsu", Arrays.asList(1L, 2L, 3L));
			ticketActionDtoList.add(ticketActionDto_buy);

			TicketActionDto ticketActionDto_reserve = new TicketActionDto("reserve", "bozsofortzsu", Arrays.asList(4L, 5L, 6L));
			ticketActionDtoList.add(ticketActionDto_reserve);

			TicketActionDto ticketActionDto_buy2 = new TicketActionDto("buy", "danielb", Arrays.asList(10L, 11L, 12L));
			ticketActionDtoList.add(ticketActionDto_buy2);
			TicketActionDto ticketActionDto_return = new TicketActionDto("return", "danielb", Arrays.asList(11L, 12L));
			ticketActionDtoList.add(ticketActionDto_return);

			TicketActionDto ticketActionDto_buyAll = new TicketActionDto("buy", "1_username",
					Arrays.asList(100L, 101L, 200L, 201L, 300L, 301L, 400L, 401L,
							500L, 501L, 600L, 601L, 700L, 701L, 800L, 801L,
							2500L, 2501L, 2600L, 2601L, 2700L, 2701L));
			ticketActionDtoList.add(ticketActionDto_buyAll);

			this.ticketActionController.buyOrReserveTicket(ticketActionDtoList);


			// ************* APPUSER *************

			init = false;
		}
	}


	// -----------------------  GOOGLE TESTS  -----------------------

//    @Test
//    public void testReportPerformances_Google_soldTickets_byTheatre() {
//        ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
//                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
//                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, true);
//        Assert.assertEquals(3, this.reportService.report(reportFilterDto).getCriteriaResultPerformancesDtos().size());
//    }
//
//    @Test
//    public void testReport_Google_soldTickets_byTheatre_ticketAmount() {
//        ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
//                new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
//                        LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, true);
//        Assert.assertEquals(1940, (long) this.reportService.report(reportFilterDto).getTicketAmount());
//    }

	// -----------------------  GOOGLE TESTS  -----------------------

	// -----------------------  REPORT TESTS  -----------------------


	@Test
	public void testReportPerformances_soldTickets_byTheatre() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(3, this.reportService.report(reportFilterDto).getCriteriaResultPerformancesDtos().size());
	}

	@Test
	public void testReportPerformances_soldTickets_byTheatre_testFirst_amount() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(1030, this.reportService.report(reportFilterDto).
				getCriteriaResultPerformancesDtos().get(0).getSumOfTickets());
	}

	@Test
	public void testReportPerformances_soldTickets_byTheatre_testFirst_returned() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("RETURNED", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(200, this.reportService.report(reportFilterDto).
				getCriteriaResultPerformancesDtos().get(0).getSumOfTickets());
	}


	@Test
	public void testReportPerformances_soldTickets_byTheatre_testFirst_count() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(14, (long)
				this.reportService.report(reportFilterDto).getCriteriaResultPerformancesDtos().get(0).getCountOfTicket());
	}

	@Test
	public void testReportPerformances_soldTickets_byTheatre_testSecond_amount() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(580, this.reportService.report(reportFilterDto).
				getCriteriaResultPerformancesDtos().get(1).getSumOfTickets());
	}

	@Test
	public void testReportPerformances_soldTickets_byTheatre_testSecond_count() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(8, (long) this.reportService.report(reportFilterDto).
				getCriteriaResultPerformancesDtos().get(1).getCountOfTicket());
	}

	@Test
	public void testReportPerformances_soldTickets_byTheatre_testThird_amount() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(330, this.reportService.report(reportFilterDto).getCriteriaResultPerformancesDtos().get(2).getSumOfTickets());
	}

	@Test
	public void testReportPerformances_soldTickets_byTheatre_testThird_count() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), true, false);
		Assert.assertEquals(6, (long) this.reportService.report(reportFilterDto).getCriteriaResultPerformancesDtos().get(2).getCountOfTicket());
	}

	@Test
	public void testReport_soldTickets_byTheatre_ticketCount() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
		Assert.assertEquals(28, (long) this.reportService.report(reportFilterDto).getTicketCount());
	}

	@Test
	public void testReport_soldTickets_byTheatre_ticketAmount() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
		Assert.assertEquals(1940, (long) this.reportService.report(reportFilterDto).getTicketAmount());
	}

	@Test
	public void testReport_soldTickets_byTheatre_outOfDate() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), false, false);
		Assert.assertNull(this.reportService.report(reportFilterDto).getTicketAmount());
	}

	@Test
	public void testReport_soldTickets_byTheatre_testStartDate() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1, 0, 0);
		Assert.assertEquals(startDate, this.reportService.report(reportFilterDto).getStart());
	}

	@Test
	public void testReport_soldTickets_byTheatre_testEndDate() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10, 23, 59);
		Assert.assertEquals(endDate, this.reportService.report(reportFilterDto).getEnd());
	}

	@Test
	public void testReport_soldTickets_byTheatre_theatreName() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "theatre", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), false, false);
		Assert.assertEquals("The Royal National Theatre", this.reportService.report(reportFilterDto).getSearchPathName());
	}

	@Test
	public void testReport_soldTickets_byAuditorium_auditoriumName() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "auditorium", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10), false, false);
		Assert.assertEquals("Oliver Theatre", this.reportService.report(reportFilterDto).getSearchPathName());
	}

	@Test
	public void testReport_soldTickets_byPlay_playName() {
		ReportFilterDto reportFilterDto = new ReportFilterDto("SOLD", "play", 1L,
				new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
						LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16), false, false);
		Assert.assertEquals("Standing at the Sky's Edge", this.reportService.report(reportFilterDto).getSearchPathName());
	}


	// -----------------------  REPORT TESTS  -----------------------

	// -----------------------  SEARCH PERFORMANCES TESTS  -----------------------


	@Test
	public void testSearchFilteredPerformances_byPlayType_Comedy_findAll() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"Comedy", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 10));
		Assert.assertEquals(2, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlayType_Comedy_withWrongDate() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"Comedy", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 9),
				new PageableDto(0, 10));
		Assert.assertEquals(0, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlayType_Drama_findAll() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"Drama", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 16));
		Assert.assertEquals(4, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlayType_Drama_wrongDate() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"Drama", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 15,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 16));
		Assert.assertEquals(0, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlayType_Drama_emptyPage() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"Drama", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(1, 8));
		Assert.assertEquals(0, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlayType_Musical_findAll() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"Musical", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 16));
		Assert.assertEquals(2, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlayType_Musical_forOneDate() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"Musical", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 3,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 3),
				new PageableDto(0, 16));
		Assert.assertEquals(1, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlay_firstPlay() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"play", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 10));
		Assert.assertEquals(2, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlay_firstPlay_withWrongDate() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"play", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 10));
		Assert.assertEquals(0, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlay_lastPlay() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"play", 8L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 10));
		Assert.assertEquals(2, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byPlay_lastPlay_withWrongDate() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"play", 8L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 10),
				new PageableDto(0, 10));
		Assert.assertEquals(0, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}


	@Test
	public void testSearchFilteredPerformances_byAuditorium_findAll() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 10));
		Assert.assertEquals(4, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byAuditorium_firstPage() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 2));
		Assert.assertEquals(2, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byAuditorium_filterForOneDay() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1),
				new PageableDto(0, 2));
		Assert.assertEquals(1, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byAuditorium_filterForOneDay_EmptySecondPage() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"auditorium", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1),
				new PageableDto(1, 2));
		Assert.assertEquals(0, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byLastAuditorium_findAll() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"auditorium", 4L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 16));
		Assert.assertEquals(6, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byLastAuditorium_findAll_checkFirst() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"auditorium", 4L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 16));
		Assert.assertEquals("Lemons Lemons Lemons Lemons Lemons",
				this.searchPerformanceService.searchFilteredPerformances(dto).get(0).getPlayName());
	}


	@Test
	public void testSearchFilteredPerformances_byLastAuditorium_findAll_checkLast() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"auditorium", 4L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(2, 2));
		Assert.assertEquals("Dr. Semmelweis",
				this.searchPerformanceService.searchFilteredPerformances(dto).get(1).getPlayName());
	}


	@Test
	public void testSearchFilteredPerformances_byTheatre_firstPage() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 5),
				new PageableDto(0, 5));
		Assert.assertEquals(5, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byTheatre_secondPage() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 5),
				new PageableDto(1, 2));
		Assert.assertEquals(2, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byTheatre_first() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 5),
				new PageableDto(0, 5));
		Assert.assertEquals("Standing at the Sky's Edge",
				this.searchPerformanceService.searchFilteredPerformances(dto).get(0).getPlayName());
	}

	@Test
	public void testSearchFilteredPerformances_byTheatre_last() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(1, 5));
		Assert.assertEquals("Romeo and Julie",
				this.searchPerformanceService.searchFilteredPerformances(dto).get(4).getPlayName());
	}

	@Test
	public void testSearchFilteredPerformances_byTheatre_checkEmptyPage() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(3, 5));
		Assert.assertEquals(0,
				this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	@Test
	public void testSearchFilteredPerformances_byTheatre_checkAuditorium() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(1, 5));
		Assert.assertEquals("Dorfman Theatre",
				this.searchPerformanceService.searchFilteredPerformances(dto).get(4).getAuditoriumName());
	}

	@Test
	public void testSearchFilteredPerformances_byTheatre_AllInOnePage() {
		FilterPerformancesDto dto = new FilterPerformancesDto(
				"theatre", 1L, new SearchDateDto(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1,
				LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 16),
				new PageableDto(0, 16));
		Assert.assertEquals(10, this.searchPerformanceService.searchFilteredPerformances(dto).size());
	}

	// -----------------------  SEARCH PERFORMANCES TESTS  -----------------------

	// -----------------------  TICKET ACTIONS TESTS  -----------------------

	@Test
	public void testReturnTest_withAppUser() {
		Assert.assertEquals(TicketCondition.RETURNED, this.ticketRepository.findAll().get(10).getTicketCondition());
	}

	@Test
	public void testReturnTest_withAppUser_listTickets() {
		Assert.assertEquals(3, this.appUserService.listTickets("danielb").size());
	}

	@Test
	public void testReserveTest_withAppUser_ticketList() {
		Assert.assertEquals(6, this.appUserService.listTickets("bozsofortzsu").size());
	}

	@Test
	public void testReserveTest_withAppUser_sold() {
		Assert.assertEquals("Sold", this.appUserService.listTickets("bozsofortzsu").get(0).getTicketCondition());
	}

	@Test
	public void testReserveTest_withAppUser_reserved() {
		Assert.assertEquals("Reserved", this.appUserService.listTickets("bozsofortzsu").get(3).getTicketCondition());
	}

	@Test
	public void testReserveTest_withAppUser_playName() {
		Assert.assertEquals("Standing at the Sky's Edge", this.appUserService.listTickets("bozsofortzsu").get(0).getPlayName());
	}

	@Test
	public void testReserveTest_withAppUser_theatreName() {
		Assert.assertEquals("The Royal National Theatre", this.appUserService.listTickets("bozsofortzsu").get(0).getTheatreName());
	}

	@Test
	public void testReserveTest_withAppUser_ticketPrice() {
		Assert.assertEquals(100, (long) this.appUserService.listTickets("bozsofortzsu").get(0).getTicketPrice());
	}


	// -----------------------  TICKET ACTIONS TESTS  -----------------------

	// -----------------------  APPUSER TESTS  -----------------------

	@Test
	public void testSavedAppUser_fromRepository_findAll() {
		Assert.assertEquals(18, this.appUserRepository.findAll().size());
	}

	@Test
	public void testSavedAppUser_fromRepository_checkUserName() {
		Assert.assertEquals("fortzsu", this.appUserRepository.findAll().get(0).getUsername());
	}

	@Test
	public void testSavedAppUser_fromRepository_checkUserName_Last() {
		Assert.assertEquals("15_username", this.appUserRepository.findAll().get(17).getUsername());
	}

	@Test
	public void testSavedAppUser_fromRepository_checkUserType_guest() {
		Assert.assertEquals(AppUserType.GUEST, this.appUserRepository.findAll().get(17).getAppUserType());
	}

	@Test
	public void testSavedAppUser_fromRepository_checkUserType() {
		Assert.assertEquals(AppUserType.SUPER_ADMIN, this.appUserRepository.findAll().get(0).getAppUserType());
	}

	@Test
	public void testSavedAppUser_checkTicketListByUsername() {
		Assert.assertEquals(HttpStatus.OK, this.appUserController.listTickets("fortzsu").getStatusCode());
	}

	@Test
	public void testSaveAppUser_listTickets_Empty() {
		Assert.assertEquals(0, this.appUserService.listTickets("fortzsu").size());
	}

	@Test
	public void testSaveAppUser_likedPlays() {
		Assert.assertEquals(0, this.appUserService.listLikedPlays("fortzsu").size());
		this.appUserService.likePlay("fortzsu", 1L);
		Assert.assertEquals(1, this.appUserService.listLikedPlays("fortzsu").size());
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
		Assert.assertEquals(SeatConditions.AVAILABLE, this.seatRepository.findAll().get(0).getSeatCondition());
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
	public void testListPerformance_service_findAll() {
		Assert.assertEquals(16, this.performanceService.listPerformances(new PageableDto(0, 16)).size());
	}

	@Test
	public void testListPerformance_service_checkPages() {
		Assert.assertEquals(4, this.performanceService.listPerformances(new PageableDto(2, 4)).size());
	}

	@Test
	public void testSavePerformance_checkDateTime_fromService() {
		Assert.assertEquals(19, this.performanceService.findPerformanceById(8L).getPerformanceDateTime().getHour());
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
	public void testSaveContributors_checkLastByName_fromService() {
		Assert.assertEquals("HaroldPTheatre", this.contributorService.findById(16L).getFirstName());
	}

	@Test
	public void testSaveContributors_findById_fromService_found() {
		Assert.assertFalse(this.contributorService.findById(1L).getFirstName().isEmpty());
	}

	@Test
	public void testSaveContributors_checkListSize_fromService() {
		Assert.assertEquals(16, this.contributorService.findContributors().size());
	}

	@Test
	public void testSaveContributors_checkListSize_fromRepository() {
		Assert.assertEquals(16, this.contributorRepository.findAll().size());
	}

	// -----------------------  ADDRESS  TESTS  -----------------------
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
		Assert.assertFalse(this.auditoriumService.findAuditoriumById(1L).getActive());
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
		Assert.assertEquals(4, this.auditoriumService.listAuditoriums().size());
	}

	@Test
	public void testSaveAuditorium_findThird_fromService() {
		Assert.assertEquals(150, (long) this.auditoriumService.findAuditoriumByAuditoriumName("Dorfman Theatre").getCapacity());
	}

	@Test
	public void testSaveAuditorium_findSecond_fromService() {
		Assert.assertEquals("Lyttelton Theatre", this.auditoriumService.findAuditoriumById(2L).getAuditoriumName());
	}

	@Test
	public void testSaveAuditorium_findLast_fromRepository() {
		Assert.assertEquals("Harold Pinter Theatre", this.auditoriumRepository.findById(4L).get().getAuditoriumName());
	}

	@Test
	public void testSaveAuditorium_findFirst_fromRepository() {
		Assert.assertEquals(1, (long) this.auditoriumRepository.findAuditoriumByAuditoriumName("Oliver Theatre").getId());
	}

	@Test
	public void testSaveAuditorium_allSize_fromRepository() {
		Assert.assertEquals(4, this.auditoriumRepository.findAll().size());
	}

	// -----------------------  THEATRE TESTS  -----------------------
	@Test
	public void testTheatreCapacity_second() {
		Assert.assertEquals(908, (long) this.theatreService.findById(1L).getCapacity());
	}

	@Test
	public void testTheatreCapacity_first() {
		Assert.assertEquals(391, (long) this.theatreService.findById(2L).getCapacity());
	}

	@Test
	public void testSaveTheatre_service_checkIfTheatreNameIsNotTaken_false() {
		Assert.assertFalse(this.theatreService.checkIfTheatreNameIsNotTaken("Harold Pinter Theatre"));
	}

	@Test
	public void testSaveTheatre_service_checkIfTheatreNameIsNotTaken_true() {
		Assert.assertTrue(this.theatreService.checkIfTheatreNameIsNotTaken("The Royal National Theatre!"));
	}

	@Test
	public void testSaveTheatre_checkFSecond_id_fromRepository_findByTheatreName() {
		Assert.assertEquals(2, (long) this.theatreRepository.findTheatreByTheatreName("Harold Pinter Theatre").getId());
	}

	@Test
	public void testSaveTheatre_checkFirst_id_fromRepository_findByTheatreName() {
		Assert.assertEquals(1, (long) this.theatreRepository.findTheatreByTheatreName("The Royal National Theatre").getId());
	}

	@Test
	public void testSaveTheatre_checkListSize_fromRepository() {
		Assert.assertEquals(2, this.theatreRepository.findAll().size());
	}

	@Test
	public void testSaveTheatre_checkSecond_name_fromRepository() {
		Assert.assertEquals("Harold Pinter Theatre", this.theatreRepository.findAll().get(1).getTheatreName());
	}

	@Test
	public void testSaveTheatre_checkFirst_name_fromRepository() {
		Assert.assertEquals("The Royal National Theatre", this.theatreRepository.findAll().get(0).getTheatreName());
	}

}

