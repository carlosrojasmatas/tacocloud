package com.sia.tacocloud.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Table("taco")
@NoArgsConstructor
public final class Taco {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID id;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be 5 characters long")
    private String tacoName;

    @NotNull
    @Size(min = 2, message = "Choose at least 2 ingredients")
    private List<IngredientUDT> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(new IngredientUDT(ingredient.getName(), ingredient.getType()));
    }
}
