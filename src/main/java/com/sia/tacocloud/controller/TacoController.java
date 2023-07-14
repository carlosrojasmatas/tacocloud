package com.sia.tacocloud.controller;

import com.sia.tacocloud.model.*;
import com.sia.tacocloud.repository.IngredientsRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@Slf4j
@RequestMapping("taco")
@SessionAttributes("tacoOrder")
public class TacoController {

    private final IngredientsRepository ingredientsRepository;

    @Autowired
    public TacoController(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }



    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientsRepository.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.name().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping("/design")
    public String designForm() {
        return "designForm.html";
    }

    private List<Ingredient> filterByType(Iterable<Ingredient> ingredients, Ingredient.Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }


    @PostMapping
    public String processTaco(@Valid Taco taco,
                              Errors errors,
                              @ModelAttribute TacoOrder order) {

        if (errors.hasErrors()) {
            return "/designForm";
        }


        order.addTaco(new TacoUDT(taco.getTacoName(),
                taco.getIngredients()
                        .stream()
                        .map(i -> new IngredientUDT(i.getName(), i.getType()))
                        .collect(Collectors.toList())));
        log.info("Processing taco:{} ", taco);
        return "redirect:/order/current";

    }
}
