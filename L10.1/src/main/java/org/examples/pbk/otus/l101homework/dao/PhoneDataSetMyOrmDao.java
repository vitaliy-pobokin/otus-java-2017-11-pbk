package org.examples.pbk.otus.l101homework.dao;

import org.examples.pbk.otus.l101homework.dataset.PhoneDataSet;
import org.examples.pbk.otus.l101homework.myorm.OrmEntityManager;

public class PhoneDataSetMyOrmDao extends GenericDataSetMyOrmDao<PhoneDataSet> {
    public PhoneDataSetMyOrmDao(OrmEntityManager entityManager) {
        super(entityManager);
    }
}
