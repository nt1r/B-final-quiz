package com.example.demo.service;

import com.example.demo.dto.GroupDto;
import com.example.demo.entity.Trainee;
import com.example.demo.entity.Trainer;
import com.example.demo.entity.TrainingGroup;
import com.example.demo.exception.GroupNameRepeatedException;
import com.example.demo.exception.GroupNotFoundException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import com.example.demo.util.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GroupService {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final GroupRepository groupRepository;

    private final int TRAINER_IN_EACH_GROUP = 2;

    public GroupService(TraineeRepository traineeRepository, TrainerRepository trainerRepository, GroupRepository groupRepository) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.groupRepository = groupRepository;
    }

    public List<GroupDto> autoGrouping() {
        // GTB: - 应该删除旧的分组或者设为逻辑删除
        List<Trainee> allTrainees = traineeRepository.findAll();
        List<Trainer> allTrainers = trainerRepository.findAll();

        // GTB: - 讲师数量不足时应该抛异常
        int groupCount = allTrainers.size() / TRAINER_IN_EACH_GROUP;
        Collections.shuffle(allTrainees);
        Collections.shuffle(allTrainers);

        List<TrainingGroup> groups = initGroupList(groupCount);

        assignTraineesIntoGroups(allTrainees, groups);
        assignTrainersIntoGroups(allTrainers, groups);

        List<TrainingGroup> groupsWithId = saveTrainingGroups(groups);

        resetLeftTrainers(allTrainers, groupCount);

        return ConvertUtil.convertTrainingGroupList2GroupDto(groupsWithId);
    }

    private void resetLeftTrainers(List<Trainer> allTrainers, int groupCount) {
        for (int trainerIndex = groupCount * TRAINER_IN_EACH_GROUP; trainerIndex < allTrainers.size(); ++trainerIndex) {
            Trainer trainer = allTrainers.get(trainerIndex);
            trainer.setTrainingGroup(null);
            trainerRepository.save(trainer);
        }
    }

    private List<TrainingGroup> saveTrainingGroups(List<TrainingGroup> groups) {
        List<TrainingGroup> groupsWithId = new ArrayList<>();
        for (TrainingGroup group: groups) {
            TrainingGroup groupWithId = groupRepository.save(group);
            for (Trainee trainee: groupWithId.getTrainees()) {
                trainee.setTrainingGroup(groupWithId);
                traineeRepository.save(trainee);
            }
            for (Trainer trainer: groupWithId.getTrainers()) {
                trainer.setTrainingGroup(groupWithId);
                trainerRepository.save(trainer);
            }
            groupsWithId.add(groupWithId);
        }
        return groupsWithId;
    }

    private List<TrainingGroup> initGroupList(int groupCount) {
        List<TrainingGroup> groups = new ArrayList<>();
        // GTB: - 可以使用Java8 Stream简化代码
        for (int groupIndex = 0; groupIndex < groupCount; ++groupIndex) {
            // GTB：- 长代码没有换行
            groups.add(TrainingGroup.builder().name((groupIndex + 1) + " 组").trainees(new ArrayList<>()).trainers(new ArrayList<>()).build());
        }
        return groups;
    }

    private void assignTraineesIntoGroups(List<Trainee> allTrainees, List<TrainingGroup> groups) {
        int groupIndex = 0;
        for (int traineeIndex = 0; traineeIndex < allTrainees.size(); ++traineeIndex) {
            TrainingGroup group = groups.get(groupIndex);
            group.getTrainees().add(allTrainees.get(traineeIndex));
            groupIndex = (groupIndex + 1) % groups.size();
        }
    }

    private void assignTrainersIntoGroups(List<Trainer> allTrainers, List<TrainingGroup> groups) {
        int groupIndex = 0;
        int assignedTrainerCount = TRAINER_IN_EACH_GROUP * groups.size();
        for (int trainerIndex = 0; trainerIndex < assignedTrainerCount; ++trainerIndex) {
            TrainingGroup group = groups.get(groupIndex);
            group.getTrainers().add(allTrainers.get(trainerIndex));
            groupIndex = (groupIndex + 1) % groups.size();
        }
    }

    public List<GroupDto> getGroups() {
        List<TrainingGroup> groups = groupRepository.findTopNById(trainerRepository.count() / TRAINER_IN_EACH_GROUP);
        return ConvertUtil.convertTrainingGroupList2GroupDto(groups);
    }

    public void renameGroupById(Long id, String newName) {
        if (!isGroupIdExist(id)) {
            throw new GroupNotFoundException(id);
        }
        // GTB: - Optional不能直接get
        TrainingGroup group = groupRepository.findById(id).get();
        // GTB：- 组名重复不是指与本组的组名重复，而是指与其他组的组名重复
        if (group.getName().equals(newName)) {
            throw new GroupNameRepeatedException();
        }
        group.setName(newName);
        groupRepository.save(group);
    }

    private boolean isGroupIdExist(Long id) {
        return groupRepository.findById(id).isPresent();
    }
}
