package com.example.demo.util;

import com.example.demo.dto.TraineeDto;
import com.example.demo.dto.TrainerDto;
import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class ConvertUtil {
    static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private ConvertUtil() { }

    public static Trainee convertTraineeDto2Trainee(TraineeDto traineeDto) {
        return convert(traineeDto, Trainee.class);
    }

    public static Trainer convertTrainerDto2Trainer(TrainerDto trainerDto) {
        return convert(trainerDto, Trainer.class);
    }

    private static <T> T convert(Object object, Class<T> targetClass) {
        return mapper.map(object, targetClass);
    }
}
