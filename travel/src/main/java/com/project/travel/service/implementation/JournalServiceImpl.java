package com.project.travel.service.implementation;

import com.project.travel.model.Journal;
import com.project.travel.repository.JournalRepository;
import com.project.travel.service.JournalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;
    private final Random random = new Random();

    public JournalServiceImpl(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }


    @Override
    public Journal addJournal(Journal journal) {
        if (journal.getId() == null) {
            journal.setId(random.nextLong(Long.MAX_VALUE));
        }
        return journalRepository.save(journal);
    }

    @Override
    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }


    @Override
    public Journal getJournalById(Long id) {
        Journal journal = journalRepository.findById(id);
        if (journal != null) {
            return journal;
        }
        throw new NoSuchElementException("Journal with id" + id + " does not exist");
    }

    @Override
    public Journal updateJournal(Journal journal) {
        return journalRepository.updateJournal(journal);
    }

    @Override
    public void deleteJournal(Long id) {
        journalRepository.deleteById(id);
    }
}
