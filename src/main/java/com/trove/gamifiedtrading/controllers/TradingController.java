package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.BuyAssetDto;
import com.trove.gamifiedtrading.data.dto.CreateAssetDto;
import com.trove.gamifiedtrading.services.ITradingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class TradingController {

    private final ITradingService iTradingService;

    public TradingController(ITradingService iTradingService) {
        this.iTradingService = iTradingService;
    }

    @PostMapping("/create-asset")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateAssetDto createAssetDto) {

        var response = iTradingService.createAsset(createAssetDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/buy-asset")
    public ResponseEntity<BaseResponse> buy(@RequestBody BuyAssetDto buyAssetDto) {

        var response = iTradingService.buyAsset(buyAssetDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sell-asset")
    public ResponseEntity<BaseResponse> sell(@RequestBody BuyAssetDto buyAssetDto) {

        var response = iTradingService.sellAsset(buyAssetDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
