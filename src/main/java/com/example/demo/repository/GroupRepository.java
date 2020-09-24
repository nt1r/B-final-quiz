package com.example.demo.repository;

import com.example.demo.entity.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<TrainingGroup, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM gtb_group ORDER BY id DESC LIMIT :topCount")
    List<TrainingGroup> findTopNById(@Param("topCount") long topCount);
}
