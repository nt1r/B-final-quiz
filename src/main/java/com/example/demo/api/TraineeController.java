package com.example.demo.api;

import com.example.demo.dto.AddNewMemberRequestDto;
import com.example.demo.dto.TraineeDto;
import com.example.demo.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TraineeController {
    private final GroupService groupService;

    public TraineeController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group-api/init-list")
    public List<TraineeDto> getAllTrainees() {
        return groupService.getAllTrainees();
    }

    @PostMapping("/group-api/member")
    public List<TraineeDto> addOneMember(@RequestBody AddNewMemberRequestDto newMemberRequestDto) {
        return groupService.addOneMember(newMemberRequestDto);
    }
}
