package com.netcracker.model.repository;


import com.netcracker.jdbc.repository.GenericJdbcRepository;
import com.netcracker.model.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository extends GenericJdbcRepository<Item, Long> {


    public ItemRepository() {
        super(Item.TABLE_NAME, Item.ID_COLUMN);
    }

    @Override
    public Map<String, Object> mapColumns(Item entity) {
        Map<String, Object> columns = new HashMap<>();
        columns.put("title", entity.getName());
        columns.put("age", entity.getAge());
        return columns;
    }

    @Override
    public RowMapper<Item> mapRow() {
        return new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int i) throws SQLException {
                Item item = new Item();
                item.setId(resultSet.getLong("id"));
                item.setName(resultSet.getString("title"));
                item.setAge(resultSet.getInt("age"));
                return item;
            }
        };
    }
}
