package com.example.task_codepred.service;

import com.example.task_codepred.entity.Advertisement;
import com.example.task_codepred.exception.AdvertisementNotFoundException;
import com.example.task_codepred.repository.AdvertisementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private AdvertisementService advertisementService;

    private Advertisement testAdvertisement;
    private Advertisement savedAdvertisement;

    @BeforeEach
    void setUp() {
        testAdvertisement = new Advertisement();
        testAdvertisement.setTresc("Test advertisement content");
        testAdvertisement.setDataDodania(LocalDateTime.now());
        testAdvertisement.setIloscWyswietlen(0);

        savedAdvertisement = new Advertisement();
        savedAdvertisement.setId(1L);
        savedAdvertisement.setTresc("Test advertisement content");
        savedAdvertisement.setDataDodania(LocalDateTime.now());
        savedAdvertisement.setIloscWyswietlen(0);
    }

    @Test
    void add_ShouldSaveAndReturnAdvertisement() {
        // Given
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(savedAdvertisement);

        // When
        Advertisement result = advertisementService.add(testAdvertisement);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test advertisement content", result.getTresc());
        verify(advertisementRepository).save(any(Advertisement.class));
    }

    @Test
    void update_ShouldUpdateExistingAdvertisement() {
        // Given
        Long id = 1L;
        Advertisement updatedAdvertisement = new Advertisement();
        updatedAdvertisement.setTresc("Updated content");
        updatedAdvertisement.setIloscWyswietlen(5);

        when(advertisementRepository.findById(id)).thenReturn(Optional.of(savedAdvertisement));
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(savedAdvertisement);

        // When
        Advertisement result = advertisementService.update(id, updatedAdvertisement);

        // Then
        assertNotNull(result);
        verify(advertisementRepository).findById(id);
        verify(advertisementRepository, times(2)).save(any(Advertisement.class));
    }

    @Test
    void update_ShouldThrowException_WhenAdvertisementNotFound() {
        // Given
        Long id = 999L;
        Advertisement updatedAdvertisement = new Advertisement();
        updatedAdvertisement.setTresc("Updated content");

        when(advertisementRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(AdvertisementNotFoundException.class, () -> {
            advertisementService.update(id, updatedAdvertisement);
        });
        verify(advertisementRepository).findById(id);
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void delete_ShouldDeleteExistingAdvertisement() {
        // Given
        Long id = 1L;
        when(advertisementRepository.existsById(id)).thenReturn(true);
        doNothing().when(advertisementRepository).deleteById(id);

        // When
        advertisementService.delete(id);

        // Then
        verify(advertisementRepository).existsById(id);
        verify(advertisementRepository).deleteById(id);
    }

    @Test
    void delete_ShouldThrowException_WhenAdvertisementNotFound() {
        // Given
        Long id = 999L;
        when(advertisementRepository.existsById(id)).thenReturn(false);

        // When & Then
        assertThrows(AdvertisementNotFoundException.class, () -> {
            advertisementService.delete(id);
        });
        verify(advertisementRepository).existsById(id);
        verify(advertisementRepository, never()).deleteById(any());
    }

    @Test
    void getById_ShouldReturnAdvertisementAndIncrementViewCount() {
        // Given
        Long id = 1L;
        when(advertisementRepository.findById(id)).thenReturn(Optional.of(savedAdvertisement));
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(savedAdvertisement);

        // When
        Advertisement result = advertisementService.getById(id);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(advertisementRepository).findById(id);
        verify(advertisementRepository).save(any(Advertisement.class));
    }

    @Test
    void getById_ShouldIncrementIloscWyswietlen() {
        // Given
        Long id = 1L;
        Advertisement advertisement = new Advertisement();
        advertisement.setId(1L);
        advertisement.setTresc("Test content");
        advertisement.setIloscWyswietlen(5);

        when(advertisementRepository.findById(id)).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(advertisement);

        // When
        advertisementService.getById(id);

        // Then
        verify(advertisementRepository).save(argThat(adv -> adv.getIloscWyswietlen() == 6));
    }

    @Test
    void getById_ShouldThrowException_WhenAdvertisementNotFound() {
        // Given
        Long id = 999L;
        when(advertisementRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(AdvertisementNotFoundException.class, () -> {
            advertisementService.getById(id);
        });
        verify(advertisementRepository).findById(id);
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }
}
