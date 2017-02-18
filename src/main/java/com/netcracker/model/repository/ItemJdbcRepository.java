package com.netcracker.model.repository;


import com.netcracker.model.Items;
import com.netcracker.repository.GenericJdbcRepository;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ItemJdbcRepository extends GenericJdbcRepository<Items, Long> {


    public ItemJdbcRepository(DataSource dataSource) {
        super(dataSource, "items", "id");
    }

    @Override
    public Map<String, Object> mapColumns(Items entity) {
        Map<String, Object> columns = new HashMap<>();
        columns.put("title", entity.getName());
        columns.put("age", entity.getAge());
        return columns;
    }

    @Override
    public RowMapper<Items> mapRow() {
        return new RowMapper<Items>() {
            @Override
            public Items mapRow(ResultSet resultSet, int i) throws SQLException {
                Items items = new Items();
                items.setId(resultSet.getLong("id"));
                items.setName(resultSet.getString("title"));
                items.setAge(resultSet.getInt("age"));
                return items;
            }
        };
    }
}
