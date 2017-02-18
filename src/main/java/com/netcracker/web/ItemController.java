package com.netcracker.web;

import com.netcracker.model.Items;
import com.netcracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService service;

    @GetMapping("/")
    public List<Items> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Items getOne(@PathVariable Long id){
        return service.getOne(id);
    }
}
