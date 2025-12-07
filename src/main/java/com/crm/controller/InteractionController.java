package com.crm.controller;

import com.crm.dto.InteractionDTO;
import com.crm.service.InteractionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping("/contact/{contactId}")
    public ResponseEntity<InteractionDTO> createInteraction(
            @PathVariable Long contactId,
            @Valid @RequestBody InteractionDTO interactionDTO) {
        InteractionDTO created = interactionService.createInteraction(contactId, interactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InteractionDTO> updateInteraction(
            @PathVariable Long id,
            @Valid @RequestBody InteractionDTO interactionDTO) {
        InteractionDTO updated = interactionService.updateInteraction(id, interactionDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteractionDTO> getInteraction(@PathVariable Long id) {
        InteractionDTO interaction = interactionService.getInteraction(id);
        return ResponseEntity.ok(interaction);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<InteractionDTO>> getInteractionsByContact(@PathVariable Long contactId) {
        List<InteractionDTO> interactions = interactionService.getInteractionsByContact(contactId);
        return ResponseEntity.ok(interactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteraction(@PathVariable Long id) {
        interactionService.deleteInteraction(id);
        return ResponseEntity.noContent().build();
    }
}
