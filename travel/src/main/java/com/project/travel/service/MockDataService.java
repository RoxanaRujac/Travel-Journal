package com.project.travel.service;
import com.project.travel.model.Journal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MockDataService {
    void generateMockData();
    public List<Journal> getJournals();
}
