package com.reticket.reticket;

import com.reticket.reticket.dto.save.*;
import com.reticket.reticket.service.*;
import com.reticket.reticket.service.performance.GenerateTicketToPerformanceService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetDatabaseForTest {

    public static void init(GenerateTicketToPerformanceService generateTicketToPerformanceService, TicketActionService ticketActionService,
                            TheaterService theaterService, AuditoriumService auditoriumService, AddressService addressService, ContributorService contributorService,
                            AppUserService appUserService, PlayService playService) {
        // ************* THEATRE *************
        theaterService.save(new TheaterSaveDto("The Royal National Theater", "theatreHistory"));
        theaterService.save(new TheaterSaveDto("Harold Pinter Theater", "theatreHistory"));

        // ************* THEATRE *************
        // ************* AUDITORIUM *************

        List<AuditoriumPriceCategorySaveDto> auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
                new AuditoriumPriceCategorySaveDto(1, 5),
                new AuditoriumPriceCategorySaveDto(2, 10),
                new AuditoriumPriceCategorySaveDto(3, 15),
                new AuditoriumPriceCategorySaveDto(4, 20)));
        auditoriumService.save(new AuditoriumSaveDto(
                "Oliver Theater", 1L, 25, 18, auditoriumPriceCategorySaveDtoList)); //C:450

        auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
                new AuditoriumPriceCategorySaveDto(1, 7),
                new AuditoriumPriceCategorySaveDto(2, 13),
                new AuditoriumPriceCategorySaveDto(3, 18)));
        auditoriumService.save(new AuditoriumSaveDto(
                "Lyttelton Theater", 1L, 22, 14, auditoriumPriceCategorySaveDtoList)); //C:308

        auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
                new AuditoriumPriceCategorySaveDto(1, 6),
                new AuditoriumPriceCategorySaveDto(2, 11)));
        auditoriumService.save(new AuditoriumSaveDto(
                "Dorfman Theater", 1L, 15, 10, auditoriumPriceCategorySaveDtoList)); //C:150

        auditoriumPriceCategorySaveDtoList = new ArrayList<>(Arrays.asList(
                new AuditoriumPriceCategorySaveDto(1, 6),
                new AuditoriumPriceCategorySaveDto(2, 11),
                new AuditoriumPriceCategorySaveDto(3, 18)));
        auditoriumService.save(new AuditoriumSaveDto(
                "Harold Pinter Theater", 2L, 23, 17, auditoriumPriceCategorySaveDtoList)); //C:391


        // ************* AUDITORIUM *************

        // ************* ADDRESS *************

        addressService.save(new AddressSaveDto("SE1 9PX", "London", "Upper Ground, South Bank", 1, 1L));
        addressService.save(new AddressSaveDto("SE1 9PX", "London", "Upper Ground, South Bank", 2, 2L));
        addressService.save(new AddressSaveDto("SE1 9PX", "London", "Upper Ground, South Bank", 3, 3L));
        addressService.save(new AddressSaveDto("SW1Y 4SW", "London", "Panton", 4, 4L));

        // ************* ADDRESS *************

        // ************* CONTRIBUTOR *************

        String national = "NationalTheatre";
        String harold = "HaroldPTheatre";
        String introduction = "This is an introduction.";
        for (int i = 1; i <= 10; i++) {
            contributorService.save(new ContributorSaveDto(national, String.valueOf(i), introduction));
        }
        for (int i = 1; i <= 6; i++) {
            contributorService.save(new ContributorSaveDto(harold, String.valueOf(i), introduction));
        }

        // ************* CONTRIBUTOR *************

        // ************* CONTRIBUTORS FOR PLAY SAVE *************

        List<ContributorsSaveForPlaySaveDto> cForP = new ArrayList<>();

        for (int i = 1; i <= 16; i++) {
            cForP.add(new ContributorsSaveForPlaySaveDto((long) i, "Actor"));
        }


        // ************* CONTRIBUTORS FOR PLAY SAVE *************

        // ************* PLAY *************

        PlaySaveDto playSaveDto_1 = new PlaySaveDto("Standing at the Sky's Edge", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                1L, new ArrayList<>(Arrays.asList(100, 85, 65, 40, 25)), "Drama");
        playSaveDto_1.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(0), cForP.get(1), cForP.get(2), cForP.get(3)));
        playService.save(playSaveDto_1);

        PlaySaveDto playSaveDto_2 = new PlaySaveDto("Dancing at Lughnasa", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                1L, new ArrayList<>(Arrays.asList(125, 95, 85, 50, 20)), "Musical");
        playSaveDto_2.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(4), cForP.get(1), cForP.get(2), cForP.get(3)));
        playService.save(playSaveDto_2);

        PlaySaveDto playSaveDto_3 = new PlaySaveDto("Phaedra", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                2L, new ArrayList<>(Arrays.asList(95, 70, 50, 20)), "Tragedy");
        playSaveDto_3.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(3), cForP.get(4), cForP.get(5), cForP.get(6), cForP.get(7), cForP.get(8)));
        playService.save(playSaveDto_3);

        PlaySaveDto playSaveDto_4 = new PlaySaveDto("The Motive and the Cue", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                2L, new ArrayList<>(Arrays.asList(95, 70, 50, 20)), "Crime");
        playSaveDto_4.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(5), cForP.get(6), cForP.get(7), cForP.get(8)));
        playService.save(playSaveDto_4);

        PlaySaveDto playSaveDto_5 = new PlaySaveDto("Romeo and Julie", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                3L, new ArrayList<>(Arrays.asList(80, 55, 25)), "Tragedy");
        playSaveDto_5.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(0), cForP.get(1), cForP.get(2), cForP.get(3), cForP.get(5), cForP.get(6), cForP.get(7), cForP.get(8), cForP.get(9)));
        playService.save(playSaveDto_5);

        PlaySaveDto playSaveDto_6 = new PlaySaveDto("Lemons Lemons Lemons Lemons Lemons", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                4L, new ArrayList<>(Arrays.asList(85, 65, 40, 25)), "Comedy");
        playSaveDto_6.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(10), cForP.get(11), cForP.get(12)));
        playService.save(playSaveDto_6);

        PlaySaveDto playSaveDto_7 = new PlaySaveDto("A Little Life", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                4L, new ArrayList<>(Arrays.asList(110, 90, 60, 25)), "Drama");
        playSaveDto_7.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(10), cForP.get(11), cForP.get(12), cForP.get(13), cForP.get(14), cForP.get(15)));
        playService.save(playSaveDto_7);

        PlaySaveDto playSaveDto_8 = new PlaySaveDto("Dr. Semmelweis", "This is the plot.",
                LocalDateTime.of(2023, 3, 1, 19, 0),
                4L, new ArrayList<>(Arrays.asList(80, 70, 50, 25)), "Crime");
        playSaveDto_8.setContributorsSaveForPlaySaveDtoList(Arrays.asList(cForP.get(10), cForP.get(11), cForP.get(12), cForP.get(13), cForP.get(14), cForP.get(15)));
        playService.save(playSaveDto_8);

        // ************* PLAY *************
        // ************* PERFORMANCE *************

        for (int i = 1, j = 1; i <= 16; i++) {
            generateTicketToPerformanceService.generateTickets(new PerformanceSaveDto(
                    LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), i, 20, 0),
                    (long) j));
            if (i % 2 == 0) {
                j++;
            }
        }
        // ************* PERFORMANCE *************

        // ************* APPUSER *************

        appUserService.saveGuest(new GuestUserSaveDto("FirstName_GuestOne", "LastName_GuestOne", "guestOne", "test", "guestOne@testemail.com"));
        appUserService.saveGuest(new GuestUserSaveDto("FirstName_GuestTwo", "LastName_GuestTwo", "guestTwo", "test",  "guestTwo@testemail.com"));
        appUserService.saveGuest(new GuestUserSaveDto("FirstName_GuestThree", "LastName_GuestThree", "guestThree", "test", "guestThree@testemail.com"));
        appUserService.saveGuest(new GuestUserSaveDto("FirstName_GuestFour", "LastName_GuestFour", "guestFour", "test",  "guestFour@testemail.com"));


        TicketActionDto ticketActionDto_buy = new TicketActionDto("buy", "guestTwo", Arrays.asList(1L, 2L, 3L));
        ticketActionService.ticketAction(ticketActionDto_buy);

        TicketActionDto ticketActionDto_reserve = new TicketActionDto("reserve", "guestTwo", Arrays.asList(4L, 5L, 6L));
        ticketActionService.ticketAction(ticketActionDto_reserve);

        TicketActionDto ticketActionDto_buy2 = new TicketActionDto("buy", "guestThree", Arrays.asList(10L, 11L, 12L));
        ticketActionService.ticketAction(ticketActionDto_buy2);
        TicketActionDto ticketActionDto_return = new TicketActionDto("return", "guestThree", Arrays.asList(11L, 12L));
        ticketActionService.ticketAction(ticketActionDto_return);

        TicketActionDto ticketActionDto_buyAll = new TicketActionDto("buy", "guestFour",
                Arrays.asList(100L, 101L, 200L, 201L, 300L, 301L, 400L, 401L,
                        500L, 501L, 600L, 601L, 700L, 701L, 800L, 801L,
                        2500L, 2501L, 2600L, 2601L, 2700L, 2701L));
        ticketActionService.ticketAction(ticketActionDto_buyAll);


        // ************* APPUSER *************
    }
}
