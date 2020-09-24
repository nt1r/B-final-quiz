package com.example.demo.dto;

import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {
    Long id;
    String name;
    List<Trainee> trainees;
    List<Trainer> trainers;
}
