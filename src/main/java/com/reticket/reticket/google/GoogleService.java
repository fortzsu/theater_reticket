package com.reticket.reticket.google;

import org.springframework.stereotype.Service;

@Service
public class GoogleService {

//    private static Sheets sheetsService;
//
//    @Autowired
//    public GoogleService() throws GeneralSecurityException, IOException {
//        sheetsService = SheetsServiceUtil.getSheetsService();
//    }
//
//    public void exportDataToSheet(ReportResultDto dto) throws IOException {
//        Spreadsheet spreadsheet = createSheet(dto);
//        addDataToSheet(dto, spreadsheet);
//    }
//
//    private Spreadsheet createSheet(ReportResultDto dto) throws IOException {
//        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
//                new SpreadsheetProperties().setTitle(createTitleForSheet(dto)));
//        return sheetsService
//                .spreadsheets()
//                .create(spreadSheet).execute();
//    }
//
//    private BatchUpdateValuesResponse addDataToSheet(ReportResultDto dto, Spreadsheet spreadsheet) throws IOException {
//        List<ValueRange> data = fillSheet(dto);
//        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
//                .setValueInputOption("USER_ENTERED")
//                .setData(data);
//
//        return sheetsService.spreadsheets().values()
//                .batchUpdate(spreadsheet.getSpreadsheetId(), batchBody)
//                .execute();
//    }
//
//    private List<ValueRange> fillSheet(ReportResultDto dto) {
//
//        List<ValueRange> data = new ArrayList<>();
//        data.add(new ValueRange()
//                .setRange("B2")
//                .setValues(List.of(
//                        Arrays.asList("Period start:", dto.getStart().toString()))));
//        data.add(new ValueRange()
//                .setRange("B3")
//                .setValues(List.of(
//                        Arrays.asList("Period end:", dto.getEnd().toString()))));
//        data.add(new ValueRange()
//                .setRange("B4")
//                .setValues(List.of(
//                        Arrays.asList("Ticket condition: ", dto.getTicketCondition()))));
//        data.add(new ValueRange()
//                .setRange("B5")
//                .setValues(List.of(
//                        Arrays.asList("Number of tickets: ", dto.getTicketCount().toString()))));
//        data.add(new ValueRange()
//                .setRange("B6")
//                .setValues(List.of(
//                        Arrays.asList("Sum of tickets: ", dto.getTicketAmount().toString()))));
//        if(dto.getSearchPathName() == null) {
//            data.add(new ValueRange()
//                    .setRange("B7")
//                    .setValues(List.of(
//                            List.of("Performances"))));
//            data.add(new ValueRange()
//                    .setRange("F7")
//                    .setValues(List.of(
//                            List.of("Sum of tickets:"))));
//            data.add(new ValueRange()
//                    .setRange("G7")
//                    .setValues(List.of(
//                            List.of("Number of tickets:"))));
//            for (int i = 0, j = 8; i < dto.getCriteriaResultPerformancesDtos().size(); i++, j++) {
//                data.add(fillSheetWithPerformances(dto.getCriteriaResultPerformancesDtos().get(i), j));
//            }
//        }
//        return data;
//    }
//
//    private ValueRange fillSheetWithPerformances(ReportResultPerformancesDto criteriaResultPerformancesDto, int j) {
//        ValueRange performance = new ValueRange();
//        performance.setRange("B" + j).setValues(
//                List.of(Arrays.asList(criteriaResultPerformancesDto.getTheatreName(),
//                        criteriaResultPerformancesDto.getAuditoriumName(),
//                        criteriaResultPerformancesDto.getPlayName(),
//                        criteriaResultPerformancesDto.getPerformanceDateTime().toString(),
//                        criteriaResultPerformancesDto.getSumOfTickets(),
//                        criteriaResultPerformancesDto.getCountOfTicket())));
//        return performance;
//    }
//
//    private String createTitleForSheet(ReportResultDto dto) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String start = dto.getStart().format(formatter);
//        String end = dto.getEnd().format(formatter);
//        String result;
//        if(dto.getSearchPathName() != null) {
//            result = "Report - " + dto.getSearchPathName() +  " - " + start + " - " + end;
//        } else {
//            result = "Report - " + start + " - " + end;
//        }
//        return result;
//    }

}
