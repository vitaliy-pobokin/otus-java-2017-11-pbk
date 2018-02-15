package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dao.AddressDataSetHibernateDao;
import org.examples.pbk.otus.l101homework.dataset.AddressDataSet;

public class AddressDataSetHibernateDbService extends GenericHibernateDbService<AddressDataSet> {
    public AddressDataSetHibernateDbService(AddressDataSetHibernateDao dao) {
        super(dao);
    }
}
