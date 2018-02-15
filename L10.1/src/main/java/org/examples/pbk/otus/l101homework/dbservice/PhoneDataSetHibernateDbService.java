package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dao.PhoneDataSetHibernateDao;
import org.examples.pbk.otus.l101homework.dataset.PhoneDataSet;
import org.examples.pbk.otus.l101homework.dataset.PhoneDataSetHibernate;

public class PhoneDataSetHibernateDbService extends GenericHibernateDbService<PhoneDataSetHibernate> {
    public PhoneDataSetHibernateDbService(PhoneDataSetHibernateDao dao) {
        super(dao);
    }
}
