package com.skyblue.pitzeria.web.controller;

import com.skyblue.pitzeria.persistence.entity.PizzaEntity;
import com.skyblue.pitzeria.service.PizzaService;
import com.skyblue.pitzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer elements) {
        return ResponseEntity.ok(this.pizzaService.getAll(page, elements));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getAvailable(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer elements,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "ASC") String sorDirection) {
        return ResponseEntity.ok(this.pizzaService.getAvailable(page, elements, sortBy, sorDirection));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }

    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient) {
        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }

    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithout(@PathVariable String ingredient) {
        return ResponseEntity.ok(this.pizzaService.getWithout(ingredient));
    }

    @GetMapping("/without/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapestPizzas(@PathVariable Double price) {
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaEntity> getPizzaId(@PathVariable Long id) {
        return ResponseEntity.ok(this.pizzaService.getId(id));
    }

    @PostMapping
    public ResponseEntity<PizzaEntity> pizzaAdd(@RequestBody PizzaEntity pizza) {
        if (pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> pizzaUpdate(@RequestBody PizzaEntity pizza) {
        if (pizza.getIdPizza() != null || this.pizzaService.exists(pizza.getIdPizza())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> pizzaDelete(@PathVariable Long id) {
        if (this.pizzaService.exists(id)) {
            this.pizzaService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/price")
    public ResponseEntity<Void> pizzaUpdate(@RequestBody UpdatePizzaPriceDto pizzaPriceDto) {
        if (this.pizzaService.exists(pizzaPriceDto.pizzaId())) {
            this.pizzaService.updatePrice(pizzaPriceDto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
