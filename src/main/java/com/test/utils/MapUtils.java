package com.test.utils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils {

    public static Map<Integer, String> sortMap(Map<Integer, String> map) {
        return map.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (s1, s2) -> s1.compareTo(s2) > 0 ? s1 + "|" + s2 : s2 + "|" + s1, LinkedHashMap::new));
    }

}
