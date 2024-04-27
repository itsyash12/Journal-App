package com.SpringProject.journalApp.controller;

import com.SpringProject.journalApp.Entity.JournalEntry;
import com.SpringProject.journalApp.Service.JournalEntryService;
//import org.bson.types.ObjectId;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
//import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAllJournal(){
        return journalEntryService.getEntries();
    }

    @GetMapping("id/{journalId}")
    public JournalEntry getOneJournal(@PathVariable ObjectId journalId){
        return journalEntryService.getOneEntry(journalId).orElse(null);
    }

    @DeleteMapping("id/{journalId}")
    public boolean deleteJournal(@PathVariable ObjectId journalId){
        journalEntryService.deleteEntry(journalId);
        return true;
    }

    @PutMapping
    public JournalEntry updateJournal(@RequestBody JournalEntry newEntry){
        JournalEntry prevEntry = journalEntryService.getOneEntry(newEntry.getId()).orElse(null);

        if(prevEntry!=null)
        {
            prevEntry.setTitle(prevEntry.getTitle().equals(newEntry.getTitle()) ? prevEntry.getTitle() : newEntry.getTitle());
            prevEntry.setContent(prevEntry.getContent().equals(newEntry.getContent()) ? prevEntry.getContent() : newEntry.getContent());
            journalEntryService.saveEntry(prevEntry);
        }
        return prevEntry;
    }
    @PostMapping
    public boolean createJournalEntry(@RequestBody JournalEntry entry){
        entry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(entry);
        return true;
    }






}
