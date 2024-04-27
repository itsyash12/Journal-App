package com.SpringProject.journalApp.Service;

import com.SpringProject.journalApp.Entity.JournalEntry;
import com.SpringProject.journalApp.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalRepository journalEntryRepository;

    public boolean saveEntry(JournalEntry entry){
        journalEntryRepository.save(entry);
        return true;
    }


    public List<JournalEntry> getEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getOneEntry(ObjectId journalId) {
        return journalEntryRepository.findById(journalId);
    }

    public void deleteEntry(ObjectId journalId) {
        journalEntryRepository.deleteById(journalId);
        return;
    }
}
