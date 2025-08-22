package com.example.task_codepred.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String tresc;

    @Column(name = "data_dodania")
    private LocalDateTime dataDodania;

    @Column(name = "ilosc_wyswietlen")
    private int iloscWyswietlen = 0;

    @PrePersist
    protected void onCreate() {
        this.dataDodania = LocalDateTime.now();
    }
}
