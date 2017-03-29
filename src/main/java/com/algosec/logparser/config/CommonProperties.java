package com.algosec.logparser.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "commonProperties", ignoreUnknownFields = false)
public class CommonProperties {

    private String fileURL;

    private String dataExtractStrategy;

    private String dataHandleStrategy;

    public String getFileURL() {
        return fileURL;
    }


    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }


    public String getDataExtractStrategy() {
        return dataExtractStrategy;
    }


    public void setDataExtractStrategy(String dataExtractStrategy) {
        this.dataExtractStrategy = dataExtractStrategy;
    }


    public String getDataHandleStrategy() {
        return dataHandleStrategy;
    }


    public void setDataHandleStrategy(String dataHandleStrategy) {
        this.dataHandleStrategy = dataHandleStrategy;
    }
}
