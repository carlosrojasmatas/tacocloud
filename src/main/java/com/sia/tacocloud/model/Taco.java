package com.sia.tacocloud.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public final class Taco {

    @NotNull
    @Size(min = 5,message = "Name must be 5 characters long")
    private  String tacoName;

    @NotNull
    @Size(min = 2,message = "Choose at least 2 ingredients")
    private  List<Ingredient> ingredients;

}