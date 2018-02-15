package org.examples.pbk.otus.l101homework.dao;

import org.examples.pbk.otus.l101homework.dataset.AddressDataSet;
import org.examples.pbk.otus.l101homework.myorm.OrmEntityManager;

public class AddressDataSetMyOrmDao extends GenericDataSetMyOrmDao<AddressDataSet> {
    public AddressDataSetMyOrmDao(OrmEntityManager entityManager) {
        super(entityManager);
    }
}
