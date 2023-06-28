package com.sia.tacocloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

@Data
public class Ingredient implements Persistable<String> {
    @Id
    private  String id;
    private  String name;
    private  Type type;

    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
