package com.example.demo.api;

import com.example.demo.dto.TraineeDto;
import com.example.demo.entity.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trainees")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainee addOneTrainee(@RequestBody @Valid TraineeDto traineeDto) {
        return traineeService.addOneTrainee(traineeDto);
    }

    @GetMapping
    public List<Trainee> getAllTrainees(@RequestParam("grouped") boolean isGrouped) {
        return traineeService.getAllTrainees(isGrouped);
    }
}
