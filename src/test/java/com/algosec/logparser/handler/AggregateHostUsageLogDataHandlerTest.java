package com.algosec.logparser.handler;

import java.util.List;
import java.util.Map;

import com.algosec.logparser.TestUtils;
import com.algosec.logparser.extractor.LogDataExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregateHostUsageLogDataHandlerTest {

    @Autowired
    @Qualifier("stringLogDataExtractor")
    LogDataExtractor stringLogDataExtractor;

    @Autowired
    AggregateHostUsageLogDataHandler logDataHandler;


    @Test
    public void shouldReturnCorrectTypeWhenGetTypeCalled() {
        assertThat(logDataHandler.getHandlerType(), is("aggregatedHostUsage"));
    }


    @Test
    public void shouldCorrecltyAgregateDataWhenCorrectLogFileIsPassed() {
        String path = TestUtils.convertRelativePathToFileToAbsolute(
                "com/algosec/logparser/handler/log_with_five_events_of_two_hosts.log"
        );
        List<String> result = stringLogDataExtractor.extract(path);
        assertThat(result.size(), is(5));

        Map<String, Integer> aggregateData = logDataHandler.prepareAggregateData(result);

        assertThat(aggregateData.size(), is(2));
        assertThat(aggregateData.get("kh.google.com"), is(3));
        assertThat(aggregateData.get("personal.avira-update.com"), is(2));
    }


}
