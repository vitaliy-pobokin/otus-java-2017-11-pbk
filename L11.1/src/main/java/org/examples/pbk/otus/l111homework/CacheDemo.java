package org.examples.pbk.otus.l111homework;

import org.examples.pbk.otus.l111homework.cache.CacheEngine;
import org.examples.pbk.otus.l111homework.cache.CacheEngineImpl;
import org.examples.pbk.otus.l111homework.dao.UsersDataSetHibernateDao;
import org.examples.pbk.otus.l111homework.dataset.UsersDataSet;
import org.examples.pbk.otus.l111homework.dbservice.DbService;
import org.examples.pbk.otus.l111homework.dbservice.UsersCachedHibernateDbService;

import java.util.Random;
import java.util.UUID;

public class CacheDemo {
    public static void main(String[] args) {
        CacheEngine<Long, UsersDataSet> cacheEngine = new CacheEngineImpl<>(5000, 1000);
        DbService<UsersDataSet> cachedDbService = new UsersCachedHibernateDbService(new UsersDataSetHibernateDao(), cacheEngine);
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            String name = UUID.randomUUID().toString();
            int age = random.nextInt(90);
            cachedDbService.create(new UsersDataSet(-1, name, age));
        }
        for (int i = 0; i < 100; i++) {
            cachedDbService.read((long) random.nextInt(110));
        }
        System.out.printf("Hit: %d, Miss: %d\n", cacheEngine.getHitCount(), cacheEngine.getMissCount());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            cachedDbService.read((long) random.nextInt(100));
        }
        System.out.printf("Hit: %d, Miss: %d\n", cacheEngine.getHitCount(), cacheEngine.getMissCount());

        cacheEngine.dispose();
        PersistenceManager.getInstance().close();
    }
}
