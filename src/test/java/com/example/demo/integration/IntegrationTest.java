package com.example.demo.integration;

import com.example.demo.dto.TraineeDto;
import com.example.demo.dto.TrainerDto;
import com.example.demo.service.TraineeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class IntegrationTest {
    private final String addOneTraineeUrl = "/trainees";
    private final String addOneTrainerUrl = "/trainers";
    private final String getAllTraineesUrl = "/trainees?grouped=%s";
    private final String getAllTrainersUrl = "/trainers?grouped=%s";

    private TraineeDto sampleTraineeDto;
    private TrainerDto sampleTrainerDto;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TraineeService traineeService;

    @Autowired
    private JacksonTester<TraineeDto> traineeDtoJacksonTester;
    @Autowired
    private JacksonTester<TrainerDto> trainerDtoJacksonTester;

    @BeforeEach
    void setUp() {
        sampleTraineeDto = new TraineeDto("张三", "武汉", "san.zhang@thoughtworks.com", "https://github.com/zhangsan", "12345678");
        sampleTrainerDto = new TrainerDto("张三");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loadContext() {
    }

    @Nested
    class TraineeTest {
        @Nested
        class HappyPaths {
            @Test
            void should_create_new_trainee() throws Exception {
                addSampleTrainee()
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$", hasKey("id")))
                        .andExpect(jsonPath("$.name", is("张三")))
                        .andExpect(jsonPath("$.office", is("武汉")))
                        .andExpect(jsonPath("$.email", is("san.zhang@thoughtworks.com")))
                        .andExpect(jsonPath("$.github", is("https://github.com/zhangsan")))
                        .andExpect(jsonPath("$.zoomId", is("12345678")));
            }

            @Test
            void should_get_all_trainees_not_grouped() throws Exception {
                mockMvc.perform(get(String.format(getAllTraineesUrl, "false")).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(35)));
            }
        }
    }

    @Nested
    class TrainerTest {
        @Nested
        class HappyPaths {
            @Test
            void should_create_new_trainer() throws Exception {
                addSampleTrainer()
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$", hasKey("id")))
                        .andExpect(jsonPath("$.name", is("张三")));
            }

            @Test
            void should_get_all_trainers() throws Exception {
                mockMvc.perform(get(String.format(getAllTrainersUrl, "false")).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(9)));
            }
        }
    }

    private ResultActions addSampleTrainee() throws Exception {
        return mockMvc.perform(post(addOneTraineeUrl).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(traineeDtoJacksonTester.write(sampleTraineeDto).getJson()));
    }

    private ResultActions addSampleTrainer() throws Exception {
        return mockMvc.perform(post(addOneTrainerUrl).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(trainerDtoJacksonTester.write(sampleTrainerDto).getJson()));
    }
}
