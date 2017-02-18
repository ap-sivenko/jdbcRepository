package com.netcracker.jdbc.repository;


public interface Persistable<ID extends Number> {
    ID getId();
    void setId(ID id);
}
