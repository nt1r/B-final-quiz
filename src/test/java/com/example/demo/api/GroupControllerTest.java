//package com.example.demo.api;
//
//import com.example.demo.dto.RenameTeamRequestDto;
//import com.example.demo.service.GroupService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class GroupControllerTest {
//    private final String assignGroupUrl = "/group/assignment";
//    private final String renameTeamUrl = "/group-api/rename-team";
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    GroupService groupService;
//
//    @BeforeEach
//    void setUp() {
//        groupService = new GroupService();
//        GroupService.initTeamName();
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    public void shouldGetRandomMemberList() throws Exception {
//        mockMvc.perform(post(assignGroupUrl).accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8"))
//                .andExpect(jsonPath("$", hasSize(6)))
//                .andExpect(jsonPath("$[0].memberList", hasSize(6)))
//                .andExpect(jsonPath("$[1].memberList", hasSize(6)))
//                .andExpect(jsonPath("$[2].memberList", hasSize(6)))
//                .andExpect(jsonPath("$[3].memberList", hasSize(6)))
//                .andExpect(jsonPath("$[4].memberList", hasSize(6)))
//                .andExpect(jsonPath("$[5].memberList", hasSize(5)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldRenameTeamSuccess() throws Exception {
//        RenameTeamRequestDto renameTeamRequestDto = RenameTeamRequestDto.builder()
//                .index(3)
//                .newName("To be No. 4")
//                .build();
//
//        mockMvc.perform(post(renameTeamUrl).accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(renameTeamRequestDto)))
//                .andExpect(jsonPath("$", is("success")))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldRenameTeamFailWhenNewNameIsEmpty() throws Exception {
//        RenameTeamRequestDto renameTeamRequestDto = RenameTeamRequestDto.builder()
//                .index(3)
//                .newName("")
//                .build();
//
//        mockMvc.perform(post(renameTeamUrl).accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(renameTeamRequestDto)))
//                .andExpect(jsonPath("$", is("invalid name")))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void shouldRenameTeamFailWhenNewNameIsRepeated() throws Exception {
//        RenameTeamRequestDto renameTeamRequestDto = RenameTeamRequestDto.builder()
//                .index(4)
//                .newName("Team 5")
//                .build();
//
//        mockMvc.perform(post(renameTeamUrl).accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(renameTeamRequestDto)))
//                .andExpect(jsonPath("$", is("invalid name")))
//                .andExpect(status().isBadRequest());
//    }
//}