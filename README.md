<h1>WAŻNE</h1>

Pliki znajdują sie w sekcji master, nie main

Kroki z eksportem do pdf oraz wysyłaniem maila z przypomnieniem o zwrocie nie są zrobione 
Krok z obrazem dockerowym nie zrobilem z powodu problemow z komputerem ktore proba zrobienia tego kroku powodowala.

Połączenie z bazą PostgreSQL w pliku application.properties
spring.datasource.url = jdbc:postgresql://localhost:5432/databasemd
spring.datasource.username = usermd
spring.datasource.password = haslomd

Projekt domyślnie startuje na:
http://localhost:8080

kontrolery do testowania w Swagger:
* genre-entity-controller
* reader-controller
* book-controller
* calculator-controller

Szablon wprowadzania niektórych danych w Swagger POST

reader:
{
  "name": "imie",
  "email": "mail@mail.com"
}
genre:
{
  "name": "nazwa"
}
book:
{
  "title": "tytul",
  "author": "autor",
  "isbn": "numer isbn",
  "yearPublished": rok wydania,
  "publisher": "wydawca",
  "genre": {
    "id": id istniejącego gatunku
  }
}
lub bez gatunku:
{
  "title": "tytul",
  "author": "autor",
  "isbn": "numer isbn",
  "yearPublished": rok wydania,
  "publisher": "wydawca"
}
