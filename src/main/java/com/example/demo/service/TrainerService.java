package com.example.demo.service;

import com.example.demo.dto.TrainerDto;
import com.example.demo.entity.Trainer;
import com.example.demo.exception.TrainerNotFoundException;
import com.example.demo.repository.TrainerRepository;
import com.example.demo.util.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    private final List<String> defaultTrainerNameList = Arrays.asList(
            "桂溪京",
            "张钊",
            "朱玉前",
            "彭梦秋",
            "董志刚",
            "张巍",
            "杜鹃",
            "王晓峰",
            "王雅君"
    );

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
        initTrainers();
    }

    private void initTrainers() {
        // GTB: - 可以使用Java8 Stream简化代码
        for (String trainerName : defaultTrainerNameList) {
            TrainerDto trainerDto = new TrainerDto(trainerName);
            Trainer trainer = ConvertUtil.convertTrainerDto2Trainer(trainerDto);
            trainerRepository.save(trainer);
        }
    }

    public Trainer addOneTrainer(TrainerDto trainerDto) {
        Trainer trainer = ConvertUtil.convertTrainerDto2Trainer(trainerDto);
        return trainerRepository.save(trainer);
    }

    public List<Trainer> getAllTrainers(boolean isGrouped) {
        return isGrouped ? trainerRepository.findAllGrouped() : trainerRepository.findAllNotGrouped();
    }

    public void deleteOneTrainer(Long id) {
        if (!isTrainerExists(id)) {
            throw new TrainerNotFoundException(id);
        }
        trainerRepository.deleteById(id);
    }

    private boolean isTrainerExists(Long id) {
        return trainerRepository.findById(id).isPresent();
    }
}
