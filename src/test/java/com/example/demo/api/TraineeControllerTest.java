package com.example.demo.api;

import com.example.demo.dto.AddNewMemberRequestDto;
import com.example.demo.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TraineeControllerTest {
    private final String getAllMembersUrl = "/group-api/init-list";
    private final String addOneMemberUrl = "/group-api/member";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    GroupService groupService;

    @BeforeEach
    void setUp() {
        groupService = new GroupService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldGetAllMembersSuccess() throws Exception {
        mockMvc.perform(get(getAllMembersUrl).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(35)))
                .andExpect(jsonPath("$[0].name", is("沈乐棋")))
                .andExpect(jsonPath("$[4].name", is("王登宇")))
                .andExpect(jsonPath("$[7].name", is("廖燊")))
                .andExpect(jsonPath("$[11].name", is("肖美琦")))
                .andExpect(jsonPath("$[13].name", is("齐瑾浩")))
                .andExpect(jsonPath("$[19].name", is("党泽")))
                .andExpect(jsonPath("$[23].name", is("马庆")))
                .andExpect(jsonPath("$[29].name", is("赵允齐")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddNewMemberSuccess() throws Exception {
        AddNewMemberRequestDto addNewMemberRequestDto = AddNewMemberRequestDto.builder().name("张三").build();

        mockMvc.perform(post(addOneMemberUrl).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addNewMemberRequestDto)))
                .andExpect(jsonPath("$", hasSize(36)))
                .andExpect(jsonPath("$[0].name", is("沈乐棋")))
                .andExpect(jsonPath("$[4].name", is("王登宇")))
                .andExpect(jsonPath("$[7].name", is("廖燊")))
                .andExpect(jsonPath("$[11].name", is("肖美琦")))
                .andExpect(jsonPath("$[13].name", is("齐瑾浩")))
                .andExpect(jsonPath("$[19].name", is("党泽")))
                .andExpect(jsonPath("$[23].name", is("马庆")))
                .andExpect(jsonPath("$[29].name", is("赵允齐")))
                .andExpect(jsonPath("$[35].name", is("张三")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddNewMemberFailedWhenNameIsEmpty() throws Exception {
        AddNewMemberRequestDto addNewMemberRequestDto = AddNewMemberRequestDto.builder().name("").build();

        mockMvc.perform(post(addOneMemberUrl).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addNewMemberRequestDto)))
                .andExpect(jsonPath("$", hasSize(35)))
                .andExpect(jsonPath("$[0].name", is("沈乐棋")))
                .andExpect(jsonPath("$[4].name", is("王登宇")))
                .andExpect(jsonPath("$[7].name", is("廖燊")))
                .andExpect(jsonPath("$[11].name", is("肖美琦")))
                .andExpect(jsonPath("$[13].name", is("齐瑾浩")))
                .andExpect(jsonPath("$[19].name", is("党泽")))
                .andExpect(jsonPath("$[23].name", is("马庆")))
                .andExpect(jsonPath("$[29].name", is("赵允齐")))
                .andExpect(status().isOk());
    }
}
