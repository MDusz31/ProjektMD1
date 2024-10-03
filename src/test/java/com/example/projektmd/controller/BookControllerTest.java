package com.example.projektmd.controller;

import com.example.projektmd.model.Book;
import com.example.projektmd.model.Reader;
import com.example.projektmd.service.BookService;
import com.example.projektmd.service.ReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookService.getAllBooks(null, null, null, null, null, null)).thenReturn(Collections.singletonList(book));

        var response = bookController.getAllBooks(null, null, null, null, null, null);

        assertEquals(1, response.size());
        assertEquals("Test Book", response.get(0).getTitle());
        verify(bookService, times(1)).getAllBooks(null, null, null, null, null, null);
    }

    @Test
    void testGetBookByIdFound() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Book", response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookByIdNotFound() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        book.setTitle("New Book");

        when(bookService.addBook(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.addBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Book", response.getBody().getTitle());
        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");

        when(bookService.updateBook(any(Long.class), any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.updateBook(1L, book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Book", response.getBody().getTitle());
        verify(bookService, times(1)).updateBook(1L, book);
    }

    @Test
    void testDeleteBook() {
        ResponseEntity<Void> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void testBorrowBookNotFound() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = bookController.borrowBook(1L, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Książka o ID 1 nie została znaleziona.", response.getBody());
    }

    @Test
    void testReturnBookNotFound() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = bookController.returnBook(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Książka o ID 1 nie została znaleziona.", response.getBody());
    }

    @Test
    void testReturnBookNotBorrowed() {
        Book book = new Book();
        book.setId(1L);
        book.setReader(null);

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<String> response = bookController.returnBook(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Książka o ID 1 nie jest obecnie wypożyczona.", response.getBody());
    }

    @Test
    void testBorrowBookAlreadyBorrowed() {
        Book book = new Book();
        book.setId(1L);
        book.setReader(new Reader()); // Book is already borrowed

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(readerService.getReaderById(1L)).thenReturn(Optional.of(new Reader()));

        ResponseEntity<String> response = bookController.borrowBook(1L, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Książka o ID 1 jest już wypożyczona.", response.getBody());
    }
}
