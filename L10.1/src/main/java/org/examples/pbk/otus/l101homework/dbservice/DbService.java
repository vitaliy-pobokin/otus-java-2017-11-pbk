package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dataset.DataSet;

import java.util.List;

public interface DbService<T extends DataSet> {

    void create(T object);

    T read(Long id);

    List<T> readAll();

    void update(T object);

    void delete(T object);
}
