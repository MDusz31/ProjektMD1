package com.example.projektmd.service;

import com.example.projektmd.model.Genre;
import com.example.projektmd.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;
    //Metoda zwracająca wszystkie dodane gatunki
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
    //Metoda zwracająca gatunki po id
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }
    //Metoda dodająca gatunek do bazy
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }
    //Metoda aktualizująca istniejący gatunek
    public Genre updateGenre(Long id, Genre genreDetails) {
        Genre genre = genreRepository.findById(id).orElse(null);
        if (genre != null) {
            genre.setName(genreDetails.getName());
            return genreRepository.save(genre);
        }
        return null;
    }
    // Metoda do usuwania gatunków po id
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
