package com.example.demo.service;

import com.example.demo.dto.TrainerDto;
import com.example.demo.entity.Trainer;
import com.example.demo.repository.TrainerRepository;
import com.example.demo.util.ConvertUtil;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer addOneTrainer(TrainerDto trainerDto) {
        Trainer trainer = ConvertUtil.convertTrainerDto2Trainer(trainerDto);
        return trainerRepository.save(trainer);
    }
}
