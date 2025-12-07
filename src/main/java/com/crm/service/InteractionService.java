package com.crm.service;

import com.crm.dto.InteractionDTO;
import com.crm.entity.Contact;
import com.crm.entity.Interaction;
import com.crm.repository.ContactRepository;
import com.crm.repository.InteractionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final ContactRepository contactRepository;

    public InteractionService(InteractionRepository interactionRepository, ContactRepository contactRepository) {
        this.interactionRepository = interactionRepository;
        this.contactRepository = contactRepository;
    }

    public InteractionDTO createInteraction(Long contactId, InteractionDTO interactionDTO) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));

        Interaction interaction = new Interaction();
        interaction.setContact(contact);
        interaction.setNotes(interactionDTO.getNotes());
        interaction.setDate(interactionDTO.getDate());

        Interaction saved = interactionRepository.save(interaction);
        return convertToDTO(saved);
    }

    public InteractionDTO updateInteraction(Long id, InteractionDTO interactionDTO) {
        Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interaction not found"));

        interaction.setNotes(interactionDTO.getNotes());
        interaction.setDate(interactionDTO.getDate());

        Interaction updated = interactionRepository.save(interaction);
        return convertToDTO(updated);
    }

    public InteractionDTO getInteraction(Long id) {
        Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interaction not found"));
        return convertToDTO(interaction);
    }

    public List<InteractionDTO> getInteractionsByContact(Long contactId) {
        contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));

        return interactionRepository.findByContactIdOrderByDateDesc(contactId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteInteraction(Long id) {
        Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interaction not found"));
        interactionRepository.delete(interaction);
    }

    private InteractionDTO convertToDTO(Interaction interaction) {
        return new InteractionDTO(
                interaction.getId(),
                interaction.getContact().getId(),
                interaction.getNotes(),
                interaction.getDate(),
                interaction.getCreatedAt()
        );
    }
}
