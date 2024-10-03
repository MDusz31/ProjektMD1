package com.example.projektmd.controller;

import com.example.projektmd.model.Book;
import com.example.projektmd.model.Reader;
import com.example.projektmd.service.BookService;
import com.example.projektmd.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    //Pobieranie listy książek z filtrowaniem
    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer yearPublished,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Boolean isBorrowed) {
        return bookService.getAllBooks(title, author, isbn, yearPublished, publisher, isBorrowed);
    }
    //Pobieranie książki po id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Dodawanie nowej książki
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    //Aktualizowanie istniejącej książki po podanym id
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }
    //Usuwanie istniejącej książki o podanym id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    //Wypozyczanie wybranej książki dla danego czytelika, wraz ze sprawdzeniem czy książka i czytelnik istnieją oraz czy książka nie jest już wypożyczona
    @PostMapping("/{bookId}/borrow/{readerId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId, @PathVariable Long readerId) {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Książka o ID " + bookId + " nie została znaleziona.");
        }

        Optional<Reader> readerOptional = readerService.getReaderById(readerId);
        if (readerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Czytelnik o ID " + readerId + " nie został znaleziony.");
        }

        Book book = bookOptional.get();
        if (book.isBorrowed()) {
            return ResponseEntity.badRequest().body("Książka o ID " + bookId + " jest już wypożyczona.");
        }

        book.setReader(readerOptional.get());
        bookService.updateBook(bookId, book);
        return ResponseEntity.ok("Książka została wypożyczona.");
    }
    //Zwracanie książki po id, wraz ze sprawdzeniem czy książka istnieje oraz czy jest wypożyczona
    @PostMapping("/{bookId}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Książka o ID " + bookId + " nie została znaleziona.");
        }

        Book book = bookOptional.get();
        if (book.getReader() == null) {
            return ResponseEntity.badRequest().body("Książka o ID " + bookId + " nie jest obecnie wypożyczona.");
        }

        book.setReader(null);
        bookService.updateBook(bookId, book);
        return ResponseEntity.ok("Książka została zwrócona.");
    }
    //Eksportuje książki z filtrowaniem do pliku CSV
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportBooksToCSV(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer yearPublished,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Boolean isBorrowed) throws IOException {

        List<Book> books = bookService.getAllBooks(title, author, isbn, yearPublished, publisher, isBorrowed);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        StringBuilder csvBuilder = new StringBuilder();
        //struktura pliku
        csvBuilder.append("ID,Tytuł,Autor,ISBN,Rok Wydania,Wydawnictwo\n");

        for (Book book : books) {
            csvBuilder.append(book.getId()).append(",")
                    .append(book.getTitle()).append(",")
                    .append(book.getAuthor()).append(",")
                    .append(book.getIsbn()).append(",")
                    .append(book.getYearPublished()).append(",")
                    .append(book.getPublisher()).append("\n");
        }

        outputStream.write(csvBuilder.toString().getBytes());
        byte[] csvBytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "books.csv");
        headers.setContentLength(csvBytes.length);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
