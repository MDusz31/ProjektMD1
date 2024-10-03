package com.example.projektmd.repository;

import com.example.projektmd.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
//repozytorium dziedziczy po JpaRepository domyślne operacje dzialania na bazie