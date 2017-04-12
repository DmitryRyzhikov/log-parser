package com.algosec.logparser.handler;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.algosec.logparser.misc.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConcurrentAggregateHostUsageLogDataHandler implements LogDataHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentAggregateHostUsageLogDataHandler.class);

    public static final String TYPE = "concurrentAggregatedHostUsage";

    @Override
    public String getHandlerType() {
        return TYPE;
    }

    @PostConstruct
    public void init(){

    }

    @Override
    public void handleData(List<String> logData) {
        LOGGER.info("Starting data handling for strategy [{}].", TYPE);

        Map<String, Integer> aggregateData = prepareAggregateData(logData);

        // sort by value (usage)
        Map<String, Integer> sortedByUsageDesc = CommonUtils.sortByValue(aggregateData);

        // pretty print
        CommonUtils.mapPrettyPrint(sortedByUsageDesc);
    }


    public Map<String, Integer> prepareAggregateData(List<String> logData) {
        Instant start = Instant.now();

        ConcurrentMap<String, Integer> hostUsageData = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newWorkStealingPool();
        for (String eventString : logData) {
            executor.submit(() -> {
                String[] array = eventString.split("[ \\t]+", -1);

                String host = array[15];

                Integer num = hostUsageData.putIfAbsent(host, 1);
                if (num != null) {
                    hostUsageData.computeIfPresent(host, (key, value) -> ++value);
                }
            });
        }

        // wait while all tasks will be terminated
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Error on executor termination");
        }

        Instant end = Instant.now();
        LOGGER.info("Data preparation took {} millis", Duration.between(start, end).toMillis());

        return hostUsageData;
    }

}
