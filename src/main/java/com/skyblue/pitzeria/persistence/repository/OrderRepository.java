package com.skyblue.pitzeria.persistence.repository;

import com.skyblue.pitzeria.persistence.entity.OrderEntity;
import com.skyblue.pitzeria.persistence.projection.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);

    List<OrderEntity> findAllByMethodIn(List<String> methods);

    @Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id", nativeQuery = true)
    List<OrderEntity> findCustomerOrders(@Param("id") Long idCustomer);

    @Query(value =
            "SELECT po.id_order AS idOrder, cu.name AS customerName, po.date AS orderDate, " +
                    "po.total AS orderTotal, STRING_AGG(pi.name, ', ') AS pizzaNames " +
                    "FROM pizza_order po " +
                    "INNER JOIN customer cu ON po.id_customer = cu.id_customer " +
                    "INNER JOIN order_item oi ON po.id_order = oi.id_order " +
                    "INNER JOIN pizza pi ON oi.id_pizza = pi.id_pizza " +
                    "WHERE po.id_order = :orderId " +
                    "GROUP BY po.id_order, cu.name, po.date, po.total", nativeQuery = true)
    OrderSummary findSummary(@Param("orderId") Long orderId);

    @Procedure(value = "take_random_pizza_order", outputParameterName = "result_message")
    Boolean saveRandomOrder(@Param("id_customer") Long idCustomer, @Param("method") String method);

}

