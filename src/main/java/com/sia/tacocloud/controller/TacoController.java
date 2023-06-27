package com.sia.tacocloud.controller;

import com.sia.tacocloud.model.Ingredient;
import com.sia.tacocloud.model.Order;
import com.sia.tacocloud.model.Taco;
import com.sia.tacocloud.repository.IngredientsRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@Slf4j
@RequestMapping("taco")
@SessionAttributes("order")
public class TacoController {

    private IngredientsRepository ingredientsRepository;

    @Autowired
    public TacoController(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
//        );

        Iterable<Ingredient> ingredients = ingredientsRepository.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.name().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
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
                              @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "/designForm";
        }
        order.addTaco(taco);
        log.info("Processing taco:{} ", taco);
        return "redirect:/order/current";

    }
}
