<h1>WAŻNE</h1>

Pliki znajdują sie w sekcji master, nie main, są opisane komentarzami

Narzędzia i środowiska których używałem:
* IntelliJ IDEA 2024.2.1
* pgAdmin 4
* Postman
* Docker Desktop

Kroki z eksportem do pdf oraz wysyłaniem maila z przypomnieniem o zwrocie nie są zrobione 
Krok z obrazem dockerowym nie zrobilem z powodu problemow z komputerem ktore proba zrobienia tego kroku powodowala.

Połączenie z bazą PostgreSQL w pliku application.properties</br>
spring.datasource.url = jdbc:postgresql://localhost:5432/databasemd</br>
spring.datasource.username = usermd</br>
spring.datasource.password = haslomd</br>

Projekt domyślnie startuje na:
http://localhost:8080

kontrolery do testowania w Swagger:
* genre-entity-controller
* reader-controller
* book-controller
* calculator-controller

Szablon wprowadzania niektórych danych w Swagger POST

<h3>reader:</h3>
{</br>
  "name": "imie",</br>
  "email": "mail@mail.com"</br>
}</br>
<h3>genre:</h3>
{</br>
  "name": "nazwa"</br>
}
<h3>book:</h3>
{</br>
  "title": "tytul",</br>
  "author": "autor",</br>
  "isbn": "numer isbn",</br>
  "yearPublished": rok wydania,</br>
  "publisher": "wydawca",</br>
  "genre": {</br>
    "id": id istniejącego gatunku</br>
     }</br>
}</br>
<h3>lub bez gatunku:</h3>
{</br
  "title": "tytul",</br>
  "author": "autor",</br>
  "isbn": "numer isbn",</br>
  "yearPublished": rok wydania,</br>
  "publisher": "wydawca"</br>
}
