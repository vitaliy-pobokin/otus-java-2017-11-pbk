package org.examples.pbk.otus.l101homework.dao;

import org.examples.pbk.otus.l101homework.dataset.UsersDataSet;
import org.examples.pbk.otus.l101homework.myorm.OrmEntityManager;

public class UsersDataSetMyOrmDao extends GenericDataSetMyOrmDao<UsersDataSet> {
    public UsersDataSetMyOrmDao(OrmEntityManager entityManager) {
        super(entityManager);
    }
}
