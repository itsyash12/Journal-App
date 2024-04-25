package com.SpringProject.journalApp.controller;

import com.SpringProject.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
    @GetMapping
    public List<JournalEntry> getAllJournal(){
        return new ArrayList<>(journalEntries.values());
    }
    @GetMapping("id/{journalId}")
    public JournalEntry getOneJournal(@PathVariable long journalId){
        return journalEntries.get(journalId);
    }
    @DeleteMapping("id/{journalId}")
    public JournalEntry deleteJournal(@PathVariable long journalId){
        return journalEntries.remove(journalId);
    }

    @PutMapping
    public JournalEntry updateJournal(@RequestBody JournalEntry journalBody){
        return journalEntries.put(journalBody.getId(),journalBody);
    }
    @PostMapping
    public boolean createJournalEntry(@RequestBody JournalEntry entry){
        journalEntries.put(entry.getId(),entry);
        return true;
    }






}
