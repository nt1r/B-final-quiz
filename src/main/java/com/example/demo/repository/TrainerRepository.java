package com.example.demo.repository;

import com.example.demo.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM trainer WHERE training_group_id IS NOT NULL")
    List<Trainer> findAllGrouped();

    @Query(nativeQuery = true, value = "SELECT * FROM trainer WHERE training_group_id IS NULL")
    List<Trainer> findAllNotGrouped();
}
