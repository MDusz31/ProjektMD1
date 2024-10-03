package com.example.projektmd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "genre")
public class Genre {
    //Pole id generowane z kluczem głównym
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

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
}
