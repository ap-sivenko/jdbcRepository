package com.netcracker.web;

import com.netcracker.model.Item;
import com.netcracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping("/")
    public List<Item> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Item getOne(@PathVariable Long id){
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id){
        service.deleteOne(id);
    }

    @PostMapping("/")
    public Item addOne(@RequestBody Item item){
        return  service.addOne(item);
    }

    @PutMapping("/{id}")
    public Item updateOne(@PathVariable Long id, @RequestBody Item item){
        return service.updateOne(id, item);
    }
}
