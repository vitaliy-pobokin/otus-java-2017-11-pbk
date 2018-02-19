package org.examples.pbk.otus.l121homework.cache;

import java.lang.ref.SoftReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {

    private ConcurrentHashMap<K, SoftReference<Element<K, V>>> cache;
    private int hit;
    private int miss;
    private final long lifeTime;
    private final long idleTime;

    private Timer timer;

    public CacheEngineImpl(long lifeTime, long idleTime) {
        if (lifeTime < 0 || idleTime < 0) throw new IllegalArgumentException();
        this.lifeTime = lifeTime;
        this.idleTime = idleTime;
        cache = new ConcurrentHashMap<>();
        if (lifeTime != 0 || idleTime != 0) {
            initExpirationTask();
        }
    }

    @Override
    public void put(Element<K, V> element) {
        cache.put(element.getKey(), new SoftReference<>(element));
    }

    @Override
    public Element<K, V> get(K key) {
        SoftReference<Element<K, V>> reference = cache.get(key);
        if (reference != null) {
            Element<K, V> element = reference.get();
            if (element != null) {
                hit++;
                element.setAccessed();
                return element;
            }
        }
        miss++;
        return null;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    public int getCacheSize() {
        return cache.size();
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public long getIdleTime() {
        return idleTime;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private void initExpirationTask() {
        long expirationTaskPeriod;
        if (lifeTime == 0 || idleTime == 0) {
            expirationTaskPeriod = lifeTime > idleTime ? lifeTime : idleTime;
        } else {
            expirationTaskPeriod = lifeTime < idleTime ? lifeTime : idleTime;
        }
        Timer timer = new Timer("Expiration Timer");
        TimerTask expirationTask = new TimerTask() {
            @Override
            public void run() {
                for (K key : cache.keySet()) {
                    SoftReference<Element<K, V>> reference = cache.get(key);
                    if (reference != null) {
                        Element<K, V> element = reference.get();
                        if (element != null && isExpire(element)) {
                            cache.remove(key);
                        }
                    }
                }
            }
        };
        this.timer = timer;
        timer.scheduleAtFixedRate(expirationTask, 0, expirationTaskPeriod);
    }

    private boolean isExpire(Element<K, V> element) {
        return (lifeTime > 0 && System.currentTimeMillis() - element.getCreationTime() > lifeTime) ||
                (idleTime > 0 && System.currentTimeMillis() - element.getLastAccessTime() > idleTime);
    }
}
