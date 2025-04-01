package com.project.travel.service;

import com.project.travel.model.Entry;
import java.util.List;

public interface EntryService {
    List<Entry> getAllEntries();
    Entry getEntryById(Long id);
    List<Entry> getEntriesByJournalId(Long journalId);
    Long addEntry(Entry entry);
    void updateEntry(Entry entry);
    void deleteEntry(Long id);
}