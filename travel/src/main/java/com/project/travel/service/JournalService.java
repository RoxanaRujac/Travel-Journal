package com.project.travel.service;
import com.project.travel.model.Journal;

import java.util.List;
import java.util.Optional;

public interface JournalService {
    Journal addJournal(Journal journal);
    List<Journal> getAllJournals();
    Journal getJournalById(Long id);
    Journal updateJournal(Journal journal);
    void deleteJournal(Long id);
}
