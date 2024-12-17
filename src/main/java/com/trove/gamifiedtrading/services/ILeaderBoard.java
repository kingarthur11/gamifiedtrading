package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.dto.ConvertUserEntity;
import com.trove.gamifiedtrading.entity.UserEntity;

import java.util.List;

public interface ILeaderBoard {
        ApiResponse<List<ConvertUserEntity>> getUsersRanking();
        void getUserRanking(Long userId);
}
