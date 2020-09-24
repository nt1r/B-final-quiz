package com.example.demo.repository;

import com.example.demo.entity.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<TrainingGroup, Long> {
}
