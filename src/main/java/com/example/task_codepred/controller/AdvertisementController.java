package com.example.task_codepred.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_codepred.dto.AdvertisementDto;
import com.example.task_codepred.dto.CreateAdvertisementDto;
import com.example.task_codepred.dto.UpdateAdvertisementDto;
import com.example.task_codepred.entity.Advertisement;
import com.example.task_codepred.service.AdvertisementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Validated
@Tag(name = "Advertisement Management", description = "API endpoints for managing advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    @Operation(summary = "Add new advertisement", description = "Creates a new advertisement with automatic date setting and view count initialization")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Advertisement created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<AdvertisementDto> addAdvertisement(@Valid @RequestBody CreateAdvertisementDto createDto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTresc(createDto.getTresc());

        Advertisement saved = advertisementService.add(advertisement);
        AdvertisementDto responseDto = convertToDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update advertisement", description = "Updates an existing advertisement by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Advertisement updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Advertisement not found")
    })
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
    @Operation(summary = "Delete advertisement", description = "Deletes an advertisement by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Advertisement deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Advertisement not found")
    })
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        advertisementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get advertisement by ID", description = "Retrieves an advertisement by ID and increments view count")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Advertisement retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Advertisement not found")
    })
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
