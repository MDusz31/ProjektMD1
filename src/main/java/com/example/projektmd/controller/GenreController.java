package com.example.projektmd.controller;

import com.example.projektmd.model.Genre;
import com.example.projektmd.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;
    //Operacja get pobiera listę wszystkich gatunków
    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
    //Pobiera gatunek po jego identyfikatorze
    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        Genre genre = genreService.getGenreById(id);
        if (genre != null) {
            return ResponseEntity.ok(genre);
        }
        return ResponseEntity.notFound().build();
    }
    //Operacja Post tworzy i dodaje nowy gatunek
    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        Genre createdGenre = genreService.createGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }
    //PutMapping aktualizuje istniejący w bazie gatunek po id
    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genreDetails) {
        Genre updatedGenre = genreService.updateGenre(id, genreDetails);
        if (updatedGenre != null) {
            return ResponseEntity.ok(updatedGenre);
        }
        return ResponseEntity.notFound().build();
    }
    //Operacja usuwająca istniejący gatunek po id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
