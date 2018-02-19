package org.examples.pbk.otus.l111homework.dbservice;

import org.examples.pbk.otus.l111homework.cache.CacheEngine;
import org.examples.pbk.otus.l111homework.dao.GenericDataSetHibernateDao;
import org.examples.pbk.otus.l111homework.dataset.UsersDataSet;

public class UsersCachedHibernateDbService extends GenericCachedHibernateDbService<UsersDataSet> {
    public UsersCachedHibernateDbService(GenericDataSetHibernateDao<UsersDataSet> dao, CacheEngine<Long, UsersDataSet> cacheEngine) {
        super(dao, cacheEngine);
    }
}
