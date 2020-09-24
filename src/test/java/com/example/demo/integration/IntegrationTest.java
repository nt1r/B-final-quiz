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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class IntegrationTest {
    private final String addOneTraineeUrl = "/trainees";
    private final String getAllTraineesUrl = "/trainees?grouped=%s";
    private final String deleteOneTraineeUrl = "/trainees/%d";

    private final String addOneTrainerUrl = "/trainers";
    private final String getAllTrainersUrl = "/trainers?grouped=%s";
    private final String deleteOneTrainerUrl = "/trainers/%d";

    private final String autoGroupingUrl = "/groups/auto-grouping";

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
                        .andExpect(status().isOk());
            }

            @Test
            void should_delete_trainee_by_id() throws Exception {
                mockMvc.perform(delete(String.format(deleteOneTraineeUrl, 1L)).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        class SadPaths {
            @Test
            void should_return_bad_request_when_name_is_empty() throws Exception {
                sampleTraineeDto.setName("");
                addSampleTrainee()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(1)))
                        .andExpect(jsonPath("$.details.name", is("姓名字段不能为空")));
            }

            @Test
            void should_return_bad_request_when_office_is_empty() throws Exception {
                sampleTraineeDto.setOffice("");
                addSampleTrainee()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(1)))
                        .andExpect(jsonPath("$.details.office", is("办公室字段不能为空")));
            }

            @Test
            void should_return_bad_request_when_email_is_empty() throws Exception {
                sampleTraineeDto.setEmail("");
                addSampleTrainee()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(1)))
                        .andExpect(jsonPath("$.details.email", is("邮箱字段不能为空")));
            }

            @Test
            void should_return_bad_request_when_email_is_invalid() throws Exception {
                sampleTraineeDto.setEmail("qweqwe");
                addSampleTrainee()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(1)))
                        .andExpect(jsonPath("$.details.email", is("邮箱格式不正确")));
            }

            @Test
            void should_return_bad_request_when_github_is_empty() throws Exception {
                sampleTraineeDto.setGithub("");
                addSampleTrainee()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(1)))
                        .andExpect(jsonPath("$.details.github", is("github字段不能为空")));
            }

            @Test
            void should_return_bad_request_when_zoom_is_empty() throws Exception {
                sampleTraineeDto.setZoomId("");
                addSampleTrainee()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(1)))
                        .andExpect(jsonPath("$.details.zoomId", is("zoom字段不能为空")));
            }

            @Test
            void should_return_bad_request_when_multiple_fields_is_invalid() throws Exception {
                sampleTraineeDto.setName("");
                sampleTraineeDto.setOffice("");
                sampleTraineeDto.setEmail("");
                sampleTraineeDto.setGithub("");
                sampleTraineeDto.setZoomId("");
                addSampleTrainee()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(5)))
                        .andExpect(jsonPath("$.details.name", is("姓名字段不能为空")))
                        .andExpect(jsonPath("$.details.office", is("办公室字段不能为空")))
                        .andExpect(jsonPath("$.details.email", is("邮箱字段不能为空")))
                        .andExpect(jsonPath("$.details.github", is("github字段不能为空")))
                        .andExpect(jsonPath("$.details.zoomId", is("zoom字段不能为空")));
            }

            @Test
            void should_return_not_found_when_id_not_exist() throws Exception {
                mockMvc.perform(delete(String.format(deleteOneTraineeUrl, 100L)).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.message", is("Trainee ID 100 Not Found")));
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
                        .andExpect(status().isOk());
            }

            @Test
            void should_delete_trainer_by_id() throws Exception {
                mockMvc.perform(delete(String.format(deleteOneTrainerUrl, 1L)).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        class SadPaths {
            @Test
            void should_return_bad_request_when_name_is_empty() throws Exception {
                sampleTrainerDto.setName("");
                addSampleTrainer()
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$", hasKey("message")))
                        .andExpect(jsonPath("$.message", is("Invalid values.")))
                        .andExpect(jsonPath("$", hasKey("details")))
                        .andExpect(jsonPath("$.details", aMapWithSize(1)))
                        .andExpect(jsonPath("$.details.name", is("姓名字段不能为空")));
            }

            @Test
            void should_return_not_found_when_id_not_exist() throws Exception {
                mockMvc.perform(delete(String.format(deleteOneTrainerUrl, 100L)).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.message", is("Trainer ID 100 Not Found")));
            }
        }
    }

    @Nested
    class GroupTest {
        @Nested
        class HappyPaths {
            @Test
            void should_auto_grouping() throws Exception {
                mockMvc.perform(post(autoGroupingUrl).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(4)));
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
