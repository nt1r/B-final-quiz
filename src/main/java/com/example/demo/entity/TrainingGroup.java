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
    List<Trainee> trainees;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "trainingGroup")
    List<Trainer> trainers;
}
