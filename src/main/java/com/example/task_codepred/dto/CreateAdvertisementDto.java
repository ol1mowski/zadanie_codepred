package com.example.task_codepred.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdvertisementDto {
    
    @NotBlank(message = "Treść ogłoszenia nie może być pusta")
    @Size(min = 10, max = 1000, message = "Treść ogłoszenia musi mieć od 10 do 1000 znaków")
    private String tresc;
}
