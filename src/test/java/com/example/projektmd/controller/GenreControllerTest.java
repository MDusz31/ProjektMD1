package com.example.projektmd.controller;

import com.example.projektmd.model.Genre;
import com.example.projektmd.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class GenreControllerTest {

    @InjectMocks
    private GenreController genreController;

    @Mock
    private GenreService genreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllGenres() {
        // Given
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Science Fiction");

        List<Genre> genres = Arrays.asList(genre1, genre2);
        when(genreService.getAllGenres()).thenReturn(genres);

        // When
        List<Genre> result = genreController.getAllGenres();

        // Then
        assertEquals(2, result.size());
        assertEquals("Fiction", result.get(0).getName());
        assertEquals("Science Fiction", result.get(1).getName());
    }

    @Test
    public void testGetGenreByIdFound() {
        // Given
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fiction");

        when(genreService.getGenreById(1L)).thenReturn(genre);

        // When
        ResponseEntity<Genre> response = genreController.getGenreById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fiction", response.getBody().getName());
    }

    @Test
    public void testGetGenreByIdNotFound() {
        // Given
        when(genreService.getGenreById(anyLong())).thenReturn(null);

        // When
        ResponseEntity<Genre> response = genreController.getGenreById(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateGenre() {
        // Given
        Genre genre = new Genre();
        genre.setName("Fiction");

        Genre createdGenre = new Genre();
        createdGenre.setId(1L);
        createdGenre.setName("Fiction");

        when(genreService.createGenre(any(Genre.class))).thenReturn(createdGenre);

        // When
        ResponseEntity<Genre> response = genreController.createGenre(genre);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Fiction", response.getBody().getName());
    }

    @Test
    public void testUpdateGenreFound() {
        // Given
        Genre updatedGenre = new Genre();
        updatedGenre.setId(1L);
        updatedGenre.setName("Updated Fiction");

        when(genreService.updateGenre(1L, updatedGenre)).thenReturn(updatedGenre);

        // When
        ResponseEntity<Genre> response = genreController.updateGenre(1L, updatedGenre);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Fiction", response.getBody().getName());
    }

    @Test
    public void testUpdateGenreNotFound() {
        // Given
        when(genreService.updateGenre(anyLong(), any(Genre.class))).thenReturn(null);

        // When
        ResponseEntity<Genre> response = genreController.updateGenre(1L, new Genre());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteGenre() {
        // Given
        doNothing().when(genreService).deleteGenre(1L);

        // When
        ResponseEntity<Void> response = genreController.deleteGenre(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(genreService, times(1)).deleteGenre(1L);
    }
}
