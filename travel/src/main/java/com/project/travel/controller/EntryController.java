package com.project.travel.controller;

import com.project.travel.model.Entry;
import com.project.travel.model.Journal;
import com.project.travel.model.Media;
import com.project.travel.service.EntryService;
import com.project.travel.service.JournalService;
import com.project.travel.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/entry")
public class EntryController {
    private final EntryService entryService;
    private final JournalService journalService;
    private final MediaService mediaService;

    public EntryController(EntryService entryService, JournalService journalService, MediaService mediaService) {
        this.entryService = entryService;
        this.journalService = journalService;
        this.mediaService = mediaService;
    }

    @Operation(summary = "Get an entry by ID", description = "Retrieves an entry along with its media attachments")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entry found"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> viewEntry(@PathVariable Long id) {
        try {
            Entry entry = entryService.getEntryById(id);
            entry.setMediaAttachments(mediaService.getMediaByEntryId(id));
            return ResponseEntity.ok(entry);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }

    @Operation(summary = "Create a new entry", description = "Creates and returns the newly created entry")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entry created successfully")
    })
    @PostMapping
    public ResponseEntity<?> saveEntry(@org.springframework.web.bind.annotation.RequestBody Entry entry) {
        Long entryId = entryService.addEntry(entry);
        entry.setId(entryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }


    @Operation(summary = "Update an existing entry", description = "Updates and returns the updated entry")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entry updated successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    @PutMapping
    public ResponseEntity<?> updateEntry(@org.springframework.web.bind.annotation.RequestBody Entry entry) {
        entryService.updateEntry(entry);
        return ResponseEntity.ok(entry);
    }


    @Operation(summary = "Delete an entry", description = "Deletes an entry by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Entry deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id) {
        try {
            entryService.deleteEntry(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }

    @Operation(summary = "Upload media for an entry", description = "Uploads media files and attaches them to an entry")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Media uploaded successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    @PostMapping("/{entryId}/media")
    public ResponseEntity<?> uploadMedia(@PathVariable Long entryId, @RequestParam("mediaFiles") MultipartFile[] mediaFiles, @RequestParam(value = "mediaCaptions", required = false) String[] mediaCaptions) {
        try {
            Entry entry = entryService.getEntryById(entryId);
            for (int i = 0; i < mediaFiles.length; i++) {
                MultipartFile file = mediaFiles[i];
                if (!file.isEmpty()) {
                    Media media = new Media();
                    media.setEntryId(entryId);
                    media.setCaption(mediaCaptions != null && i < mediaCaptions.length ? mediaCaptions[i] : "");
                    media.setUrl(mediaService.saveMediaFile(file));
                    media.setType(mediaService.determineMediaType(file.getContentType()));
                    mediaService.addMedia(media);
                }
            }
            return ResponseEntity.ok("Media uploaded successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }

    @Operation(summary = "Delete a media item", description = "Deletes a media item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Media deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Media not found")
    })
    @DeleteMapping("/media/{id}")
    public ResponseEntity<?> deleteMedia(@PathVariable Long id) {
        try {
            Media media = mediaService.getMediaById(id);
            mediaService.deleteMediaFile(media.getUrl());
            mediaService.deleteMedia(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media not found");
        }
    }
}
