package com.project.travel.repository;

import com.project.travel.model.Entry;
import com.project.travel.model.Journal;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EntryRepository {
    private final List<Entry> entries = new ArrayList<>();

    public Entry save(Entry entry) {
        entries.add(entry);
        return entry;
    }

    public void saveAll(List<Entry> newEntries) {
        entries.addAll(newEntries);
    }

    public List<Entry> findAll() {
        return new ArrayList<>(entries);
    }

    public Entry findById(Long id) {
        return entries.stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Entry> findByJournalId(Long journalId) {
        return entries.stream()
                .filter(entry -> entry.getJournalId().equals(journalId))
                .toList();
    }

    public Entry updateEntry(Entry updatedEntry) {
        Entry oldEntry = findById(updatedEntry.getId());
        if (oldEntry != null) {
            oldEntry.setTitle(updatedEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent());
            oldEntry.setLocationName(updatedEntry.getLocationName());
            oldEntry.setMediaAttachments(updatedEntry.getMediaAttachments());
            return oldEntry;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return entries.removeIf(entry -> entry.getId().equals(id));
    }
}
