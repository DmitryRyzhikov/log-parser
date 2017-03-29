package com.algosec.logparser.extractor;

import java.util.List;

import com.algosec.logparser.TestUtils;
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
public class StringLogDataExtractorTest {

    @Autowired
    @Qualifier("stringLogDataExtractor")
    LogDataExtractor stringLogDataExtractor;

    @Test
    public void shouldReturnCorrectTypeWhenGetTypeCalled(){
        assertThat(stringLogDataExtractor.getExtractorType(), is("stringExtraction"));
    }

    @Test
    public void shouldReturnEmptyListWhenAllLinesOfLogFileStartedWithComment(){
        String path = TestUtils.convertRelativePathToFileToAbsolute(
                "com/algosec/logparser/extractor/log_with_comments_only.log"
        );
        List<String> result = stringLogDataExtractor.extract(path);
        assertThat(result.size(), is(0));
    }

    @Test
    public void shouldReturnEmptyListWhenLogFileIsEmpty(){
        String path = TestUtils.convertRelativePathToFileToAbsolute(
                "com/algosec/logparser/extractor/log_empty.log"
        );
        List<String> result = stringLogDataExtractor.extract(path);
        assertThat(result.size(), is(0));
    }

    @Test
    public void shouldReturnCorrectNumberOfEventsWhenLogFileContainsBothEventsAndComments(){
        String path = TestUtils.convertRelativePathToFileToAbsolute(
                "com/algosec/logparser/extractor/log_with_two_comments_and_two_events.log"
        );
        List<String> result = stringLogDataExtractor.extract(path);
        assertThat(result.size(), is(2));
    }

    @Test
    public void shouldReturnCorrectNumberOfEventsWhenLogFileContainsEventsOnly(){
        String path = TestUtils.convertRelativePathToFileToAbsolute(
                "com/algosec/logparser/extractor/log_with_two_events_only.log"
        );
        List<String> result = stringLogDataExtractor.extract(path);
        assertThat(result.size(), is(2));
    }


}
