package com.example.projektmd.repository;

import com.example.projektmd.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
//repozytorium dziedziczy po JpaRepository domyślne operacje dzialania na bazie