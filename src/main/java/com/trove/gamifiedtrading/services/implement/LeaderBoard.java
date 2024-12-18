package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.dto.ConvertUserEntity;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.repository.UserRepository;
import com.trove.gamifiedtrading.services.ILeaderBoard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderBoard implements ILeaderBoard {

    private final UserRepository userRepository;

    LeaderBoard(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse<List<ConvertUserEntity>> getUsersRanking() {
        var response = new ApiResponse<List<ConvertUserEntity>>();

        List<ConvertUserEntity> leaderBoard = userRepository.findAll().stream()
            .sorted((o1, o2) -> (int)(o1.getGemCount() - o2.getGemCount()))
            .map(LeaderBoard::convertUserEntity)
            .toList();

        response.setStatus("success");
        response.setMessage("LeaderBoard board retrieved successfully.");
        response.setCode(200);
        response.setResult(leaderBoard);

        return response;
    }

    @Override
    public void getUserRanking(Long userId) {

    }

    public static ConvertUserEntity convertUserEntity(UserEntity userEntity) {
        Long rank = userEntity.getId();
        return new ConvertUserEntity(
           userEntity.getUsername(),
           rank,
           userEntity.getGemCount()
        );
    }
}
