package com.project.travel.controller;

import com.project.travel.model.Journal;
import com.project.travel.model.Entry;
import com.project.travel.service.JournalService;
import com.project.travel.service.EntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/journal")
public class JournalController {
    private final JournalService journalService;
    private final EntryService entryService;

    public JournalController(JournalService journalService, EntryService entryService) {
        this.journalService = journalService;
        this.entryService = entryService;
    }

    @Operation(summary = "Get all journals", description = "Retrieves a list of all journals")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Journals retrieved successfully") })
    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals() {
        return ResponseEntity.ok(journalService.getAllJournals());
    }

    @Operation(summary = "Create a new journal", description = "Creates and returns the newly created journal")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Journal created successfully") })
    @PostMapping
    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
        Journal savedJournal = journalService.addJournal(journal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJournal);
    }

    @Operation(summary = "Get a journal by ID", description = "Retrieves a journal along with its entries")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Journal found"),
            @ApiResponse(responseCode = "404", description = "Journal not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable Long id) {
        try {
            Journal journal = journalService.getJournalById(id);
            List<Entry> entries = entryService.getEntriesByJournalId(id);
            journal.setEntries(entries);
            return ResponseEntity.ok(journal);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal not found");
        }
    }

    @Operation(summary = "Update a journal", description = "Updates and returns the updated journal")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Journal updated successfully"),
            @ApiResponse(responseCode = "404", description = "Journal not found")
    })
    @PutMapping
    public ResponseEntity<?> updateJournal(@RequestBody Journal journal) {
        journalService.updateJournal(journal);
        return ResponseEntity.ok(journal);
    }

    @Operation(summary = "Delete a journal", description = "Deletes a journal along with its entries")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Journal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Journal not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable Long id) {
        try {
            List<Entry> entries = entryService.getEntriesByJournalId(id);
            for (Entry entry : entries) {
                entryService.deleteEntry(entry.getId());
            }
            journalService.deleteJournal(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal not found");
        }
    }
}