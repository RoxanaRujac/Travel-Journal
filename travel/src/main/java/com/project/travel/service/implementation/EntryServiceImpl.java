package com.project.travel.service.implementation;

import com.project.travel.model.Entry;
import com.project.travel.repository.EntryRepository;
import com.project.travel.service.EntryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EntryServiceImpl implements EntryService {
    private final EntryRepository entryRepository;
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EntryServiceImpl(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public Entry getEntryById(Long id) {
        Entry entry = entryRepository.findById(id);
        if (entry == null) {
            throw new NoSuchElementException("Entry not found with ID: " + id);
        }
        return entry;
    }

    @Override
    public List<Entry> getEntriesByJournalId(Long journalId) {
        return entryRepository.findByJournalId(journalId);
    }

    @Override
    public Long addEntry(Entry entry) {
        // Set new ID and timestamp
        if (entry.getId() == null) {
            entry.setId(idGenerator.getAndIncrement());
        }

        // Set creation timestamp if not present
        if (entry.getCreatedAt() == null) {
            entry.setCreatedAt(LocalDateTime.now().format(formatter));
        }

        entryRepository.save(entry);
        return entry.getId();
    }

    @Override
    public void updateEntry(Entry entry) {
        // Check if entry exists
        Entry existingEntry = getEntryById(entry.getId());

        // Update the entry
        Entry updatedEntry = entryRepository.updateEntry(entry);
        if (updatedEntry == null) {
            throw new NoSuchElementException("Failed to update entry with ID: " + entry.getId());
        }
    }

    @Override
    public void deleteEntry(Long id) {
        // Check if entry exists
        getEntryById(id);

        // Delete the entry
        boolean deleted = entryRepository.deleteById(id);
        if (!deleted) {
            throw new NoSuchElementException("Failed to delete entry with ID: " + id);
        }
    }
}