package org.examples.pbk.otus.l111homework.cache;

public interface CacheEngine<K, V> {

    void put(Element<K, V> element);

    Element<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}

