package com.example.projektmd.repository;

import com.example.projektmd.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
}

//repozytorium dziedziczy po JpaRepository domyślne operacje dzialania na bazie