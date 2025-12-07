package com.crm.service;

import com.crm.dto.ContactDTO;
import com.crm.dto.InteractionDTO;
import com.crm.entity.Contact;
import com.crm.entity.Company;
import com.crm.entity.Interaction;
import com.crm.repository.ContactRepository;
import com.crm.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    public ContactService(ContactRepository contactRepository, CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        // Нормализация: пустые строки -> null
        String email = (contactDTO.getEmail() != null && !contactDTO.getEmail().trim().isEmpty()) 
            ? contactDTO.getEmail().trim() : null;
        String phone = (contactDTO.getPhone() != null && !contactDTO.getPhone().trim().isEmpty()) 
            ? contactDTO.getPhone().trim() : null;

        // Проверка уникальности email
        if (email != null) {
            contactRepository.findByEmail(email).ifPresent(existing -> {
                throw new IllegalArgumentException("Email уже используется другим контактом");
            });
        }

        // Проверка уникальности phone
        if (phone != null) {
            contactRepository.findByPhone(phone).ifPresent(existing -> {
                throw new IllegalArgumentException("Телефон уже используется другим контактом");
            });
        }

        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setPosition(contactDTO.getPosition());
        contact.setNotes(contactDTO.getNotes());

        if (contactDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(contactDTO.getCompanyId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            contact.setCompany(company);
        }

        Contact saved = contactRepository.save(contact);
        return convertToDTO(saved);
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));

        // Нормализация: пустые строки -> null
        String email = (contactDTO.getEmail() != null && !contactDTO.getEmail().trim().isEmpty()) 
            ? contactDTO.getEmail().trim() : null;
        String phone = (contactDTO.getPhone() != null && !contactDTO.getPhone().trim().isEmpty()) 
            ? contactDTO.getPhone().trim() : null;

        // Проверка уникальности email при изменении
        if (email != null) {
            contactRepository.findByEmail(email).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new IllegalArgumentException("Email уже используется другим контактом");
                }
            });
        }

        // Проверка уникальности phone при изменении
        if (phone != null) {
            contactRepository.findByPhone(phone).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new IllegalArgumentException("Телефон уже используется другим контактом");
                }
            });
        }

        contact.setName(contactDTO.getName());
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setPosition(contactDTO.getPosition());
        contact.setNotes(contactDTO.getNotes());

        if (contactDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(contactDTO.getCompanyId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            contact.setCompany(company);
        } else {
            contact.setCompany(null);
        }

        Contact updated = contactRepository.save(contact);
        return convertToDTO(updated);
    }

    public ContactDTO getContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));
        return convertToDTO(contact);
    }

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ContactDTO> searchContacts(String search) {
        return contactRepository.searchContacts(search).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ContactDTO> getContactsByCompany(Long companyId) {
        companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
        return contactRepository.findByCompanyId(companyId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));
        contactRepository.delete(contact);
    }

    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        dto.setPosition(contact.getPosition());
        dto.setNotes(contact.getNotes());

        if (contact.getCompany() != null) {
            dto.setCompanyId(contact.getCompany().getId());
            dto.setCompanyName(contact.getCompany().getName());
        }

        if (contact.getInteractions() != null && !contact.getInteractions().isEmpty()) {
            dto.setInteractions(contact.getInteractions().stream()
                    .map(this::convertInteractionToDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private InteractionDTO convertInteractionToDTO(Interaction interaction) {
        return new InteractionDTO(
                interaction.getId(),
                interaction.getContact().getId(),
                interaction.getNotes(),
                interaction.getDate(),
                interaction.getCreatedAt()
        );
    }
}
