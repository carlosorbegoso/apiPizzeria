package com.skyblue.pitzeria.persistence.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemId implements Serializable {
    private Long idOrder;
    private Long idItem;
}