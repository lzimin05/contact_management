package com.crm.controller;

import com.crm.dto.ContactDTO;
import com.crm.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("ContactController Tests")
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create contact via API")
    void testCreateContact() throws Exception {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setEmail("john@example.com");
        contactDTO.setPhone("+1234567890");
        contactDTO.setPosition("Manager");

        mockMvc.perform(post("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @DisplayName("Should get all contacts")
    void testGetAllContacts() throws Exception {
        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should fail validation with invalid email")
    void testCreateContactWithInvalidEmail() throws Exception {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setEmail("invalid-email");
        contactDTO.setPhone("+1234567890");

        mockMvc.perform(post("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isBadRequest());
    }
}
