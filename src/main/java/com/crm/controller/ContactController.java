package com.crm.controller;

import com.crm.dto.ContactDTO;
import com.crm.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactDTO contactDTO) {
        ContactDTO created = contactService.createContact(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDTO) {
        ContactDTO updated = contactService.updateContact(id, contactDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContact(@PathVariable Long id) {
        ContactDTO contact = contactService.getContact(id);
        return ResponseEntity.ok(contact);
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        List<ContactDTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactDTO>> searchContacts(@RequestParam String query) {
        List<ContactDTO> contacts = contactService.searchContacts(query);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ContactDTO>> getContactsByCompany(@PathVariable Long companyId) {
        List<ContactDTO> contacts = contactService.getContactsByCompany(companyId);
        return ResponseEntity.ok(contacts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
