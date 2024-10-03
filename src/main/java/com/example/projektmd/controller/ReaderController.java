package com.example.projektmd.controller;

import com.example.projektmd.model.Reader;
import com.example.projektmd.repository.ReaderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    private final ReaderRepository readerRepository;

    public ReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }
    //Operacja POst dodaje nowego czytelnika do bazy
    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader) {
        Reader savedReader = readerRepository.save(reader);
        return ResponseEntity.ok(savedReader);
    }
    //Operacja get pobiera listę wszystkich czytelników
    @GetMapping
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    //pobiera czytelnika o podanym id
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reader not found with id: " + id));
        return ResponseEntity.ok(reader);
    }
    ////PutMapping aktualizuje istniejącego czytelnika w bazie po id
    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable Long id, @RequestBody Reader readerDetails) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reader not found with id: " + id));

        reader.setName(readerDetails.getName());
        reader.setEmail(readerDetails.getEmail());

        Reader updatedReader = readerRepository.save(reader);
        return ResponseEntity.ok(updatedReader);
    }
    //Operacja usuwająca istniejącego czytelnika po id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reader not found with id: " + id));

        readerRepository.delete(reader);
        return ResponseEntity.noContent().build();
    }
}
