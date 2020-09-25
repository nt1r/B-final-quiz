package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "gtb_group")
public class TrainingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "trainingGroup")
    // GTB: - 集合类型字段一般在字段声明时就初始化为空集合
    List<Trainee> trainees;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "trainingGroup")
    List<Trainer> trainers;
}
