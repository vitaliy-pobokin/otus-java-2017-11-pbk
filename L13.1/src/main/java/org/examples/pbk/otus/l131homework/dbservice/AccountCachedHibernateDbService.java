package org.examples.pbk.otus.l131homework.dbservice;

import org.examples.pbk.otus.l131homework.cache.CacheEngine;
import org.examples.pbk.otus.l131homework.dao.AccountDataSetHibernateDao;
import org.examples.pbk.otus.l131homework.dataset.AccountDataSet;

public class AccountCachedHibernateDbService extends GenericCachedHibernateDbService<AccountDataSet> {

    private AccountDataSetHibernateDao dao;

    public AccountCachedHibernateDbService(AccountDataSetHibernateDao dao, CacheEngine<Long, AccountDataSet> cacheEngine) {
        super(dao, cacheEngine);
        this.dao = dao;
    }

    public AccountDataSet findByUsername(String username) {
        return runInSession(session -> {
            dao.setSession(session);
            return dao.findByUsername(username);
        });
    }
}
