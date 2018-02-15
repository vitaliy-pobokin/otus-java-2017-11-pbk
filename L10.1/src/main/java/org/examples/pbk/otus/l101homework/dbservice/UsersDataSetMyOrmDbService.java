package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dao.UsersDataSetMyOrmDao;
import org.examples.pbk.otus.l101homework.dataset.UsersDataSet;

public class UsersDataSetMyOrmDbService extends GenericMyOrmDbService<UsersDataSet> {
    public UsersDataSetMyOrmDbService(UsersDataSetMyOrmDao dao) {
        super(dao);
    }
}
