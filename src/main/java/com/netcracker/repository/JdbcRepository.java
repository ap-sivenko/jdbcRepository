package com.netcracker.repository;


import com.netcracker.repository.util.Persistable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface JdbcRepository<T extends Persistable<ID>, ID extends Serializable> {
    Optional<T> findOne(ID id);
    List<T> findAll();
    Optional<T> save(T entity);
    void delete(ID id);
    void deleteAll();
    long count();
}
