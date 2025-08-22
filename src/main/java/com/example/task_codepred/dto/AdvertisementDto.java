package com.example.task_codepred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {
    private Long id;
    private String tresc;
    private LocalDateTime dataDodania;
    private int iloscWyswietlen;
}
