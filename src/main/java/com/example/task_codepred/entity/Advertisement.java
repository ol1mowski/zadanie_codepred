package com.example.task_codepred.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "advertisements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Treść ogłoszenia nie może być pusta")
    @Size(min = 10, max = 1000, message = "Treść ogłoszenia musi mieć od 10 do 1000 znaków")
    @Column(nullable = false)
    private String tresc;

    @Column(name = "data_dodania")
    private LocalDateTime dataDodania;

    @Min(value = 0, message = "Liczba wyświetleń nie może być ujemna")
    @Column(name = "ilosc_wyswietlen")
    private int iloscWyswietlen = 0;

    @PrePersist
    protected void onCreate() {
        this.dataDodania = LocalDateTime.now();
    }
}
