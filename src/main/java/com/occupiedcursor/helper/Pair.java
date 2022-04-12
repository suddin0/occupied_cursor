package com.occupiedcursor.helper;

/**
 * This class helps store a key value pair
 * @param <K> The data type of the key element
 * @param <V> the data type of the value element
 */
public class Pair <K, V>{
    private K key = null;
    private V value = null;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V pair) {
        this.value = pair;
    }

    @Override
    public String toString() {
        return "" + key + " : " + value + "";
    }
}
