package com.axialworks.modules.legacy.openapi_tools;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class OpenApiDataUtils {

    private OpenApiDataUtils() { }

    static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    static <K, V> Map<K, V> emptyIfNull(Map<K, V> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }
}
