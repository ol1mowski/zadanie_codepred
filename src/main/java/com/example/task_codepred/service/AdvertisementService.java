package com.example.task_codepred.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.task_codepred.entity.Advertisement;
import com.example.task_codepred.exception.AdvertisementNotFoundException;
import com.example.task_codepred.repository.AdvertisementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdvertisementService {

    private static final Logger logger = LoggerFactory.getLogger(AdvertisementService.class);
    private final AdvertisementRepository advertisementRepository;

    public Advertisement add(Advertisement advertisement) {
        logger.info("Adding new advertisement with content: {}", advertisement.getTresc());
        Advertisement saved = advertisementRepository.save(advertisement);
        logger.info("Advertisement added successfully with ID: {} at {}", saved.getId(), saved.getDataDodania());
        return saved;
    }

    public Advertisement update(Long id, Advertisement updated) {
        logger.info("Updating advertisement with ID: {}", id);
        Advertisement existing = getById(id);
        
        existing.setTresc(updated.getTresc());
        existing.setIloscWyswietlen(updated.getIloscWyswietlen());
        
        Advertisement saved = advertisementRepository.save(existing);
        logger.info("Advertisement updated successfully with ID: {} at {}", saved.getId(), saved.getDataDodania());
        return saved;
    }

    public void delete(Long id) {
        logger.info("Deleting advertisement with ID: {}", id);
        if (!advertisementRepository.existsById(id)) {
            logger.warn("Attempted to delete non-existent advertisement with ID: {}", id);
            throw new AdvertisementNotFoundException(id);
        }
        advertisementRepository.deleteById(id);
        logger.info("Advertisement deleted successfully with ID: {}", id);
    }

    public Advertisement getById(Long id) {
        logger.info("Retrieving advertisement with ID: {}", id);
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Advertisement not found with ID: {}", id);
                    return new AdvertisementNotFoundException(id);
                });
        
        int oldViewCount = advertisement.getIloscWyswietlen();
        advertisement.setIloscWyswietlen(oldViewCount + 1);
        advertisementRepository.save(advertisement);
        
        logger.info("Advertisement retrieved successfully with ID: {}, view count incremented from {} to {}", 
                   id, oldViewCount, advertisement.getIloscWyswietlen());
        return advertisement;
    }
}
