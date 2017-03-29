package com.algosec.logparser.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StringLogDataExtractor implements LogDataExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringLogDataExtractor.class);

    public static final String TYPE = "stringExtraction";


    @Override
    public String getExtractorType() {
        return TYPE;
    }


    public List<String> extract(String filePath) {
        LOGGER.info("Starting data extraction from file [{}].", filePath);
        List<String> result = new ArrayList<String>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            result = stream
                    .filter(line -> !line.startsWith("#"))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            LOGGER.error("Exception on attempt to extract data from file [{}].", filePath, ex);
        }

        LOGGER.info("Data extraction from [{}] finished successfully. Events extracted [{}]", filePath, result.size());
        return result;
    }

}
