package com.project.travel.service.implementation;

import com.project.travel.model.Journal;
import com.project.travel.repository.JournalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class JournalServiceImplTest {
    private static final String TITLE = "TestJournal";

    private Journal journal;

    // UUT (Unit Under Test)
    private JournalServiceImpl journalService;

    @Mock
    private JournalRepository journalRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        journalService = new JournalServiceImpl(journalRepository);

        // Initialize your journal object
        journal = new Journal(1L, 1L, TITLE, null, "Sample description", "2025-03-17", null);
    }

    @Test
    public void givenExistingJournal_whenFindJournalById_thenReturnJournal() {
        // given
        when(journalRepository.findById(1L)).thenReturn(journal);

        // when
        Journal resultJournal = journalService.getJournalById(1L);

        // then
        assertNotNull(resultJournal);
        assertEquals(TITLE, resultJournal.getTitle());
        Mockito.verify(journalRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(journalRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void givenNonExistingJournal_whenFindJournalById_thenThrowException() {
        // given
        when(journalRepository.findById(1L)).thenReturn(null);

        // when & then
        assertThrows(NoSuchElementException.class, () -> {
            journalService.getJournalById(1L);
        });
    }

    @Test
    void givenValidJournal_whenUpdateJournal_thenReturnUpdatedJournal() {
        // given
        when(journalRepository.findById(1L)).thenReturn(journal);
        Journal updatedJournal = new Journal(1L, 1L, "Updated Title", null, "Updated description", "2025-03-17", null);
        when(journalRepository.updateJournal(any(Journal.class))).thenReturn(updatedJournal);

        // when
        Journal result = journalService.updateJournal(updatedJournal);

        // then
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated description", result.getDescription());
    }

    @Test
    void givenValidJournal_whenDeleteJournal_thenNoException() {
        // given
        doNothing().when(journalRepository).deleteById(1L);

        // when
        journalService.deleteJournal(1L);

        // then
        Mockito.verify(journalRepository, Mockito.times(1)).deleteById(1L);
    }
  
}