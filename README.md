# 🚀 Aplikacja Ogłoszeniowa - Task Codepred

## 📋 Opis Projektu

Aplikacja ogłoszeniowa to nowoczesne REST API napisane w **Java 24** z wykorzystaniem **Spring Boot 3.5.5**, które umożliwia pełne zarządzanie ogłoszeniami (CRUD) z automatycznym śledzeniem liczby wyświetleń.

## ✨ Główne Funkcjonalności

- **🔍 Dodawanie ogłoszeń** - Tworzenie nowych ogłoszeń z automatycznym ustawieniem daty
- **📖 Wyświetlanie ogłoszeń** - Pobieranie ogłoszeń po ID z automatycznym zwiększaniem licznika wyświetleń
- **✏️ Edycja ogłoszeń** - Modyfikacja treści i licznika wyświetleń
- **🗑️ Usuwanie ogłoszeń** - Bezpieczne usuwanie ogłoszeń z walidacją
- **📊 Licznik wyświetleń** - Automatyczne śledzenie popularności ogłoszeń

## 🏗️ Architektura i Technologie

### **Backend Stack:**
- **Java 24** - Najnowsza wersja Java z długoterminowym wsparciem
- **Spring Boot 3.5.5** - Framework do tworzenia aplikacji enterprise
- **Spring Data JPA** - Abstrakcja dostępu do bazy danych
- **H2 Database** - Szybka baza danych w pamięci (idealna do developmentu)
- **Hibernate 6.6.26** - ORM do mapowania obiektowo-relacyjnego
- **Gradle 8.14.3** - System budowania z automatycznym zarządzaniem zależnościami

### **Wzorce Projektowe:**
- **MVC (Model-View-Controller)** - Separacja logiki biznesowej od prezentacji
- **Repository Pattern** - Abstrakcja dostępu do danych
- **Service Layer** - Logika biznesowa w osobnym warstwie
- **DTO Pattern** - Transfer danych między warstwami
- **Global Exception Handling** - Centralna obsługa błędów

### **Struktura Pakietów:**
```
src/main/java/com/example/task_codepred/
├── controller/          # REST API endpoints
├── service/            # Logika biznesowa
├── repository/         # Dostęp do bazy danych
├── entity/            # Encje JPA
├── dto/               # Data Transfer Objects
└── exception/         # Niestandardowe wyjątki
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

## 📡 API Endpoints

### **Base URL:** `http://localhost:8080/ads`

| Metoda | Endpoint | Opis | Status Code |
|--------|----------|------|-------------|
| `POST` | `/ads` | Dodaj nowe ogłoszenie | 201 Created |
| `GET` | `/ads/{id}` | Pobierz ogłoszenie po ID | 200 OK |
| `PUT` | `/ads/{id}` | Edytuj ogłoszenie | 200 OK |
| `DELETE` | `/ads/{id}` | Usuń ogłoszenie | 204 No Content |

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

### **DTOs:**
- **`CreateAdvertisementDto`** - Do tworzenia nowych ogłoszeń
- **`UpdateAdvertisementDto`** - Do aktualizacji istniejących ogłoszeń  
- **`AdvertisementDto`** - Pełne dane ogłoszenia

## 🧪 Testy

### **Pokrycie Testowe:**
- **Testy jednostkowe:** 9 testów (100% pokrycie)
- **Testy integracyjne:** 4 testy (pełny przepływ CRUD)
- **Framework:** JUnit 5 + Mockito

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
```

### **Gradle Dependencies:**
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
    compileOnly 'org.projectlombok:lombok'
}
```

## 🚨 Obsługa Błędów

### **Global Exception Handler:**
- **404 Not Found** - Ogłoszenie nie istnieje
- **400 Bad Request** - Błędne dane wejściowe
- **500 Internal Server Error** - Błędy serwera

### **Format Błędu:**
```json
{
  "timestamp": "2025-08-22T13:45:30",
  "status": 404,
  "message": "Advertisement with id 999 not found"
}
```

## 📚 Kolekcja Postman

Dołączona kolekcja `postman_collection.json` zawiera:
- ✅ Wszystkie endpointy API
- ✅ Przykłady requestów i odpowiedzi
- ✅ Testy automatyczne
- ✅ Zmienne środowiskowe

**Import do Postman:** `File → Import → postman_collection.json`

## 🎯 Kluczowe Zalety Implementacji

### **1. Nowoczesna Technologia:**
- Java 24 z najnowszymi funkcjami
- Spring Boot 3.5.5 z pełnym wsparciem
- Hibernate 6 z optymalizacją wydajności

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
- Walidacja danych wejściowych
- Logowanie i monitoring

### **5. Developer Experience:**
- Hot reload z Spring DevTools
- H2 Console do debugowania
- Automatyczne generowanie schematu DB

## 🔮 Możliwości Rozszerzenia

### **Krótkoterminowe:**
- Dodanie autentykacji i autoryzacji
- Implementacja cache'owania (Redis)
- Dodanie walidacji Bean Validation
- Implementacja paginacji wyników

### **Długoterminowe:**
- Mikrousługowa architektura
- Integracja z Elasticsearch
- System powiadomień
- Dashboard administracyjny

## 📊 Metryki i Monitoring

- **H2 Console** - Monitoring bazy danych
- **Spring Boot Actuator** - Metryki aplikacji
- **Logowanie** - Strukturalne logi z timestampami

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

---

*⭐ Jeśli projekt Ci się podobał, zostaw gwiazdkę!*
