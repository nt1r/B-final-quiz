package com.example.demo.api;

import com.example.demo.dto.TrainerDto;
import com.example.demo.entity.Trainer;
import com.example.demo.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainer addOneTrainer(@RequestBody @Valid TrainerDto trainerDto) {
        return trainerService.addOneTrainer(trainerDto);
    }

    @GetMapping
    public List<Trainer> getAllTrainers(@RequestParam("grouped") boolean isGrouped) {
        return trainerService.getAllTrainers(isGrouped);
    }

    @DeleteMapping("/{trainer_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneTrainee(@PathVariable("trainer_id") Long id) {
        trainerService.deleteOneTrainer(id);
    }
}
