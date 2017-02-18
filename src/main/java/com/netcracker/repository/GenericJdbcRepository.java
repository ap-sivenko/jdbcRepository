package com.netcracker.repository;


import com.netcracker.repository.util.Persistable;
import com.netcracker.repository.util.UpdateQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class GenericJdbcRepository<T extends Persistable<ID>, ID extends Serializable> implements JdbcRepository<T,ID> {

    protected JdbcTemplate jdbcTemplate;
    protected String tableName;
    protected String idColumnName;
    private SimpleJdbcInsert insertEntity;

    public GenericJdbcRepository(DataSource dataSource, String tableName, String idColumnName) {
        this.tableName = tableName;
        this.idColumnName = idColumnName;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertEntity = new SimpleJdbcInsert(dataSource)
                .withTableName(this.tableName)
                .usingGeneratedKeyColumns(this.idColumnName);
    }

    @Override
    public Optional<T> findOne(ID id) {
        String sql = "SELECT * FROM ".concat(this.tableName).concat(" WHERE ").concat(this.idColumnName).concat(" = ?");
        T object = this.jdbcTemplate.queryForObject(sql, new Object[]{id}, this.mapRow());
        return Optional.ofNullable(object);
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM ".concat(this.tableName);
        List<T> result = this.jdbcTemplate.query(sql, this.mapRow());
        return result;
    }

    @Override
    public Optional<T> save(T entity) {
        if (entity.getId() != null){
            UpdateQuery updateQuery = this.buildUpdateQuery(entity);
            this.jdbcTemplate.update(updateQuery.getQuery(), entity.getId(), updateQuery.getValues());
            Optional.ofNullable(entity);
        } else {
            ID id = (ID) insertEntity.executeAndReturnKey(this.mapColumns(entity));
            entity.setId(id);
            return Optional.ofNullable(entity);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void delete(ID id) {
        String sql = "DELETE * FROM ".concat(this.tableName).concat(" WHERE ").concat(this.idColumnName).concat(" = ?");
        this.jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE * FROM ".concat(this.tableName);
        this.jdbcTemplate.update(sql);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM ".concat(this.tableName);
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // TODO: 18.02.2017 need optimisation
    private UpdateQuery buildUpdateQuery(T entity){

        StringBuilder sql = new StringBuilder("UPDATE ".concat(this.tableName).concat(" SET "));
        List<Object> values = new ArrayList();

        this.mapColumns(entity).keySet().forEach( key -> {
            sql.append(key).append("=? ");
            values.add(this.mapColumns(entity).get(key));
        });

        sql.append(" WHERE ").append(this.idColumnName).append(" = ?");

        UpdateQuery updateQuery = new UpdateQuery(sql.toString(), values.toArray());
        return updateQuery;
    }

    public abstract  Map<String, Object> mapColumns(T entity);
    public abstract RowMapper<T> mapRow();
}
