package com.example.demo.repository;

import com.example.demo.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM trainee WHERE training_group_id IS NOT NULL")
    List<Trainee> findAllGrouped();

    @Query(nativeQuery = true, value = "SELECT * FROM trainee WHERE training_group_id IS NULL")
    List<Trainee> findAllNotGrouped();
}
