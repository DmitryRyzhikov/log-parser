/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.algosec.logparser.service;

import java.util.List;

import com.algosec.logparser.config.CommonProperties;
import com.algosec.logparser.handler.LogDataHandler;
import com.algosec.logparser.extractor.LogDataExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogParserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogParserService.class);

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private List<LogDataExtractor> dataExtractors;

    @Autowired
    private List<LogDataHandler> dataHandlers;


    public void parseAndHandleData() {
        String fileURL = commonProperties.getFileURL();
        String dataExtractStrategy = commonProperties.getDataExtractStrategy();
        String dataHandleStrategy = commonProperties.getDataHandleStrategy();

        LOGGER.info(
                "File parsing URL [{}]. Data extraction type [{}]. Data handling strategy [{}]",
                fileURL, dataExtractStrategy, dataHandleStrategy
        );

        // extract data from file using selected strategy
        List<String> extractedData;
        LogDataExtractor dataExtractor = getLogDataExtractor(dataExtractStrategy);
        if (dataExtractor != null) {
            extractedData = dataExtractor.extract(fileURL);
        } else {
            LOGGER.warn("Extractor for strategy [{}] not found. Please, check you configuration.", dataExtractStrategy);
            // if data extractor is not found - it is pointless to continue program
            return;
        }

        // handle extracted data using selected strategy
        LogDataHandler logDataHandler = getLogDataHandler(dataHandleStrategy);
        if (logDataHandler != null) {
            logDataHandler.handleData(extractedData);
        } else {
            LOGGER.warn("Handler for strategy [{}] not found. Please, check you configuration. ", dataHandleStrategy);
        }
    }


    private LogDataHandler getLogDataHandler(String handlerType) {
        return dataHandlers
                .stream()
                .filter(handler -> handler.getHandlerType().equals(handlerType))
                .findAny()
                .orElse(null);
    }


    private LogDataExtractor getLogDataExtractor(String extractorType) {
        return dataExtractors
                .stream()
                .filter(extractor -> extractor.getExtractorType().equals(extractorType))
                .findAny()
                .orElse(null);
    }

}
