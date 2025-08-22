package com.example.task_codepred.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {
    
    @NotNull(message = "ID ogłoszenia nie może być puste")
    @Min(value = 1, message = "ID ogłoszenia musi być większe od 0")
    private Long id;
    
    @NotNull(message = "Treść ogłoszenia nie może być pusta")
    @Size(min = 10, max = 1000, message = "Treść ogłoszenia musi mieć od 10 do 1000 znaków")
    private String tresc;
    
    @NotNull(message = "Data dodania nie może być pusta")
    private LocalDateTime dataDodania;
    
    @Min(value = 0, message = "Liczba wyświetleń nie może być ujemna")
    private int iloscWyswietlen;
}
