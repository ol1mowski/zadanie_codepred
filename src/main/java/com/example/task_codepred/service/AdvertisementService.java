package com.example.task_codepred.service;

import com.example.task_codepred.entity.Advertisement;
import com.example.task_codepred.exception.AdvertisementNotFoundException;
import com.example.task_codepred.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public Advertisement add(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public Advertisement update(Long id, Advertisement updated) {
        Advertisement existing = getById(id);
        
        existing.setTresc(updated.getTresc());
        existing.setIloscWyswietlen(updated.getIloscWyswietlen());
        
        return advertisementRepository.save(existing);
    }

    public void delete(Long id) {
        if (!advertisementRepository.existsById(id)) {
            throw new AdvertisementNotFoundException(id);
        }
        advertisementRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Advertisement getById(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException(id));
        
        advertisement.setIloscWyswietlen(advertisement.getIloscWyswietlen() + 1);
        advertisementRepository.save(advertisement);
        
        return advertisement;
    }
}
