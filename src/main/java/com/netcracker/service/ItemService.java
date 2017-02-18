package com.netcracker.service;

import com.netcracker.model.Item;
import com.netcracker.model.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    @Transactional
    public List<Item> getAll(){
        return repository.findAll();
    }

    @Transactional
    public Item getOne(Long id){
        return repository.findOne(id).get();
    }

    @Transactional
    public void deleteOne(Long id){
        repository.delete(id);
    }

    @Transactional
    public Item addOne(Item item){
        return repository.save(item).get();
    }

    @Transactional
    public Item updateOne(Long id, Item item){
        Item u = repository.findOne(id).orElse(null);
        if (u == null){
            return null;
        }
        u.setName(item.getName());
        u.setAge(item.getAge());
        return repository.save(u).get();
    }

}
