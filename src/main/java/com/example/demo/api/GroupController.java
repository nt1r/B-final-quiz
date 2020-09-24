package com.example.demo.api;

import com.example.demo.dto.GroupDto;
import com.example.demo.dto.RenameTeamRequestDto;
import com.example.demo.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/auto-grouping")
    public List<GroupDto> autoGrouping() {
        return groupService.autoGrouping();
    }

    @GetMapping
    public List<GroupDto> getGroups() {
        return groupService.getGroups();
    }

    @PatchMapping("/{group_id}")
    public void renameGroupById(@PathVariable("group_id") Long id, @RequestBody RenameTeamRequestDto renameTeamRequestDto) {
        groupService.renameGroupById(id, renameTeamRequestDto.getNewName());
    }
}
