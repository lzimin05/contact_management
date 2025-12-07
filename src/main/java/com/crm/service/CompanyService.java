package com.crm.service;

import com.crm.dto.CompanyDTO;
import com.crm.dto.ContactDTO;
import com.crm.entity.Company;
import com.crm.entity.Contact;
import com.crm.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setIndustry(companyDTO.getIndustry());
        company.setWebsite(companyDTO.getWebsite());
        company.setAddress(companyDTO.getAddress());

        Company saved = companyRepository.save(company);
        return convertToDTO(saved);
    }

    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        company.setName(companyDTO.getName());
        company.setIndustry(companyDTO.getIndustry());
        company.setWebsite(companyDTO.getWebsite());
        company.setAddress(companyDTO.getAddress());

        Company updated = companyRepository.save(company);
        return convertToDTO(updated);
    }

    public CompanyDTO getCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
        return convertToDTO(company);
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
        companyRepository.delete(company);
    }

    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setIndustry(company.getIndustry());
        dto.setWebsite(company.getWebsite());
        dto.setAddress(company.getAddress());

        if (company.getContacts() != null && !company.getContacts().isEmpty()) {
            dto.setContacts(company.getContacts().stream()
                    .map(this::convertContactToDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private ContactDTO convertContactToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        dto.setPosition(contact.getPosition());
        dto.setNotes(contact.getNotes());
        return dto;
    }
}
