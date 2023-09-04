package com.skyblue.pitzeria.service;

import com.skyblue.pitzeria.persistence.entity.PizzaEntity;
import com.skyblue.pitzeria.persistence.repository.PizzaPageSortRepository;
import com.skyblue.pitzeria.persistence.repository.PizzaRepository;
import com.skyblue.pitzeria.service.dto.UpdatePizzaPriceDto;
import com.skyblue.pitzeria.service.exception.EmailApiException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaPageSortRepository pizzaPageSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPageSortRepository pizzaPageSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPageSortRepository = pizzaPageSortRepository;
    }

    public Page<PizzaEntity> getAll(Integer page, Integer elements) {
        Pageable pageableRequest = PageRequest.of(page, elements);
        return this.pizzaPageSortRepository.findAll(pageableRequest);
    }

    public Page<PizzaEntity> getAvailable(Integer page, Integer elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageableRequest = PageRequest.of(page, elements, sort);
        return this.pizzaPageSortRepository.findByAvailableTrue(pageableRequest);
    }

    public PizzaEntity getByName(String name) {
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("The pizza not exist"));
    }

    public List<PizzaEntity> getWith(String ingredient) {
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithout(String ingredient) {
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getCheapest(Double price) {
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public PizzaEntity getId(Long id) {
        return this.pizzaRepository.findById(id).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza) {
        return this.pizzaRepository.save(pizza);
    }

    public void delete(Long id) {
        this.pizzaRepository.deleteById(id);
    }

    @Transactional()
    public void updatePrice(UpdatePizzaPriceDto pizzaPriceDto) {
        this.pizzaRepository.updatePrice(pizzaPriceDto);

    }
    private void sendEmail(){
        throw  new EmailApiException();
    }

    public boolean exists(Long id) {
        return this.pizzaRepository.existsById(id);
    }
}
