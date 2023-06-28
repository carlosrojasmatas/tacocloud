package com.sia.tacocloud.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
public final class Taco {

    @Id
    private Long id;
    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5,message = "Name must be 5 characters long")
    private  String tacoName;

    @NotNull
    @Size(min = 2,message = "Choose at least 2 ingredients")
    private  List<Ingredient> ingredients;

}
