package org.examples.pbk.otus.l111homework.dao;

import org.examples.pbk.otus.l111homework.dataset.DataSet;

import java.util.List;

public interface GenericDataSetDao<T extends DataSet> {

    void create(T object);

    T read(Long id);

    List<T> readAll();

    void update(T object);

    void delete(T object);
}
