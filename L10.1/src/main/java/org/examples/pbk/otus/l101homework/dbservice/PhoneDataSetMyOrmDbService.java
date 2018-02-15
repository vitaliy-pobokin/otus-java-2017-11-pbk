package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dao.PhoneDataSetMyOrmDao;
import org.examples.pbk.otus.l101homework.dataset.PhoneDataSet;

public class PhoneDataSetMyOrmDbService extends GenericMyOrmDbService<PhoneDataSet> {
    public PhoneDataSetMyOrmDbService(PhoneDataSetMyOrmDao dao) {
        super(dao);
    }
}
