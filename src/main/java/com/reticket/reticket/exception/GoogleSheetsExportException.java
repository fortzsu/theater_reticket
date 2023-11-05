package com.reticket.reticket.exception;

public class GoogleSheetsExportException extends RuntimeException{

    public GoogleSheetsExportException() {
        super("Google Sheets export doesn't work!");
    }
}
