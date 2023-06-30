package com.sia.tacocloud.converter;

import com.sia.tacocloud.model.Ingredient;
import com.sia.tacocloud.model.IngredientUDT;
import com.sia.tacocloud.repository.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
public class StringToIngredientConverter implements Converter<String, IngredientUDT> {

    private IngredientsRepository repository;

    @Autowired
    public StringToIngredientConverter(IngredientsRepository repository) {
        this.repository = repository;
    }

    @Override
    public IngredientUDT convert(@NonNull String id) {
        return repository.findById(id)
                .map(i -> new IngredientUDT(i.getName(), i.getType()))
                .orElse(null);

    }
}
