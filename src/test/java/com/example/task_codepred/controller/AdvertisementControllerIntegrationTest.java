package com.example.task_codepred.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.task_codepred.dto.CreateAdvertisementDto;
import com.example.task_codepred.dto.UpdateAdvertisementDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class AdvertisementControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void testFullCrudFlow() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        CreateAdvertisementDto createDto = new CreateAdvertisementDto();
        createDto.setTresc("Test ogłoszenie integracyjne");

        String createResponse = mockMvc.perform(post("/ads")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tresc").value("Test ogłoszenie integracyjne"))
                .andExpect(jsonPath("$.iloscWyswietlen").value(0))
                .andExpect(jsonPath("$.dataDodania").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long advertisementId = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(get("/ads/" + advertisementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(advertisementId))
                .andExpect(jsonPath("$.tresc").value("Test ogłoszenie integracyjne"))
                .andExpect(jsonPath("$.iloscWyswietlen").value(1))
                .andExpect(jsonPath("$.dataDodania").exists());

        mockMvc.perform(get("/ads/" + advertisementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iloscWyswietlen").value(2));

        UpdateAdvertisementDto updateDto = new UpdateAdvertisementDto();
        updateDto.setTresc("Zaktualizowane ogłoszenie integracyjne");
        updateDto.setIloscWyswietlen(5);

        mockMvc.perform(put("/ads/" + advertisementId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(advertisementId))
                .andExpect(jsonPath("$.tresc").value("Zaktualizowane ogłoszenie integracyjne"))
                .andExpect(jsonPath("$.iloscWyswietlen").value(2)); // licznik pozostaje bez zmian po update

        mockMvc.perform(get("/ads/" + advertisementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tresc").value("Zaktualizowane ogłoszenie integracyjne"))
                .andExpect(jsonPath("$.iloscWyswietlen").value(3)); // 2 + 1 po kolejnym get

        mockMvc.perform(delete("/ads/" + advertisementId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/ads/" + advertisementId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetNonExistentAdvertisement() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/ads/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateNonExistentAdvertisement() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        UpdateAdvertisementDto updateDto = new UpdateAdvertisementDto();
        updateDto.setTresc("Nieistniejące ogłoszenie");
        updateDto.setIloscWyswietlen(0);

        mockMvc.perform(put("/ads/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNonExistentAdvertisement() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(delete("/ads/999"))
                .andExpect(status().isNotFound());
    }
}
