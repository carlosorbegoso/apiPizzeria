package com.skyblue.pitzeria.persistence.repository;

import com.skyblue.pitzeria.persistence.entity.PizzaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaPageSortRepository extends ListPagingAndSortingRepository<PizzaEntity, Long> {
    Page<PizzaEntity> findByAvailableTrue(Pageable pageable);
}
