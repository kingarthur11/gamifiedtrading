package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.dto.BuyAssetDto;
import com.trove.gamifiedtrading.data.dto.CreatePortforlioDto;
import com.trove.gamifiedtrading.services.IPortfolioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final IPortfolioService iPortfolioService;

    public PortfolioController(IPortfolioService iPortfolioService){
        this.iPortfolioService = iPortfolioService;
    }

    @PostMapping("/add-asset")
    public String addAsset(@RequestBody CreatePortforlioDto createPortforlioDto) {
        iPortfolioService.addAsset(createPortforlioDto);
        return "this user has been updated";
    }

    @PostMapping("/remove-asset")
    public String removeAsset(@RequestBody CreatePortforlioDto createPortforlioDto) {
        iPortfolioService.removeAsset(createPortforlioDto);
        return "this user has been updated";
    }
}
