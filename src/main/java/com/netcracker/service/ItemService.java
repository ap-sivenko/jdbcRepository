package com.netcracker.service;

import com.netcracker.model.Items;
import com.netcracker.model.repository.ItemJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemJdbcRepository repository;

    @Transactional
    public List<Items> getAll(){
        return repository.findAll();
    }

    @Transactional
    public Items getOne(Long id){
        return repository.findOne(id).get();
    }

}
