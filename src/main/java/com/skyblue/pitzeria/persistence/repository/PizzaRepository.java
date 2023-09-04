package com.skyblue.pitzeria.persistence.repository;

import com.skyblue.pitzeria.persistence.entity.PizzaEntity;
import com.skyblue.pitzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);

    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(Double price);

    Integer countByVeganTrue();

    @Query(value = "UPDATE pizza " +
            "SET price = :#{#newPizzaPrice.newPrice} " +
            "WHERE  id_pizza = :#{#newPizzaPrice.pizzaId} ", nativeQuery = true)
    @Modifying
    void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);
}
