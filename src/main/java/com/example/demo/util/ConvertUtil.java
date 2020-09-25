package com.example.demo.util;

import com.example.demo.dto.GroupDto;
import com.example.demo.dto.TraineeDto;
import com.example.demo.dto.TrainerDto;
import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import com.example.demo.entity.TrainingGroup;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {
    static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private ConvertUtil() { }

    public static Trainee convertTraineeDto2Trainee(TraineeDto traineeDto) {
        return convert(traineeDto, Trainee.class);
    }

    public static Trainer convertTrainerDto2Trainer(TrainerDto trainerDto) {
        return convert(trainerDto, Trainer.class);
    }

    public static List<GroupDto> convertTrainingGroupList2GroupDto(List<TrainingGroup> groups) {
        List<GroupDto> groupDtoList = new ArrayList<>();
        // GTB: - 可以使用Java8 Stream简化代码
        for (TrainingGroup group: groups) {
            groupDtoList.add(convert(group, GroupDto.class));
        }
        return groupDtoList;
    }

    private static <T> T convert(Object object, Class<T> targetClass) {
        return mapper.map(object, targetClass);
    }
}
