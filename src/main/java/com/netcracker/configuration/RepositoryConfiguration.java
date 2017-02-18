package com.netcracker.configuration;


import com.netcracker.model.repository.ItemJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemJdbcRepository createItemJdbcRepository(){
        ItemJdbcRepository itemJdbcRepository = new ItemJdbcRepository(dataSource);
        return itemJdbcRepository;
    }
}
