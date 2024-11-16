package com.mobile.buddybound.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.DiffExclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    @DiffExclude
    protected LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    @DiffExclude
    protected LocalDateTime updatedAt = LocalDateTime.now();
}
