package com.example.demo.repository;

import com.example.demo.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findAllByIsGrouped(boolean isGrouped);
}
