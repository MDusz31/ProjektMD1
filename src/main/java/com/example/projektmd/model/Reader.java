package com.example.projektmd.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reader")
public class Reader {

    //pola przechowujące informacje oraz pole id z kluczem głównym
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Book> borrowedBooks = new ArrayList<>();

    //getery i settery dające dostęp do pól oraz ich modyfikacje

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Przypisanie wypożyczoinej książki do czytelnika (nieuzywane)

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void addBook(Book book) {
        borrowedBooks.add(book);
        book.setReader(this);
    }

    public void removeBook(Book book) {
        borrowedBooks.remove(book);
        book.setReader(null);
    }
}
