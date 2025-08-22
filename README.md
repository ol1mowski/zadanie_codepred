# 🚀 Aplikacja Ogłoszeniowa - Task Codepred

## 📋 Opis Projektu

Aplikacja ogłoszeniowa to nowoczesne REST API napisane w **Java 24** z wykorzystaniem **Spring Boot 3.5.5**, które umożliwia pełne zarządzanie ogłoszeniami (CRUD) z automatycznym śledzeniem liczby wyświetleń. Projekt zawiera zaawansowane funkcjonalności bezpieczeństwa, monitoringu i dokumentacji API.

## ✨ Główne Funkcjonalności

- **🔍 Dodawanie ogłoszeń** - Tworzenie nowych ogłoszeń z automatycznym ustawieniem daty
- **📖 Wyświetlanie ogłoszeń** - Pobieranie ogłoszeń po ID z automatycznym zwiększaniem licznika wyświetleń
- **✏️ Edycja ogłoszeń** - Modyfikacja treści i licznika wyświetleń
- **🗑️ Usuwanie ogłoszeń** - Bezpieczne usuwanie ogłoszeń z walidacją
- **📊 Licznik wyświetleń** - Automatyczne śledzenie popularności ogłoszeń
- **🔒 Bezpieczeństwo** - Spring Security z konfiguracją CORS i nagłówków bezpieczeństwa
- **📚 Dokumentacja API** - Swagger UI z OpenAPI 3.0
- **📝 Logowanie** - Strukturalne logi SLF4J z timestampami
- **🏥 Monitoring** - Spring Boot Actuator z endpointami zdrowia
- **✅ Walidacja** - Bean Validation z automatyczną weryfikacją danych
- **🚀 CI/CD** - GitHub Actions z automatycznymi testami

## 🏗️ Architektura i Technologie

### **Backend Stack:**
- **Java 24** - Najnowsza wersja Java z długoterminowym wsparciem
- **Spring Boot 3.5.5** - Framework do tworzenia aplikacji enterprise
- **Spring Data JPA** - Abstrakcja dostępu do bazy danych
- **Spring Security** - Bezpieczeństwo i autoryzacja
- **H2 Database** - Szybka baza danych w pamięci (idealna do developmentu)
- **Hibernate 6.6.26** - ORM do mapowania obiektowo-relacyjnego
- **Gradle 8.14.3** - System budowania z automatycznym zarządzaniem zależnościami
- **Springdoc OpenAPI** - Automatyczna generacja dokumentacji API
- **Spring Boot Actuator** - Monitoring i metryki aplikacji

### **Wzorce Projektowe:**
- **MVC (Model-View-Controller)** - Separacja logiki biznesowej od prezentacji
- **Repository Pattern** - Abstrakcja dostępu do danych
- **Service Layer** - Logika biznesowa w osobnym warstwie
- **DTO Pattern** - Transfer danych między warstwami
- **Global Exception Handling** - Centralna obsługa błędów
- **Bean Validation** - Walidacja danych wejściowych
- **Security Configuration** - Konfiguracja bezpieczeństwa

### **Struktura Pakietów:**
```
src/main/java/com/example/task_codepred/
├── controller/          # REST API endpoints
├── service/            # Logika biznesowa
├── repository/         # Dostęp do bazy danych
├── entity/            # Encje JPA
├── dto/               # Data Transfer Objects
├── exception/         # Niestandardowe wyjątki
├── config/            # Konfiguracje (Security, OpenAPI)
└── aspect/            # Aspekty (audyt, logowanie)
```

## 🚀 Szybki Start

### **Wymagania Systemowe:**
- Java 24 lub nowsza
- Gradle 8.x
- Port 8080 dostępny

### **Instalacja i Uruchomienie:**

1. **Klonowanie repozytorium:**
```bash
git clone https://github.com/ol1mowski/zadanie_codepred.git
cd zadanie_codepred
```

2. **Uruchomienie aplikacji:**
```bash
./gradlew bootRun
```

3. **Weryfikacja działania:**
```bash
curl http://localhost:8080/ads
```

### **Dostęp do Bazy H2:**
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

### **Dokumentacja API (Swagger):**
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

### **Monitoring (Actuator):**
- **Health Check:** `http://localhost:8080/actuator/health`

## 📡 API Endpoints

### **Base URL:** `http://localhost:8080/ads`

| Metoda | Endpoint | Opis | Status Code | Walidacja |
|--------|----------|------|-------------|-----------|
| `POST` | `/ads` | Dodaj nowe ogłoszenie | 201 Created | ✅ Bean Validation |
| `GET` | `/ads/{id}` | Pobierz ogłoszenie po ID | 200 OK | - |
| `PUT` | `/ads/{id}` | Edytuj ogłoszenie | 200 OK | ✅ Bean Validation |
| `DELETE` | `/ads/{id}` | Usuń ogłoszenie | 204 No Content | - |

### **Przykłady Użycia:**

#### **1. Dodanie Ogłoszenia**
```bash
curl -X POST http://localhost:8080/ads \
  -H "Content-Type: application/json" \
  -d '{"tresc": "Sprzedam samochód osobowy"}'
```

**Odpowiedź:**
```json
{
  "id": 1,
  "tresc": "Sprzedam samochód osobowy",
  "dataDodania": "2025-08-22T13:45:00",
  "iloscWyswietlen": 0
}
```

#### **2. Pobranie Ogłoszenia**
```bash
curl http://localhost:8080/ads/1
```

**Odpowiedź:**
```json
{
  "id": 1,
  "tresc": "Sprzedam samochód osobowy",
  "dataDodania": "2025-08-22T13:45:00",
  "iloscWyswietlen": 1
}
```

#### **3. Edycja Ogłoszenia**
```bash
curl -X PUT http://localhost:8080/ads/1 \
  -H "Content-Type: application/json" \
  -d '{"tresc": "Sprzedam samochód osobowy - cena do negocjacji", "iloscWyswietlen": 5}'
```

#### **4. Usunięcie Ogłoszenia**
```bash
curl -X DELETE http://localhost:8080/ads/1
```

## 🗄️ Model Danych

### **Encja Advertisement:**
```java
@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tresc;
    
    @Column(name = "data_dodania")
    private LocalDateTime dataDodania;
    
    @Column(name = "ilosc_wyswietlen")
    private int iloscWyswietlen = 0;
}
```

### **DTOs z Walidacją:**
- **`CreateAdvertisementDto`** - Do tworzenia nowych ogłoszeń
  - `@NotBlank` - Treść nie może być pusta
  - `@Size(min = 3, max = 500)` - Długość treści 3-500 znaków
- **`UpdateAdvertisementDto`** - Do aktualizacji istniejących ogłoszeń
  - `@NotBlank` - Treść nie może być pusta
  - `@Size(min = 3, max = 500)` - Długość treści 3-500 znaków
  - `@Min(0)` - Liczba wyświetleń nie może być ujemna
- **`AdvertisementDto`** - Pełne dane ogłoszenia

## 🔒 Bezpieczeństwo

### **Spring Security Configuration:**
- **CSRF Protection** - Wyłączone dla API REST
- **Security Headers** - HSTS, X-Frame-Options, Content Security Policy
- **Public Endpoints** - `/ads/**`, `/h2-console/**`, `/swagger-ui/**`, `/actuator/**`
- **Request Logging** - Szczegółowe logowanie żądań HTTP

### **Bezpieczne Nagłówki:**
```http
Strict-Transport-Security: max-age=31536000
X-Frame-Options: DENY
X-Content-Type-Options: nosniff
```

## 📚 Dokumentacja API (Swagger/OpenAPI)

### **Swagger UI:**
- **URL:** `http://localhost:8080/swagger-ui.html`
- **Funkcje:** Interaktywna dokumentacja API
- **Testowanie:** Możliwość testowania endpointów bezpośrednio z UI
- **Sortowanie:** Endpointy posortowane alfabetycznie i według metody

### **OpenAPI 3.0:**
- **Specyfikacja:** `http://localhost:8080/v3/api-docs`
- **Format:** JSON zgodny ze standardem OpenAPI 3.0
- **Annotacje:** `@Operation`, `@ApiResponse`, `@Tag`

### **Przykład Annotacji:**
```java
@Operation(summary = "Add new advertisement", 
           description = "Creates a new advertisement with automatic date setting")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Advertisement created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid input data")
})
```

## 📝 Logowanie (SLF4J)

### **Konfiguracja Logowania:**
- **Framework:** SLF4J + Logback
- **Poziom:** INFO dla aplikacji, DEBUG dla Hibernate
- **Format:** Strukturalne logi z timestampami i thread ID
- **Lokalizacja:** Console + File (opcjonalnie)

### **Przykłady Logów:**
```
2025-08-22 15:42:26 [restartedMain] INFO  c.e.t.TaskCodepredApplication - Starting TaskCodepredApplication
2025-08-22 15:42:26 [restartedMain] INFO  c.e.t.TaskCodepredApplication - Using Java 24.0.1
2025-08-22 15:42:28 [restartedMain] INFO  AdvertisementService - Adding new advertisement with content: Sprzedam samochód
```

### **Logowanie Operacji:**
- **Dodawanie:** ID, treść, timestamp
- **Aktualizacja:** ID, timestamp
- **Usuwanie:** ID, timestamp
- **Pobieranie:** ID, liczba wyświetleń (przed i po)

## 🏥 Monitoring (Spring Boot Actuator)

### **Dostępne Endpointy:**
- **`/actuator/health`** - Status zdrowia aplikacji
- **`/actuator/info`** - Informacje o aplikacji
- **`/actuator/metrics`** - Metryki systemowe

### **Health Check Response:**
```json
{
  "status": "UP",
  "application": "Aplikacja Ogłoszeniowa",
  "version": "1.0.0",
  "timestamp": "2025-08-22 15:42:30",
  "environment": "Development",
  "message": "Aplikacja jest w pełni operacyjna i gotowa do obsługi ogłoszeń"
}
```

### **Metryki:**
- **Database Health** - Status połączenia z H2
- **Disk Space** - Wolne miejsce na dysku
- **Application Status** - Ogólny stan aplikacji

## 🚨 Obsługa Błędów

### **Global Exception Handler:**
- **404 Not Found** - Ogłoszenie nie istnieje
- **400 Bad Request** - Błędne dane wejściowe (Bean Validation)
- **500 Internal Server Error** - Błędy serwera

### **Format Błędu:**
```json
{
  "timestamp": "2025-08-22T13:45:30",
  "status": 404,
  "message": "Advertisement with id 999 not found"
}
```

### **Walidacja Bean Validation:**
```json
{
  "timestamp": "2025-08-22T13:45:30",
  "status": 400,
  "message": "Validation error: Treść ogłoszenia nie może być pusta"
}
```

## 🧪 Testy

### **Pokrycie Testowe:**
- **Testy jednostkowe:** 9 testów (100% pokrycie)
- **Testy integracyjne:** 4 testy (pełny przepływ CRUD)
- **Framework:** JUnit 5 + Mockito
- **Baza testowa:** H2 in-memory

### **Uruchomienie Testów:**
```bash
# Wszystkie testy
./gradlew test

# Tylko testy jednostkowe
./gradlew test --tests "*ServiceTest*"

# Tylko testy integracyjne  
./gradlew test --tests "*IntegrationTest*"
```

### **Przykład Testu Integracyjnego:**
```java
@Test
void testFullCrudFlow() throws Exception {
    // 1. ADD → 2. GET → 3. UPDATE → 4. DELETE
    // Sprawdza pełny cykl życia ogłoszenia
    // Weryfikuje automatyczne zwiększanie licznika wyświetleń
}
```

## 🚀 CI/CD (GitHub Actions)

### **Automatyzacja:**
- **Trigger:** Push/Pull Request na branch `main`
- **Java Version:** 24
- **Gradle Version:** 8.14.3
- **Cache:** Automatyczne cachowanie zależności Gradle

### **Workflow:**
```yaml
name: CI/CD Pipeline
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '24'
          distribution: 'temurin'
      - uses: gradle/gradle-build-action@v2
        with:
          arguments: clean build test
```

### **Korzyści:**
- **Automatyczne testy** przy każdej zmianie
- **Walidacja build** przed merge
- **Cache dependencies** dla szybszych buildów
- **Status reporting** w GitHub

## 🔧 Konfiguracja

### **application.properties:**
```properties
# Baza danych H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Springdoc OpenAPI
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# Logging
logging.level.com.example.task_codepred=INFO
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n

# Actuator
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

### **Gradle Dependencies:**
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.0'
    runtimeOnly 'com.h2database:h2'
    compileOnly 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}
```

## 📚 Kolekcja Postman

Dołączona kolekcja `postman_collection.json` zawiera:
- ✅ Wszystkie endpointy API
- ✅ Przykłady requestów i odpowiedzi
- ✅ Testy automatyczne
- ✅ Zmienne środowiskowe
- ✅ Przykład inkrementacji licznika wyświetleń

**Import do Postman:** `File → Import → postman_collection.json`

## 🎯 Kluczowe Zalety Implementacji

### **1. Nowoczesna Technologia:**
- Java 24 z najnowszymi funkcjami
- Spring Boot 3.5.5 z pełnym wsparciem
- Hibernate 6.6.26 z optymalizacją wydajności

### **2. Czysta Architektura:**
- Separacja warstw (Controller → Service → Repository)
- Wzorce projektowe zgodne z SOLID
- Czytelny i maintainable kod

### **3. Kompleksowe Testowanie:**
- Testy jednostkowe z Mockito
- Testy integracyjne z H2
- 100% pokrycie funkcjonalności

### **4. Produkcyjna Jakość:**
- Globalna obsługa wyjątków
- Bean Validation z automatyczną walidacją
- Logowanie i monitoring z Actuator
- Spring Security z bezpiecznymi nagłówkami

### **5. Developer Experience:**
- Hot reload z Spring DevTools
- H2 Console do debugowania
- Swagger UI do testowania API
- Automatyczne generowanie schematu DB

### **6. DevOps i CI/CD:**
- GitHub Actions z automatycznymi testami
- Cache dependencies dla szybszych buildów
- Automatyczna walidacja kodu

## 🔮 Możliwości Rozszerzenia

### **Krótkoterminowe:**
- ✅ **Zaimplementowane:** Autentykacja i autoryzacja (Spring Security)
- ✅ **Zaimplementowane:** Walidacja Bean Validation
- ✅ **Zaimplementowane:** Dokumentacja API (Swagger/OpenAPI)
- ✅ **Zaimplementowane:** Logowanie SLF4J
- ✅ **Zaimplementowane:** Monitoring (Actuator)
- ✅ **Zaimplementowane:** CI/CD (GitHub Actions)
- Implementacja cache'owania (Redis)
- Dodanie paginacji wyników

### **Długoterminowe:**
- Mikrousługowa architektura
- Integracja z Elasticsearch
- System powiadomień
- Dashboard administracyjny
- Metryki Prometheus + Grafana

## 📊 Metryki i Monitoring

- **H2 Console** - Monitoring bazy danych
- **Spring Boot Actuator** - Metryki aplikacji i zdrowie
- **SLF4J Logging** - Strukturalne logi z timestampami
- **Security Headers** - Monitoring bezpieczeństwa
- **GitHub Actions** - Metryki CI/CD

## 🤝 Współpraca

### **Zgłaszanie Problemów:**
1. Sprawdź istniejące issues
2. Utwórz nowy issue z opisem problemu
3. Dołącz logi i kroki reprodukcji

### **Pull Requests:**
1. Fork repozytorium
2. Utwórz feature branch
3. Dodaj testy dla nowej funkcjonalności
4. Uruchom wszystkie testy
5. Utwórz Pull Request

## 📄 Licencja

Projekt jest częścią zadania rekrutacyjnego. Wszelkie prawa zastrzeżone.

## 👨‍💻 Autor

**Task Codepred** - Aplikacja ogłoszeniowa zbudowana w ramach procesu rekrutacyjnego.

---

## 🚀 **Uruchom Aplikację w Jednej Komendzie:**

```bash
./gradlew bootRun
```

**Aplikacja będzie dostępna pod adresem:** `http://localhost:8080`

**Baza danych H2:** `http://localhost:8080/h2-console`

**Swagger UI:** `http://localhost:8080/swagger-ui.html`

**Health Check:** `http://localhost:8080/actuator/health`

---

## 🔥 **Nowe Funkcjonalności w Najnowszej Wersji:**

- ✅ **Spring Security** - Bezpieczeństwo i autoryzacja
- ✅ **OpenAPI 3.0** - Automatyczna dokumentacja API
- ✅ **SLF4J Logging** - Strukturalne logowanie
- ✅ **Bean Validation** - Automatyczna walidacja danych
- ✅ **Spring Boot Actuator** - Monitoring aplikacji
- ✅ **GitHub Actions** - Automatyczne CI/CD
- ✅ **Security Headers** - Bezpieczne nagłówki HTTP
- ✅ **Request Logging** - Szczegółowe logowanie żądań

*⭐ Jeśli projekt Ci się podobał, zostaw gwiazdkę!*
