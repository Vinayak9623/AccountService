package com.account.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity {
    private UUID createdBy;
    private LocalDateTime createdDate=LocalDateTime.now();
    private UUID updatedBy;
    private LocalDateTime updatedDate=LocalDateTime.now();

}
