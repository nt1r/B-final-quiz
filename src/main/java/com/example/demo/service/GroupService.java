package com.example.demo.service;

import com.example.demo.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GroupService {
    public static List<TraineeDto> traineeDtoList = new ArrayList<>();
    public static List<String> groupNameList = new ArrayList<>();

    public static List<GroupDto> groupMemberResponse;

    private Integer autoIncreaseId = 1;
    private static final Integer GROUP_NUMBER = 6;
    private final List<String> initMemberNameList = Arrays.asList(
            "沈乐棋",
            "徐慧慧",
            "陈思聪",
            "王江林",
            "王登宇",
            "杨思雨",
            "江雨舟",
            "廖燊",
            "胡晓",
            "但杰",
            "盖迈达",
            "肖美琦",
            "黄云洁",
            "齐瑾浩",
            "刘亮亮",
            "肖逸凡",
            "王作文",
            "郭瑞凌",
            "李明豪",
            "党泽",
            "肖伊佐",
            "贠晨曦",
            "李康宁",
            "马庆",
            "商婕",
            "余榕",
            "谌哲",
            "董翔锐",
            "陈泰宇",
            "赵允齐",
            "张柯",
            "廖文强",
            "刘轲",
            "廖浚斌",
            "凌凤仪"
    );
    // file read, not hard code

    public GroupService() {
        initMemberList();
        initTeamName();
    }

    public static void initTeamName() {
        for (int i = 0; i < GROUP_NUMBER; ++i) {
            groupNameList.add("Team " + (i + 1));
        }
    }

    public void initMemberList() {
        traineeDtoList.clear();
        for (String memberName: initMemberNameList) {
            traineeDtoList.add(generateNewMember(memberName));
        }
    }

    private TraineeDto generateNewMember(String name) {
        return TraineeDto.builder()
                .id(autoIncreaseId++)
                .name(name)
                .build();
    }

    public List<TraineeDto> getAllTrainees() {
        return traineeDtoList;
    }

    public List<TraineeDto> addOneMember(AddNewMemberRequestDto newMemberRequestDto) {
        if (isAddNewMemberRequestDtoValid(newMemberRequestDto)) {
            traineeDtoList.add(generateNewMember(newMemberRequestDto.getName()));
        }
        return getAllTrainees();
    }

    private boolean isAddNewMemberRequestDtoValid(AddNewMemberRequestDto newMemberRequestDto) {
        return !newMemberRequestDto.getName().equals("");
    }

    public List<GroupDto> assignGroup() {
        List<TraineeDto> randomMemberList = generateRandomMemberList(traineeDtoList);
        List<Integer> memberCountList = generateMemberCountList(traineeDtoList.size(), GROUP_NUMBER);
        return assignGroupWithCountList(randomMemberList, memberCountList);
    }

    private List<GroupDto> assignGroupWithCountList(List<TraineeDto> randomMemberList, List<Integer> memberCountList) {
        /* set 'teamList' */
        List<GroupDto> groupDtoList = new ArrayList<>();
        int lastAssignedMemberIndex = 0;
        for (int teamIndex = 0; teamIndex < memberCountList.size(); ++teamIndex) {
            /* assign members */
            int assignCount = memberCountList.get(teamIndex);
            List<TraineeDto> memberList = new ArrayList<>();
            for (int memberIndex = 0; memberIndex < assignCount; ++memberIndex) {
                memberList.add(randomMemberList.get(memberIndex + lastAssignedMemberIndex));
            }
            lastAssignedMemberIndex += assignCount;
            /* assign members */

            GroupDto groupDto = GroupDto.builder()
                    .name(groupNameList.get(teamIndex))
                    .memberList(memberList)
                    .build();
            groupDtoList.add(groupDto);
        }
        /* set 'teamList' */

        return groupDtoList;
    }

    private List<Integer> generateMemberCountList(int size, Integer groupNumber) {
        List<Integer> countList = new ArrayList<>();

        int average = size / groupNumber;
        for (int i = 0; i < groupNumber; ++i) {
            countList.add(average);
        }

        int assignedCount = average * groupNumber;
        int teamIndex = 0;
        for (int count = assignedCount; count < size; ++count) {
            countList.set(teamIndex++, average + 1);
        }

        return countList;
    }

    private List<TraineeDto> generateRandomMemberList(List<TraineeDto> memberList) {
        // deep copy first
        List<TraineeDto> randomList = new ArrayList<>(memberList);

        int generatedCount = 0;
        Random random = new Random();

        while (generatedCount < randomList.size()) {
            // get one random number from 0 - size of 'memberList'
            int randomIndex = random.nextInt(randomList.size() - generatedCount);

            // set the last member with random index by exchanging
            exchangeWithTheLast(randomList, randomIndex);
            generatedCount++;
        }
        return randomList;
    }

    private void exchangeWithTheLast(List<TraineeDto> randomList, int randomIndex) {
        int lastIndex = randomList.size() - 1;
        TraineeDto cache = randomList.get(randomIndex);
        randomList.set(randomIndex, randomList.get(lastIndex));
        randomList.set(lastIndex, cache);
    }

    public ResponseEntity<String> renameTeam(RenameTeamRequestDto renameTeamRequestDto) {
        if (!isTeamIndexValid(renameTeamRequestDto)) {
            return ResponseEntity.status(409).body("invalid index " + renameTeamRequestDto.getIndex());
        }

        if (isTeamNewNameInvalid(renameTeamRequestDto)) {
            return ResponseEntity.badRequest().body("invalid name");
        }

        groupNameList.set(renameTeamRequestDto.getIndex(), renameTeamRequestDto.getNewName());

        return ResponseEntity.ok().body("success");
    }

    private boolean isTeamNewNameInvalid(RenameTeamRequestDto renameTeamRequestDto) {
        return groupNameList.get(renameTeamRequestDto.getIndex()).equals(renameTeamRequestDto.getNewName())
                || renameTeamRequestDto.getNewName().equals("");
    }

    private boolean isTeamIndexValid(RenameTeamRequestDto renameTeamRequestDto) {
        return renameTeamRequestDto.getIndex() > -1 && renameTeamRequestDto.getIndex() < GROUP_NUMBER;
    }

    public List<GroupDto> getAssignedGroup() {
        if (groupMemberResponse == null) {
            return new ArrayList<>();
        } else {
            // update team name
            int teamIndex= 0;
            for (GroupDto groupDto : groupMemberResponse) {
                groupDto.setName(groupNameList.get(teamIndex++));
            }
            return groupMemberResponse;
        }
    }
}
