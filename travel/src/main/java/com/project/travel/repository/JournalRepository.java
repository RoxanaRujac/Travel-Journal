package com.project.travel.repository;

import com.project.travel.model.Journal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JournalRepository {
    private final List<Journal> journals = new ArrayList<>();

    public Journal save(Journal journal) {
        journals.add(journal);
        return journal;
    }

    public void saveAll(List<Journal> newJournals) {
        journals.addAll(newJournals);
    }

    public List<Journal> findAll() {
        return new ArrayList<>(journals);
    }

    public Journal findById(Long id) {
        return journals.stream()
                .filter(journal -> journal.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Journal> findByUserId(Long userId) {
        return journals.stream()
                .filter(journal -> journal.getUserId().equals(userId))
                .toList();
    }

    public Journal updateJournal(Journal updatedJournal) {
        Journal oldJournal = findById(updatedJournal.getId());
        if (oldJournal != null) {
            oldJournal.setTitle(updatedJournal.getTitle());
            oldJournal.setCoverImageFile(updatedJournal.getCoverImageFile());
            oldJournal.setDescription(updatedJournal.getDescription());
            return oldJournal;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return journals.removeIf(journal -> journal.getId().equals(id));
    }
}
