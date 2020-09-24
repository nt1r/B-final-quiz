package com.example.demo.service;

import com.example.demo.dto.AddNewMemberRequestDto;
import com.example.demo.dto.GroupDto;
import com.example.demo.dto.TraineeDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GroupAssignServiceTest {
    @Autowired
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        groupService = new GroupService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldInitMemberListSuccess() {
        // Given

        // When
        List<TraineeDto> allMemberListList = groupService.getAllTrainees();

        // Then
        assertEquals(35, allMemberListList.size());

        assertEquals("沈乐棋", allMemberListList.get(0).getName());
        assertEquals("王登宇", allMemberListList.get(4).getName());
        assertEquals("廖燊", allMemberListList.get(7).getName());
        assertEquals("肖美琦", allMemberListList.get(11).getName());
        assertEquals("齐瑾浩", allMemberListList.get(13).getName());
        assertEquals("党泽", allMemberListList.get(19).getName());
        assertEquals("马庆", allMemberListList.get(23).getName());
        assertEquals("赵允齐", allMemberListList.get(29).getName());
    }

    @Test
    public void shouldAddNewMemberSuccess() {
        // Given
        AddNewMemberRequestDto addNewMemberRequestDto = AddNewMemberRequestDto.builder()
                .name("张三")
                .build();

        // When
        List<TraineeDto> allMemberListList = groupService.addOneMember(addNewMemberRequestDto);

        // Then
        assertEquals(36, allMemberListList.size());

        assertEquals("沈乐棋", allMemberListList.get(0).getName());
        assertEquals("王登宇", allMemberListList.get(4).getName());
        assertEquals("廖燊", allMemberListList.get(7).getName());
        assertEquals("肖美琦", allMemberListList.get(11).getName());
        assertEquals("齐瑾浩", allMemberListList.get(13).getName());
        assertEquals("党泽", allMemberListList.get(19).getName());
        assertEquals("马庆", allMemberListList.get(23).getName());
        assertEquals("赵允齐", allMemberListList.get(29).getName());
        assertEquals("张三", allMemberListList.get(35).getName());
    }

    @Test
    public void shouldAddNewMemberFailedWhenNameIsEmpty() {
        // Given
        AddNewMemberRequestDto addNewMemberRequestDto = AddNewMemberRequestDto.builder()
                .name("")
                .build();

        // When
        List<TraineeDto> allMemberListList = groupService.addOneMember(addNewMemberRequestDto);

        // Then
        assertEquals(35, allMemberListList.size());

        assertEquals("沈乐棋", allMemberListList.get(0).getName());
        assertEquals("王登宇", allMemberListList.get(4).getName());
        assertEquals("廖燊", allMemberListList.get(7).getName());
        assertEquals("肖美琦", allMemberListList.get(11).getName());
        assertEquals("齐瑾浩", allMemberListList.get(13).getName());
        assertEquals("党泽", allMemberListList.get(19).getName());
        assertEquals("马庆", allMemberListList.get(23).getName());
        assertEquals("赵允齐", allMemberListList.get(29).getName());
    }

    @Test
    public void shouldAssignGroupSuccess() {
        // Given

        // When
        List<GroupDto> groupMemberResponseDto = groupService.assignGroup();

        // Then
        assertEquals(6, groupMemberResponseDto.size());

        assertEquals(6, groupMemberResponseDto.get(0).getMemberList().size());
        assertEquals(6, groupMemberResponseDto.get(1).getMemberList().size());
        assertEquals(6, groupMemberResponseDto.get(2).getMemberList().size());
        assertEquals(6, groupMemberResponseDto.get(3).getMemberList().size());
        assertEquals(6, groupMemberResponseDto.get(4).getMemberList().size());
        assertEquals(5, groupMemberResponseDto.get(5).getMemberList().size());
    }
}