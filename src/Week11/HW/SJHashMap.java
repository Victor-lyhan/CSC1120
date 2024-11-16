package Week11.HW;/*
 * Course: CS-2852
 * HashMap Homework
 * Name: Victor Han
 * Last Updated: 11/15/2024
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Simplified implementation of a Hashmap
 * @param <K> Generic type for a Key object
 * @param <V> Generic type for a Value object
 */
public class SJHashMap<K, V> implements Map<K, V> {
    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }

    private static final int STARTING_CAPACITY = 10;
    private static final double LOAD_THRESHOLD = 0.75;
    private final Entry<K, V> deletedKey = new Entry<>(null, null);
    private Entry<K, V>[] entries;
    private int numKeys;
    private int numDeletes;
    protected int totalProbes;
    protected int finds;

    /**
     * Constructor that sets a given starting capacity
     *
     * @param capacity starting capacity of the table
     */
    public SJHashMap(int capacity) {
        this.numKeys = 0;
        this.numDeletes = 0;
        this.totalProbes = 0;
        this.finds = 0;
        entries = new Entry[capacity];
    }

    /**
     * Constructor that uses the default starting capacity
     */
    public SJHashMap() {
        this(STARTING_CAPACITY);
    }

    @Override
    public int size() {
        return this.numKeys;
    }

    @Override
    public boolean isEmpty() {
        return this.numKeys == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return findKey(key) != -1;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Entry<K, V> entry : entries) {
            if (entry != null && entry != deletedKey && entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = findKey(key);
        if (index == -1) {
            return null;
        }
        return entries[index].getValue();
    }

    @Override
    public V put(K key, V value) {
        if ((numKeys + numDeletes) > (LOAD_THRESHOLD * entries.length)) {
            rehash();
        }
        int index = Math.abs(key.hashCode() % entries.length);
        while (entries[index] != null && entries[index] != deletedKey) {
            if (entries[index].getKey().equals(key)) {
                return entries[index].setValue(value);
            }
            index = (index + 1) % entries.length;
        }
        entries[index] = new Entry<>(key, value);
        numKeys++;
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = findKey(key);
        if (index == -1) return null;
        V value = entries[index].getValue();
        entries[index] = deletedKey;
        numKeys--;
        numDeletes++;
        return value;
    }

    @Override
    public void clear() {
        numKeys = 0;
        numDeletes = 0;
        totalProbes = 0;
        finds = 0;
        Arrays.fill(entries, null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;

        for (Entry<K, V> entry : entries) {
            if (entry != null) {
                if (!first) {
                    sb.append(", ");
                }
                if (entry == deletedKey) {
                    sb.append("null");
                } else {
                    sb.append(entry.toString());
                }
                first = false;
            }
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * Calculates the ratio of the number of probes per times find is called
     * @return average number of probes per find
     */
    public double averageProbes() {
        return finds == 0 ? 0.0 : (double) totalProbes / finds;
    }

    private int findKey(Object key) {
        int index = Math.abs(key.hashCode() % entries.length);
        int start = index;
        int probes = 0;

        do {
            probes++;
            if (entries[index] == null) {
                finds++;
                totalProbes += probes;
                return -1;
            }
            if (entries[index] != deletedKey && entries[index].getKey().equals(key)) {
                finds++;
                totalProbes += probes;
                return index;
            }
            index = (index + 1) % entries.length;
        } while (index != start);
        finds++;
        totalProbes += probes;
        return -1;
    }


    private void rehash() {
        Entry<K, V>[] oldEntries = entries;
        entries = new Entry[entries.length * 2 + 1];
        numKeys = 0;
        numDeletes = 0;
        for (Entry<K, V> entry : oldEntries) {
            if (entry != null && entry != deletedKey) {
                int index = Math.abs(entry.getKey().hashCode() % entries.length);
                while (entries[index] != null) {
                    index = (index + 1) % entries.length;
                }
                entries[index] = entry;
                numKeys++;
            }
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
