package org.examples.pbk.otus.l111homework.dbservice;

import org.examples.pbk.otus.l111homework.dataset.DataSet;

public interface DbService<T extends DataSet> {

    void create(T object);

    T read(Long id);
}
