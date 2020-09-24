package com.example.demo.service;

import com.example.demo.dto.TraineeDto;
import com.example.demo.entity.Trainee;
import com.example.demo.exception.TraineeNotFoundException;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.util.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;

    private final List<String> defaultTraineeNameList = Arrays.asList(
            "沈乐棋",
            "徐慧慧",
            "陈思聪",
            "王江林",
            "王登宇",
            "杨思雨",
            "江雨舟",
            "廖燊",
            "胡晓",
            "但杰",
            "盖迈达",
            "肖美琦",
            "黄云洁",
            "齐瑾浩",
            "刘亮亮",
            "肖逸凡",
            "王作文",
            "郭瑞凌",
            "李明豪",
            "党泽",
            "肖伊佐",
            "贠晨曦",
            "李康宁",
            "马庆",
            "商婕",
            "余榕",
            "谌哲",
            "董翔锐",
            "陈泰宇",
            "赵允齐",
            "张柯",
            "廖文强",
            "刘轲",
            "廖浚斌",
            "凌凤仪"
    );

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
        initTrainees();
    }

    private void initTrainees() {
        for (String traineeName : defaultTraineeNameList) {
            TraineeDto traineeDto = new TraineeDto(
                    traineeName,
                    "某城市",
                    traineeName + "@thoughtworks.com",
                    "https://github.com/" + traineeName,
                    "12345678"
            );
            Trainee trainee = ConvertUtil.convertTraineeDto2Trainee(traineeDto);
            traineeRepository.save(trainee);
        }
    }

    public Trainee addOneTrainee(TraineeDto traineeDto) {
        Trainee trainee = ConvertUtil.convertTraineeDto2Trainee(traineeDto);
        System.out.println(trainee);
        return traineeRepository.save(trainee);
    }

    public List<Trainee> getAllTrainees(boolean isGrouped) {
        return isGrouped ? traineeRepository.findAllGrouped() : traineeRepository.findAllNotGrouped();
    }

    public void deleteOneTrainee(Long id) {
        if (!isTraineeExists(id)) {
            throw new TraineeNotFoundException(id);
        }
        traineeRepository.deleteById(id);
    }

    private boolean isTraineeExists(Long id) {
        return traineeRepository.findById(id).isPresent();
    }
}
