package com.crm.service;

import com.crm.dto.ContactDTO;
import com.crm.entity.Company;
import com.crm.entity.Contact;
import com.crm.repository.CompanyRepository;
import com.crm.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("ContactService Tests")
class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new Company();
        testCompany.setName("Test Company");
        testCompany.setIndustry("Technology");
        testCompany = companyRepository.save(testCompany);
    }

    @Test
    @DisplayName("Should create contact successfully")
    void testCreateContact() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setEmail("john@example.com");
        contactDTO.setPhone("+1234567890");
        contactDTO.setPosition("Manager");
        contactDTO.setCompanyId(testCompany.getId());

        ContactDTO created = contactService.createContact(contactDTO);

        assertNotNull(created.getId());
        assertEquals("John Doe", created.getName());
        assertEquals("john@example.com", created.getEmail());
    }

    @Test
    @DisplayName("Should update contact successfully")
    void testUpdateContact() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setEmail("john@example.com");
        contactDTO.setPhone("+1234567890");
        contactDTO.setPosition("Manager");

        ContactDTO created = contactService.createContact(contactDTO);

        ContactDTO updateDTO = new ContactDTO();
        updateDTO.setName("Jane Doe");
        updateDTO.setEmail("jane@example.com");
        updateDTO.setPhone("+0987654321");
        updateDTO.setPosition("Senior Manager");

        ContactDTO updated = contactService.updateContact(created.getId(), updateDTO);

        assertEquals("Jane Doe", updated.getName());
        assertEquals("jane@example.com", updated.getEmail());
    }

    @Test
    @DisplayName("Should get contact by id")
    void testGetContact() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setEmail("john@example.com");
        contactDTO.setPhone("+1234567890");

        ContactDTO created = contactService.createContact(contactDTO);
        ContactDTO retrieved = contactService.getContact(created.getId());

        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("John Doe", retrieved.getName());
    }

    @Test
    @DisplayName("Should search contacts by name")
    void testSearchContacts() {
        ContactDTO contactDTO1 = new ContactDTO();
        contactDTO1.setName("UniqueTestName123");
        contactDTO1.setEmail("unique123@test.com");
        contactDTO1.setPhone("+9999999999");

        ContactDTO contactDTO2 = new ContactDTO();
        contactDTO2.setName("Jane Smith");
        contactDTO2.setEmail("jane@example.com");
        contactDTO2.setPhone("+0987654321");

        contactService.createContact(contactDTO1);
        contactService.createContact(contactDTO2);

        List<ContactDTO> results = contactService.searchContacts("UniqueTestName123");

        assertEquals(1, results.size());
        assertEquals("UniqueTestName123", results.get(0).getName());
    }

    @Test
    @DisplayName("Should delete contact")
    void testDeleteContact() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setEmail("john@example.com");
        contactDTO.setPhone("+1234567890");

        ContactDTO created = contactService.createContact(contactDTO);
        contactService.deleteContact(created.getId());

        assertThrows(EntityNotFoundException.class, () -> contactService.getContact(created.getId()));
    }

    @Test
    @DisplayName("Should throw exception when contact not found")
    void testGetContactNotFound() {
        assertThrows(EntityNotFoundException.class, () -> contactService.getContact(999L));
    }
}
