package com.sia.tacocloud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public final class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5,message = "Name must be 5 characters long")
    private  String tacoName;

    @NotNull
    @Size(min = 2,message = "Choose at least 2 ingredients")
    @ManyToMany
    private  List<Ingredient> ingredients;

}
