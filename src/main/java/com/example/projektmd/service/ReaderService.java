package com.example.projektmd.service;

import com.example.projektmd.model.Reader;
import com.example.projektmd.repository.ReaderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }
    //Metoda zwracająca wszystkich czytelnikow
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }
    //Metoda zwracająca czytelnikow po id
    public Optional<Reader> getReaderById(Long id) {
        return readerRepository.findById(id);
    }
    //Metoda dodająca czytelnikow
    public Reader addReader(Reader reader) {
        return readerRepository.save(reader);
    }
    //Metoda aktualizująca czytelnikow po id
    public Reader updateReader(Long id, Reader readerDetails) {
        Reader reader = readerRepository.findById(id).orElseThrow();
        reader.setName(readerDetails.getName());
        reader.setEmail(readerDetails.getEmail());
        return readerRepository.save(reader);
    }
    //Metoda usuwająca czytelnikow o podanym id
    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }
}
