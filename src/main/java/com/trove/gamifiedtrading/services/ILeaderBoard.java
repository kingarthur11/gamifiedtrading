package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.entity.PortfolioEntity;

public interface ILeaderBoard {
        void getUsersRanking();
        void getUserRanking(Long userId);
}
