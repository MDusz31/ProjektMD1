package com.example.projektmd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public String helloWorld() {
        return  "Hello World" +
                "<html><body></br>" +
                "<a href='/swagger-ui.html'>Swagger</a>" +
                "</body></html>";
    }
}

//Kontroler wyswietlający napis Hello World przy wejściu na stronę oraz odsyłacz na stronę Swaggera