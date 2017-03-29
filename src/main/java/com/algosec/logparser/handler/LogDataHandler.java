package com.algosec.logparser.handler;

import java.util.List;

/**
 * Interface that should be implemented by classes responsible for handling of data, extracted from log files
 */
public interface LogDataHandler {

    /**
     * @return type of data handler. Correct type of handler should be set in configuration file under key
     * commonProperties.dataHandleStrategy
     */
    String getHandlerType();


    /**
     * Handles accepted data
     * @param logData data extracted from log file
     */
    void handleData(List<String> logData);

}
