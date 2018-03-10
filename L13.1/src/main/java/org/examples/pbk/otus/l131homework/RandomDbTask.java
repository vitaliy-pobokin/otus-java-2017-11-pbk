package org.examples.pbk.otus.l131homework;

import org.examples.pbk.otus.l131homework.cache.CacheEngineImpl;
import org.examples.pbk.otus.l131homework.dao.UsersDataSetHibernateDao;
import org.examples.pbk.otus.l131homework.dataset.UsersDataSet;
import org.examples.pbk.otus.l131homework.dbservice.DbService;
import org.examples.pbk.otus.l131homework.dbservice.UsersCachedHibernateDbService;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class RandomDbTask {
    private Timer timer = new Timer("RandomDbTask Timer");
    private TimerTask timerTask;

    public RandomDbTask(CacheEngineImpl<Long, UsersDataSet> cacheEngine) {
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                DbService<UsersDataSet> cachedDbService = new UsersCachedHibernateDbService(new UsersDataSetHibernateDao(), cacheEngine);
                Random random = new Random();
                if (random.nextBoolean()) {
                    for (int i = 0; i < random.nextInt(100); i++) {
                        String name = UUID.randomUUID().toString();
                        int age = random.nextInt(90);
                        cachedDbService.create(new UsersDataSet(-1, name, age));
                    }
                } else {
                    for (int i = 0; i < random.nextInt(100); i++) {
                        cachedDbService.read((long) random.nextInt(cacheEngine.getCacheSize() * 2 + 1));
                    }
                }
            }
        };
    }

    public void start(long delay, long period) {
        timer.scheduleAtFixedRate(timerTask, delay, period);
    }

    public void stop() {
        timerTask.cancel();
        timer.cancel();
    }
}
