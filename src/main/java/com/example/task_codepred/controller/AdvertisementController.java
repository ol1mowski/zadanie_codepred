package com.example.task_codepred.controller;

import com.example.task_codepred.dto.AdvertisementDto;
import com.example.task_codepred.dto.CreateAdvertisementDto;
import com.example.task_codepred.dto.UpdateAdvertisementDto;
import com.example.task_codepred.entity.Advertisement;
import com.example.task_codepred.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Validated
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping
    public ResponseEntity<AdvertisementDto> addAdvertisement(@Valid @RequestBody CreateAdvertisementDto createDto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTresc(createDto.getTresc());
        
        Advertisement saved = advertisementService.add(advertisement);
        AdvertisementDto responseDto = convertToDto(saved);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementDto> updateAdvertisement(@PathVariable Long id, 
                                                              @Valid @RequestBody UpdateAdvertisementDto updateDto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTresc(updateDto.getTresc());
        advertisement.setIloscWyswietlen(updateDto.getIloscWyswietlen());
        
        Advertisement updated = advertisementService.update(id, advertisement);
        AdvertisementDto responseDto = convertToDto(updated);
        
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        advertisementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> getAdvertisement(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.getById(id);
        AdvertisementDto responseDto = convertToDto(advertisement);
        
        return ResponseEntity.ok(responseDto);
    }

    private AdvertisementDto convertToDto(Advertisement advertisement) {
        return new AdvertisementDto(
            advertisement.getId(),
            advertisement.getTresc(),
            advertisement.getDataDodania(),
            advertisement.getIloscWyswietlen()
        );
    }
}
