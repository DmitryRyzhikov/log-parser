package com.algosec.logparser.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.algosec.logparser.misc.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AggregateHostUsageLogDataHandler implements LogDataHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateHostUsageLogDataHandler.class);

    public static final String TYPE = "aggregatedHostUsage";

    @Override
    public String getHandlerType() {
        return TYPE;
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
        Map<String, Integer> hostUsageData = new HashMap<>();
        for (String s : logData) {
            String[] array = s.split("[ \\t]+", -1);

            String host = array[15];

            if(hostUsageData.get(host) == null){
                hostUsageData.put(host, 1);
            }else{
                Integer numberOfTimesUsed = hostUsageData.get(host);
                hostUsageData.put(host, ++numberOfTimesUsed);
            }
        }
        return hostUsageData;
    }

}
