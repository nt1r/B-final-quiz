//package com.example.demo.api;
//
//import com.example.demo.dto.GroupDto;
//import com.example.demo.dto.RenameTeamRequestDto;
//import com.example.demo.service.GroupService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin
//public class GroupController {
//    private final GroupService groupService;
//
//    public GroupController(GroupService groupService) {
//        this.groupService = groupService;
//    }
//
//    @PostMapping("/group/assignment")
//    public List<GroupDto> assignGroup() {
//        return groupService.assignGroup();
//    }
//
//    @GetMapping("/group-api/cached-assign")
//    public List<GroupDto> getAssignedGroup() {
//        return groupService.getAssignedGroup();
//    }
//
//    @PostMapping("/group-api/rename-team")
//    public ResponseEntity<String> renameTeam(@RequestBody RenameTeamRequestDto renameTeamRequestDto) {
//        return groupService.renameTeam(renameTeamRequestDto);
//    }
//}
