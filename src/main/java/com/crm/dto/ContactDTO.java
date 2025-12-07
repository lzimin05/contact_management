package com.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String position;

    private Long companyId;

    private String companyName;

    private String notes;

    private List<InteractionDTO> interactions;
}
