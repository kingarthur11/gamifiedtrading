package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.dto.BuyAssetDto;
import com.trove.gamifiedtrading.services.ITradingService;
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

    @PostMapping("/buy-asset")
    public String buy(@RequestBody BuyAssetDto buyAssetDto) {
        iTradingService.buyAsset(buyAssetDto);
        return "this user has been updated";
    }

    @PostMapping("/sell-asset")
    public String sell(@RequestBody BuyAssetDto buyAssetDto) {
        iTradingService.sellAsset(buyAssetDto);
        return "this user has been updated";
    }
}
