package org.examples.pbk.otus.l131homework.dbservice;

import org.examples.pbk.otus.l131homework.cache.CacheEngine;
import org.examples.pbk.otus.l131homework.dao.GenericDataSetHibernateDao;
import org.examples.pbk.otus.l131homework.dataset.UsersDataSet;

public class UsersCachedHibernateDbService extends GenericCachedHibernateDbService<UsersDataSet> {
    public UsersCachedHibernateDbService(GenericDataSetHibernateDao<UsersDataSet> dao, CacheEngine<Long, UsersDataSet> cacheEngine) {
        super(dao, cacheEngine);
    }
}
