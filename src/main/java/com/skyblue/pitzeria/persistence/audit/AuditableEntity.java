package com.skyblue.pitzeria.persistence.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public class AuditableEntity {
    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;
    @Column(name = "modified_data")
    @LastModifiedDate
    private LocalDateTime modifiedData;
}
