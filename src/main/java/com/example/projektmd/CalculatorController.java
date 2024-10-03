package com.example.projektmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    //tworzenie Loggera
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    //funkcja dodająca liczby a i b wraz z zapisem do logow
    @GetMapping("/add")
    public double add(@RequestParam double a, @RequestParam double b) {
        double result = a + b;
        logger.info("Dodawanie: {} + {} = {}", a, b, result);
        return result;
    }
    //funkcja odejmująca liczbe b od liczby a wraz z zapisem do logow
    @GetMapping("/subtract")
    public double subtract(@RequestParam double a, @RequestParam double b) {
        double result = a - b;
        logger.info("Odejmowanie: {} - {} = {}", a, b, result);
        return result;
    }
    //funkcja mnożąca liczby a i b wraz z zapisem do logow
    @GetMapping("/multiply")
    public double multiply(@RequestParam double a, @RequestParam double b) {
        double result = a * b;
        logger.info("Mnożenie: {} * {} = {}", a, b, result);
        return result;
    }
    //funkcja dzieląca liczbę a przez liczbę b wraz z zapisem do logow
    @GetMapping("/divide")
    public Object divide(@RequestParam double a, @RequestParam double b) {
        if (b == 0) {
            logger.error("Dzielenie przez zero: {} / {}", a, b);
            return new ErrorResponse("Dzielenie przez zero nie jest dozwolone.");
        }
        double result = a / b;
        logger.info("Dzielenie: {} / {} = {}", a, b, result);
        return result;
    }
    //Błąd przy dzieleniu przez 0
    static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
