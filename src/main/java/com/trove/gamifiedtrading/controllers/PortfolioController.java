package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreatePortforlioDto;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.services.IPortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final IPortfolioService iPortfolioService;

    public PortfolioController(IPortfolioService iPortfolioService){
        this.iPortfolioService = iPortfolioService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PortfolioEntity>>> getAllUsers() {
        ApiResponse<List<PortfolioEntity>> portfolios =  iPortfolioService.getAllPortfolios();
        return new ResponseEntity<>(portfolios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<PortfolioEntity>>> getUser(@PathVariable("id") Long id) {
        ApiResponse<Optional<PortfolioEntity>> portfolio = iPortfolioService.getPortfolioById(id);
        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }

    @PostMapping("/add-asset")
    public ResponseEntity<BaseResponse> addAsset(@RequestBody CreatePortforlioDto createPortforlioDto) {

        var response = iPortfolioService.addAsset(createPortforlioDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/remove-asset")
    public ResponseEntity<BaseResponse> removeAsset(@RequestBody CreatePortforlioDto createPortforlioDto) {

        var response = iPortfolioService.removeAsset(createPortforlioDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
