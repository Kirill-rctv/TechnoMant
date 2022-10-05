package ru.kochnev.technomant.SpringBoot.SpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import ru.kochnev.technomant.SpringBoot.SpringBoot.ApplicationTest;
import ru.kochnev.technomant.SpringBoot.repositories.StatisticRepository;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class StatisticControllerTest extends ApplicationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StatisticRepository statisticRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        cleanAndMigrate();
    }

    @Test
    void getStatistic() throws Exception {

        Map<OffsetDateTime, Integer> map = new HashMap<>();
        map.put(OffsetDateTime.parse("2022-10-03T00:00Z"), 1);
        map.put(OffsetDateTime.parse("2022-10-02T00:00Z"), 0);
        map.put(OffsetDateTime.parse("2022-10-01T00:00Z"), 0);
        map.put(OffsetDateTime.parse("2022-10-04T00:00Z"), 1);
        map.put(OffsetDateTime.parse("2022-09-28T00:00Z"), 0);
        map.put(OffsetDateTime.parse("2022-09-30T00:00Z"), 0);
        map.put(OffsetDateTime.parse("2022-09-29T00:00Z"), 0);
        String expectedContent = objectMapper.writeValueAsString(map);

        this.mockMvc.perform(get("/statistic/")
                        .with(httpBasic("admin", "admin"))
                        .content("2022-10-04T13:09:00Z"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }
}