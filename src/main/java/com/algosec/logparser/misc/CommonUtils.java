package com.algosec.logparser.misc;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);


    private CommonUtils() {
    }


    /**
     * Sorts map by values in descending order
     *
     * @param map map that shuld be sorted
     * @param <K> map key
     * @param <V> map value
     * @return sorted map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        if (map == null) {
            return null;
        }

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


    /**
     * Pretty prints map - every entry pair is printed on new string, also meta-information is
     * added to make date more clear - that key is Host and value is Count.
     *
     * @param map map the should be pretty printed
     * @param <K> map key
     * @param <V> map value
     */
    public static <K, V> void mapPrettyPrint(Map<K, V> map) {
        if (map == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            sb.append("     Host: ");
            sb.append(entry.getKey());
            sb.append(", Count: ");
            sb.append(entry.getValue());
            if (iter.hasNext()) {
                sb.append("\n");
            }
        }
        LOGGER.info("{}", sb.toString());
    }


}
