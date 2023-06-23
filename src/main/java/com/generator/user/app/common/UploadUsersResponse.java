package com.generator.user.app.common;

public class UploadUsersResponse {
    private int totalRecords;
    private int recordsImported;
    private int recordsFailed;
    private String message;

    // Getter and setter methods

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getRecordsImported() {
        return recordsImported;
    }

    public void setRecordsImported(int recordsImported) {
        this.recordsImported = recordsImported;
    }

    public int getRecordsFailed() {
        return recordsFailed;
    }

    public void setRecordsFailed(int recordsFailed) {
        this.recordsFailed = recordsFailed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
