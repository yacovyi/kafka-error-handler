package com.ws.ng.kafkaerrorhandler.model;

public class IcdEventFileCreation {
    public String fileid;

    public String filePath;

    public String fileName;

    public String fileFormat;

    public IcdEventFileCreation(String fileid, String filePath, String fileName, String fileFormat) {
        this.fileid = fileid;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileFormat = fileFormat;
    }

    public IcdEventFileCreation() {
    }

    @Override
    public String toString() {
        return "IcdEventFileCreation{" +
                "fileid='" + fileid + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                '}';
    }
}