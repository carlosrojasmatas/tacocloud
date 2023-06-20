package com.sia.tacocloud.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Ingredient {
    private  String id;
    private  String name;
    private  Type type;

    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
