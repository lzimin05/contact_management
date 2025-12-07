package com.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractionDTO {

    private Long id;

    private Long contactId;

    private String notes;

    private LocalDateTime date;

    private LocalDateTime createdAt;
}
