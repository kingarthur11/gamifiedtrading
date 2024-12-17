package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.services.IWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final IWalletService iWalletService;

    public WalletController(IWalletService iWalletService) {
        this.iWalletService = iWalletService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<WalletEntity>>> getAllUsers() {
        ApiResponse<List<WalletEntity>> wallets =  iWalletService.getAllWallets();
        return new ResponseEntity<>(wallets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<WalletEntity>>> getUser(@PathVariable("id") Long id) {
        ApiResponse<Optional<WalletEntity>> wallet = iWalletService.getWalletById(id);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @PostMapping("/credit-wallet")
    public ResponseEntity<BaseResponse> credit(@RequestBody CreditWalletDto creditWalletDto) {
        var response = iWalletService.creditFunds(creditWalletDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/transfer-funds")
    public ResponseEntity<BaseResponse> transfer(@RequestBody TransferFromWalletDto transferFromWalletDto) {
        var response = iWalletService.transferFunds(transferFromWalletDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
