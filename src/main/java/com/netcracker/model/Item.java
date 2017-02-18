package com.netcracker.model;

import com.netcracker.jdbc.repository.Persistable;

public class Item implements Persistable<Long> {

    public static final String TABLE_NAME = "items";
    public static final String ID_COLUMN = "id";

    private Long id;
    private String name;
    private int age;

    public Item() {
    }

    public Item(String name, int age) {
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
