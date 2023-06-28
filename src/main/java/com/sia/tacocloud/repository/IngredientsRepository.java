package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientsRepository extends CrudRepository<Ingredient,String> {

}
