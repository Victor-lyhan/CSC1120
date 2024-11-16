package Week11.HW;/*
 * Course: CS-2852
 * HashMap Homework
 * Name: FIXME
 * Last Updated: FIXME
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Suite of JUnit tests for the hanl.SJHashMap class
 */
public class HashMapTests {
    private static final int[] KEYS = {4, 14, 24, 5, 15, 25, 3, 13, 23, 49};
    private static final String[] VALUES = {"apple", "jacks", "cocoa", "puffs", "hungry?",
            "SUGAR!!!!!!!", "oatmeal", "trix", "granola", "eggs"};
    private static final String OUTPUT1 = "{23=granola, 3=oatmeal, 4=apple, 24=cocoa, 5=puffs, " +
            "25=SUGAR!!!!!!!, 49=eggs, 13=trix, 14=jacks, 15=hungry?}";
    private static final String OUTPUT2 = "{23=granola, 3=oatmeal, 4=apple, 24=cocoa, 5=puffs, " +
            "25=SUGAR!!!!!!!, 49=eggs, null, 14=jacks, 15=hungry?}";
    private SJHashMap<Integer, String> map;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        map = new SJHashMap<>();
    }

    /**
     * Fills the table with the starting values
     */
    private void fillTable() {
        for(int i = 0; i < KEYS.length; ++i) {
            map.put(KEYS[i], VALUES[i]);
        }
    }

    @Test
    @DisplayName("Test size")
    void size() {
        for(int i = 0; i < KEYS.length; ++i) {
            Assertions.assertEquals(i, map.size(), "Expected " + i + " but was " + map.size());
            map.put(KEYS[i], VALUES[i]);
        }
        Assertions.assertEquals(KEYS.length, map.size(),
                "Expected " + KEYS.length + " but was " + map.size());
    }

    @Test
    @DisplayName("Test isEmpty")
    void isEmpty() {
        Assertions.assertTrue(map.isEmpty());
        map.put(1, "Hello");
        Assertions.assertFalse(map.isEmpty());
    }

    @Test
    @DisplayName("Test containsKey")
    void containsKey() {
        fillTable();
        for(Integer key : KEYS) {
            Assertions.assertTrue(map.containsKey(key));
        }
        Assertions.assertFalse(map.containsKey(0));
    }

    @Test
    @DisplayName("Test containsValue")
    void containsValue() {
        fillTable();
        for(String value : VALUES) {
            Assertions.assertTrue(map.containsValue(value));
        }
        Assertions.assertFalse(map.containsValue("Hello!"));
    }

    @Test
    @DisplayName("Test get")
    void get() {
        fillTable();
        for(int i = 0; i < KEYS.length; ++i) {
            Assertions.assertEquals(map.get(KEYS[i]), VALUES[i]);
        }
        Assertions.assertNull(map.get(0));
    }

    @Test
    @DisplayName("Test put")
    void put() {
        for(int i = 0; i < KEYS.length; ++i) {
            String s = map.put(KEYS[i], VALUES[i]);
            Assertions.assertTrue(map.containsKey(KEYS[i]));
            Assertions.assertTrue(map.containsValue(VALUES[i]));
            Assertions.assertNull(s);
        }
        String s = map.put(4, "pear");
        Assertions.assertEquals("apple", s);
    }

    @Test
    @DisplayName("Test remove")
    void remove() {
        fillTable();
        int size = map.size();
        Assertions.assertNull(map.remove(0));
        String s = map.remove(4);
        Assertions.assertEquals(size - 1, map.size());
        Assertions.assertEquals(s, "apple");
        Assertions.assertNull(map.get(4));
    }

    @Test
    @DisplayName("Test clear")
    void clear() {
        fillTable();
        Assertions.assertFalse(map.isEmpty());
        map.clear();
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        fillTable();
        Assertions.assertEquals(OUTPUT1, map.toString());
        final int keyToRemove = 13;
        map.remove(keyToRemove);
        Assertions.assertEquals(OUTPUT2, map.toString());
    }

    @Test
    @DisplayName("Test probing count")
    void probingCount() {
        fillTable();

        for (int key : KEYS) {
            map.get(key);
        }

        final double expectedNumProbes = 17;
        final int numFinds = 10;

        Assertions.assertEquals(expectedNumProbes / numFinds, map.averageProbes());
    }
}
