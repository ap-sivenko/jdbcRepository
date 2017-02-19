package com.netcracker.jdbc.repository;


import java.util.List;
import java.util.Optional;

public interface JdbcRepository<T extends Persistable<ID>, ID extends Number> {
    Optional<T> findOne(ID id);
    List<T> findAll();
    Optional<T> save(T entity);
    void delete(ID id);
    void deleteAll();
    long count();
    Optional<T> queryForObject(String sql, Object ... args);
    List<T> queryForList(String sql, Object... args);
}
