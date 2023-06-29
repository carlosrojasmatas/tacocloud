package com.sia.tacocloud.controller;

import com.sia.tacocloud.model.TacoOrder;
import com.sia.tacocloud.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes("tacoOrder")
@Slf4j
public class OrderController {

    private OrderRepository repository;

    @Autowired
    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: {}", order);
        repository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping("/zip/{zip}")
    public ResponseEntity<List<TacoOrder>> orderByZip(@PathVariable String zip){
        var order = repository.findByZip(zip);

        return ResponseEntity.ok(order);
    }

}
