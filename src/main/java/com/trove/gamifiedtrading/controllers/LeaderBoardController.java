package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.dto.ConvertUserEntity;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.services.ILeaderBoard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderBoardController {

    private final ILeaderBoard iLeaderBoard;

    public LeaderBoardController(ILeaderBoard iLeaderBoard) {
        this.iLeaderBoard = iLeaderBoard;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ConvertUserEntity>>> getAllUsersRanking() {
        ApiResponse<List<ConvertUserEntity>> leaderBoard =  iLeaderBoard.getUsersRanking();
        return new ResponseEntity<>(leaderBoard, HttpStatus.OK);
    }
}
