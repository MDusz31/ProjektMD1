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
        Reader reader = new Reader();
        reader.setName("Jan Kowalski");
        reader.setEmail("jan@gm.com");

        when(readerRepository.save(any(Reader.class))).thenReturn(reader);

        ResponseEntity<Reader> response = readerController.addReader(reader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jan Kowalski", response.getBody().getName());
        assertEquals("jan@gm.com", response.getBody().getEmail());
    }

    @Test
    public void testGetAllReaders() {
        Reader reader1 = new Reader();
        reader1.setId(1L);
        reader1.setName("Jan Kowalski");
        reader1.setEmail("jan@gm.com");

        Reader reader2 = new Reader();
        reader2.setId(2L);
        reader2.setName("Karol Krawczyk");
        reader2.setEmail("Karol@gm.com");

        List<Reader> readers = Arrays.asList(reader1, reader2);
        when(readerRepository.findAll()).thenReturn(readers);

        List<Reader> result = readerController.getAllReaders();

        assertEquals(2, result.size());
        assertEquals("Jan Kowalski", result.get(0).getName());
        assertEquals("Karol Krawczyk", result.get(1).getName());
    }

    @Test
    public void testGetReaderById() {
        Reader reader = new Reader();
        reader.setId(1L);
        reader.setName("Jan");
        reader.setEmail("j@gm.com");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        ResponseEntity<Reader> response = readerController.getReaderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jan", response.getBody().getName());
    }

    @Test
    public void testUpdateReader() {
        Reader existingReader = new Reader();
        existingReader.setId(1L);
        existingReader.setName("Jan");
        existingReader.setEmail("j@gm.com");

        Reader updatedReader = new Reader();
        updatedReader.setName("Karol");
        updatedReader.setEmail("k@gm.com");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(existingReader));
        when(readerRepository.save(any(Reader.class))).thenReturn(existingReader);

        ResponseEntity<Reader> response = readerController.updateReader(1L, updatedReader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Karol", response.getBody().getName());
    }


    @Test
    public void testDeleteReader() {
        Reader reader = new Reader();
        reader.setId(1L);
        reader.setName("Jan");
        reader.setEmail("j@gm.com");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        doNothing().when(readerRepository).delete(any(Reader.class));

        ResponseEntity<Void> response = readerController.deleteReader(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(readerRepository, times(1)).delete(reader);
    }
}
