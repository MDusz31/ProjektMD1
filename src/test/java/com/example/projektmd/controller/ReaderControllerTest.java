package com.example.projektmd.controller;

import com.example.projektmd.model.Reader;
import com.example.projektmd.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ReaderControllerTest {

    @InjectMocks
    private ReaderController readerController;

    @Mock
    private ReaderRepository readerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddReader() {
        // Given
        Reader reader = new Reader();
        reader.setName("John Doe");
        reader.setEmail("john@example.com");

        when(readerRepository.save(any(Reader.class))).thenReturn(reader);

        // When
        ResponseEntity<Reader> response = readerController.addReader(reader);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john@example.com", response.getBody().getEmail());
    }

    @Test
    public void testGetAllReaders() {
        // Given
        Reader reader1 = new Reader();
        reader1.setId(1L);
        reader1.setName("John Doe");
        reader1.setEmail("john@example.com");

        Reader reader2 = new Reader();
        reader2.setId(2L);
        reader2.setName("Jane Smith");
        reader2.setEmail("jane@example.com");

        List<Reader> readers = Arrays.asList(reader1, reader2);
        when(readerRepository.findAll()).thenReturn(readers);

        // When
        List<Reader> result = readerController.getAllReaders();

        // Then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    public void testGetReaderByIdFound() {
        // Given
        Reader reader = new Reader();
        reader.setId(1L);
        reader.setName("John Doe");
        reader.setEmail("john@example.com");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        // When
        ResponseEntity<Reader> response = readerController.getReaderById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testGetReaderByIdNotFound() {
        // Given
        when(readerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            readerController.getReaderById(1L);
        });

        // Then
        assertEquals("Reader not found with id: 1", exception.getMessage());
    }

    @Test
    public void testUpdateReaderFound() {
        // Given
        Reader existingReader = new Reader();
        existingReader.setId(1L);
        existingReader.setName("John Doe");
        existingReader.setEmail("john@example.com");

        Reader updatedReader = new Reader();
        updatedReader.setName("John Smith");
        updatedReader.setEmail("johnsmith@example.com");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(existingReader));
        when(readerRepository.save(any(Reader.class))).thenReturn(existingReader);

        // When
        ResponseEntity<Reader> response = readerController.updateReader(1L, updatedReader);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Smith", response.getBody().getName()); // Sprawdzanie starego imienia
    }

    @Test
    public void testUpdateReaderNotFound() {
        // Given
        when(readerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            readerController.updateReader(1L, new Reader());
        });

        // Then
        assertEquals("Reader not found with id: 1", exception.getMessage());
    }

    @Test
    public void testDeleteReader() {
        // Given
        Reader reader = new Reader();
        reader.setId(1L);
        reader.setName("John Doe");
        reader.setEmail("john@example.com");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        doNothing().when(readerRepository).delete(any(Reader.class));

        // When
        ResponseEntity<Void> response = readerController.deleteReader(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(readerRepository, times(1)).delete(reader);
    }
}
