package com.netcracker.jdbc.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class GenericJdbcRepository<T extends Persistable<ID>, ID extends Number> implements JdbcRepository<T,ID> {

    protected JdbcTemplate jdbcTemplate;
    protected final String TABLE_NAME;
    protected final String ID_COLUMN;
    private SimpleJdbcInsert insertEntity;
    private String findOneQuery;
    private String findAllQuery;
    private String updateOneQuery;
    private String deleteOneQuery;
    private String deleteAllQuery;
    private String countQuery;


    public GenericJdbcRepository(String TABLE_NAME, String ID_COLUMN) {
        this.TABLE_NAME = TABLE_NAME;
        this.ID_COLUMN = ID_COLUMN;

    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertEntity = new SimpleJdbcInsert(dataSource)
                .withTableName(this.TABLE_NAME)
                .usingGeneratedKeyColumns(this.ID_COLUMN);
    }

    @Override
    public Optional<T> findOne(ID id) {
        T object = this.jdbcTemplate.queryForObject(this.buildFindOneQuery(), new Object[]{id}, this.mapRow());
        return Optional.ofNullable(object);
    }

    @Override
    public List<T> findAll() {
        List<T> result = this.jdbcTemplate.query(this.buildFindAllQuery(), this.mapRow());
        return result;
    }

    @Override
    public Optional<T> save(T entity) {
        if (entity.getId() != null){
            this.jdbcTemplate.update(this.buildUpdateQuery(entity), this.buildUpdateValues(entity));
            return Optional.ofNullable(entity);
        } else {
            ID id = (ID) insertEntity.executeAndReturnKey(this.mapColumns(entity));
            entity.setId(id);
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public void delete(ID id) {
        this.jdbcTemplate.update(this.buildDeleteOneQuery(), id);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update(this.buildDeleteAllQuery());
    }

    @Override
    public long count() {
        return this.jdbcTemplate.queryForObject(this.buildCountQuery(), Integer.class);
    }

    private String buildFindOneQuery(){
        if (findOneQuery == null){
            findOneQuery = new StringBuilder("SELECT * FROM ")
                    .append(this.TABLE_NAME)
                    .append(" WHERE ")
                    .append(this.ID_COLUMN)
                    .append(" = ?").toString();
        }
        return findOneQuery;
    }

    private String buildFindAllQuery(){
        if (findAllQuery == null){
            findAllQuery = new StringBuilder("SELECT * FROM ")
                    .append(this.TABLE_NAME).toString();
        }
        return findAllQuery;
    }

    private String buildDeleteOneQuery(){
        if (deleteOneQuery == null){
            deleteOneQuery = new StringBuilder("DELETE FROM ")
                    .append(this.TABLE_NAME)
                    .append(" WHERE ")
                    .append(this.ID_COLUMN).append(" = ?").toString();
        }
        return deleteOneQuery;
    }

    private String buildDeleteAllQuery(){
        if (deleteAllQuery == null){
            deleteAllQuery = new StringBuilder("DELETE FROM ")
                    .append(this.TABLE_NAME).toString();
        }
        return deleteAllQuery;
    }

    private String buildCountQuery(){
        if (countQuery == null){
            countQuery = new StringBuilder("SELECT COUNT(*) FROM ")
                    .append(this.TABLE_NAME).toString();
        }
        return countQuery;
    }


    private String buildUpdateQuery(T entity){
        if (updateOneQuery == null){
            Map<String, Object> columns = this.mapColumns(entity);
            StringBuilder sql = new StringBuilder("UPDATE ")
                    .append(this.TABLE_NAME)
                    .append(" SET ");
            columns.keySet().stream().forEachOrdered(key -> sql.append(key).append(" = ?, "));
            updateOneQuery = sql.deleteCharAt(sql.lastIndexOf(","))
                    .append(" WHERE ")
                    .append(this.ID_COLUMN)
                    .append(" = ?").toString();
        }
        return updateOneQuery;
    }

    private Object[] buildUpdateValues(T entity){
        List<Object> values = new ArrayList<>();
        Map<String, Object> columns = this.mapColumns(entity);
        columns.keySet().stream().forEachOrdered(key -> values.add(columns.get(key)));
        values.add(entity.getId());
        return values.toArray();
    }

    public abstract  Map<String, Object> mapColumns(T entity);
    public abstract RowMapper<T> mapRow();
}
