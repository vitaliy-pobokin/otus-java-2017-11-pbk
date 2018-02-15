package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dao.AddressDataSetMyOrmDao;
import org.examples.pbk.otus.l101homework.dataset.AddressDataSet;

public class AddressDataSetMyOrmDbService extends GenericMyOrmDbService<AddressDataSet> {
    public AddressDataSetMyOrmDbService(AddressDataSetMyOrmDao dao) {
        super(dao);
    }
}
