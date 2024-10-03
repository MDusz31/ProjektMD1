package com.example.projektmd.service;

import com.example.projektmd.model.Book;
import com.example.projektmd.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //Metoda zwracająca książki na podstawie podanego parametru (np. czy książka jest wypoąyczona lub bo autorze)
    public List<Book> getAllBooks(String title, String author, String isbn, Integer yearPublished, String publisher, Boolean isBorrowed) {
        return bookRepository.findAll(createSpecification(title, author, isbn, yearPublished, publisher, isBorrowed));
    }
    //Metoda zwracają ca książki po id
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    //Dodaje nową ksiażke do bazy danych
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
    //Metoda umożliwiająca modyfikacje istniejącej ksiażki w bazy danych
    public Book updateBook(Long id, Book book) {
        book.setId(id);
        return bookRepository.save(book);
    }
    //Usuwa książke o podanym id
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    //Metoda tworząca odpowiednie zapytanie od bazy używana w metodzie zwracającej książki
    private Specification<Book> createSpecification(String title, String author, String isbn, Integer yearPublished, String publisher, Boolean isBorrowed) {
        return (root, query, criteriaBuilder) -> {
            var option = criteriaBuilder.conjunction();

            if (title != null) {
                option = criteriaBuilder.and(option, criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            if (author != null) {
                option = criteriaBuilder.and(option, criteriaBuilder.like(root.get("author"), "%" + author + "%"));
            }
            if (isbn != null) {
                option = criteriaBuilder.and(option, criteriaBuilder.equal(root.get("isbn"), isbn));
            }
            if (yearPublished != null) {
                option = criteriaBuilder.and(option, criteriaBuilder.equal(root.get("yearPublished"), yearPublished));
            }
            if (publisher != null) {
                option = criteriaBuilder.and(option, criteriaBuilder.like(root.get("publisher"), "%" + publisher + "%"));
            }
            if (isBorrowed != null) {
                option = criteriaBuilder.and(option, isBorrowed ? criteriaBuilder.isNotNull(root.get("reader")) : criteriaBuilder.isNull(root.get("reader")));
            }

            return option;
        };
    }
}
