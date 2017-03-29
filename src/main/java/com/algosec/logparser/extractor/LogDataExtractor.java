package com.algosec.logparser.extractor;

import java.util.List;

/**
 * Interface that should be implemented by classes responsible for extraction  of data from log files
 */
public interface LogDataExtractor {

    /**
     * @return type of data extractor. Correct type of extractor should be set in configuration file under key
     * commonProperties.dataExtractStrategy
     */
    String getExtractorType();


    /**
     * By file path extracts file and then extracts log data from this file (each string represents separate log event)
     * @param filePath path where file can be found
     * @return list of log events in form of strings
     */
    List<String> extract(String filePath);

}
