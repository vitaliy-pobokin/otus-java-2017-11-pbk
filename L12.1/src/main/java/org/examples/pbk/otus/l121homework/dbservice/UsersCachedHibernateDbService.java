package org.examples.pbk.otus.l121homework.dbservice;

import org.examples.pbk.otus.l121homework.cache.CacheEngine;
import org.examples.pbk.otus.l121homework.dao.GenericDataSetHibernateDao;
import org.examples.pbk.otus.l121homework.dataset.UsersDataSet;

public class UsersCachedHibernateDbService extends GenericCachedHibernateDbService<UsersDataSet> {
    public UsersCachedHibernateDbService(GenericDataSetHibernateDao<UsersDataSet> dao, CacheEngine<Long, UsersDataSet> cacheEngine) {
        super(dao, cacheEngine);
    }
}
