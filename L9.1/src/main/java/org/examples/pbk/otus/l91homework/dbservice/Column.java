package org.examples.pbk.otus.l91homework.dbservice;

public class Column {
    private String name;
    private Class<?> javaType;
    private String dbType;

    Column(String name, Class<?> javaType, String dbType) {
        this.name = name;
        this.javaType = javaType;
        this.dbType = dbType;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Class<?> getJavaType() {
        return javaType;
    }

    void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    String getDbType() {
        return dbType;
    }

    void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
