package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dao.UsersDataSetHibernateDao;
import org.examples.pbk.otus.l101homework.dataset.UsersDataSet;

public class UsersDataSetHibernateDbService extends GenericHibernateDbService<UsersDataSet> {
    public UsersDataSetHibernateDbService(UsersDataSetHibernateDao dao) {
        super(dao);
    }
}
