package com.example.demo.util;

import com.example.demo.dto.TraineeDto;
import com.example.demo.entity.Trainee;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class ConvertorUtil {
    static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private ConvertorUtil() { }

    public static Trainee convertTraineeDto2Trainee(TraineeDto traineeDto) {
        return mapper.map(traineeDto, Trainee.class);
    }
}
