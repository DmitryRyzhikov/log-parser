package com.algosec.logparser.misc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class CommonUtilsTest {

    @Test
    public void shouldReturnNullWhenNullIsPassedToMapByValue() {
        Map<String, Integer> result = CommonUtils.sortByValue(null);
        assertThat(result, nullValue());
    }


    @Test
    public void shouldReturnEmptyMapWhenEmptyMapIsPassedToMapByValue() {
        Map<String, Integer> result = CommonUtils.sortByValue(new HashMap<String, Integer>());
        assertThat(result, notNullValue());
        assertThat(result.size(), is(0));
    }


    @Test
    public void shouldSortInDescendingOrderMapValuesWhenCorrectMapIsPassed() {
        Map<String, Integer> initialMap = new HashMap<>();
        initialMap.put("one", 1);
        initialMap.put("two", 2);
        initialMap.put("three", 3);

        LinkedHashMap<String, Integer> result = (LinkedHashMap) CommonUtils.sortByValue(initialMap);
        assertThat(result, notNullValue());
        assertThat(result.size(), is(3));


        Iterator<Map.Entry<String, Integer>> iterator = result.entrySet().iterator();

        Map.Entry<String, Integer> entry = iterator.next();
        assertThat(entry.getKey(), is("three"));
        assertThat(entry.getValue(), is(3));

        entry = iterator.next();
        assertThat(entry.getKey(), is("two"));
        assertThat(entry.getValue(), is(2));

        entry = iterator.next();
        assertThat(entry.getKey(), is("one"));
        assertThat(entry.getValue(), is(1));
    }

}
