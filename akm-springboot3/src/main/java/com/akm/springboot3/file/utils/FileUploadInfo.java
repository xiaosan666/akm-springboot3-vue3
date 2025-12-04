package com.akm.springboot3.file.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadInfo {
    private boolean fileUploadRequest;
    private String method;
    private String contentType;
    private long contentLength;
    private String boundary;
    private String characterEncoding;

    @Override
    public String toString() {
        return "FileUploadInfo{" +
            "fileUploadRequest=" + fileUploadRequest +
            ", method='" + method + '\'' +
            ", contentType='" + contentType + '\'' +
            ", contentLength=" + contentLength +
            ", boundary='" + boundary + '\'' +
            ", characterEncoding='" + characterEncoding + '\'' +
            '}';
    }
}
