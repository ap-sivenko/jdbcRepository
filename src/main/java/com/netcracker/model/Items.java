package com.netcracker.model;

import com.netcracker.repository.util.Persistable;

public class Items implements Persistable<Long> {

    private Long id;
    private String name;
    private int age;

    public Items() {
    }

    public Items(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
