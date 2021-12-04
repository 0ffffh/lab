package com.k0s.datastructures.map;

import java.util.Iterator;

public interface Map<K,V> extends Iterable<Map.Entry<K,V>>{


    int size();

    boolean isEmpty();

    void clear();

    V get(K key);

    V put(K key, V value);

    V remove(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);


    Iterator iterator();

    interface Entry<K,V> {

        K getKey();

        V getValue();
    }

}
