package com.skyblue.pitzeria.persistence.repository;

import com.skyblue.pitzeria.persistence.entity.CustomerEntity;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query(value = "SELECT c FROM CustomerEntity c WHERE c.phoneNumber =:phone")
    CustomerEntity findByPhone(@PathParam("phone") String phone);


}
