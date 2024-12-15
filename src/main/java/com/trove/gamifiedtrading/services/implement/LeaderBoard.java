package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.repository.UserRepository;
import com.trove.gamifiedtrading.services.ILeaderBoard;

import java.util.List;

public class LeaderBoard implements ILeaderBoard {

    private final UserRepository userRepository;

    LeaderBoard(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUsersRanking() {
        List <UserEntity> employeesSortedList1 = userRepository.findAll().stream()
            .sorted((o1, o2) -> (int)(o1.getGemCount() - o2.getGemCount())).toList();
    }

    @Override
    public void getUserRanking(Long userId) {

    }
}
