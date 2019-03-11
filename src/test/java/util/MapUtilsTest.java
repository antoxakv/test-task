package util;

import com.test.utils.MapUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MapUtilsTest {

    private Map<Integer, String> map = new HashMap<>(5);

    @Before
    public void init() {
        map.put(1, "b");
        map.put(2, "a");
        map.put(3, "c");
        map.put(4, "e");
        map.put(5, "d");
    }

    @Test
    public void testSortedMap() {
        Map<Integer, String> sortedMap = MapUtils.sortMap(map);
        Object[] entries = sortedMap.entrySet().toArray();

        Map.Entry<Integer, String> a = (Map.Entry<Integer, String>) entries[0];
        check(entries, 0, 2, "a");
        check(entries, 1, 1, "b");
        check(entries, 2, 3, "c");
        check(entries, 3, 5, "d");
        check(entries, 4, 4, "e");

    }

    private void check(Object[] entries, int i, Integer keyExpected, String valueExpected) {
        Map.Entry<Integer, String> a = (Map.Entry<Integer, String>) entries[i];
        Assert.assertEquals("Key #" + i + " not " + keyExpected, keyExpected, a.getKey());
        Assert.assertEquals("Value #" + i + " not " + valueExpected, valueExpected, a.getValue());
    }

}
