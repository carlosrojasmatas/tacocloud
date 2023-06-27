package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;

import java.util.Optional;

public interface IngredientsRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
