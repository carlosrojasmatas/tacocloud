package com.sia.tacocloud.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;
@Data
@UserDefinedType("ingredient")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED,force = true)
public class IngredientUDT {

    private final String name;
    private final Ingredient.Type type;
}
