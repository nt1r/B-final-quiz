package com.example.demo.service;

import com.example.demo.dto.TraineeDto;
import com.example.demo.entity.Trainee;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.util.ConvertUtil;
import org.springframework.stereotype.Service;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public Trainee addOneTrainee(TraineeDto traineeDto) {
        Trainee trainee = ConvertUtil.convertTraineeDto2Trainee(traineeDto);
        return traineeRepository.save(trainee);
    }
}
