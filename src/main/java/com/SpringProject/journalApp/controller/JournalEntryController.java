package com.SpringProject.journalApp.controller;

import com.SpringProject.journalApp.Entity.JournalEntry;
import com.SpringProject.journalApp.Service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAllJournal(){
        List<JournalEntry> all_Entries = journalEntryService.getEntries();
        return new ResponseEntity<>(all_Entries,HttpStatus.OK);
    }

    @GetMapping("id/{journalId}")
    public ResponseEntity<JournalEntry> getOneJournal(@PathVariable ObjectId journalId){
        Optional<JournalEntry> entry = journalEntryService.getOneEntry(journalId);

        if(entry.isPresent()){
                return new ResponseEntity<JournalEntry>(entry.get(), HttpStatus.OK);
        }
        else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{journalId}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId journalId){
        try{
            Optional<JournalEntry> entry = journalEntryService.getOneEntry(journalId);

            if(entry.isPresent()) {
                journalEntryService.deleteEntry(journalId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateJournal(@RequestBody JournalEntry newEntry){
        JournalEntry prevEntry = journalEntryService.getOneEntry(newEntry.getId()).orElse(null);

        if(prevEntry!=null){
            prevEntry.setTitle(prevEntry.getTitle().equals(newEntry.getTitle()) ? prevEntry.getTitle() : newEntry.getTitle());
            prevEntry.setContent(prevEntry.getContent().equals(newEntry.getContent()) ? prevEntry.getContent() : newEntry.getContent());
            journalEntryService.saveEntry(prevEntry);
            return new ResponseEntity<>(prevEntry,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry entry){
        try{
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry);
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
