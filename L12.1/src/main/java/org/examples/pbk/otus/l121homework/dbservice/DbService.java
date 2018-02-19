package org.examples.pbk.otus.l121homework.dbservice;

import org.examples.pbk.otus.l121homework.dataset.DataSet;

public interface DbService<T extends DataSet> {

    void create(T object);

    T read(Long id);
}
