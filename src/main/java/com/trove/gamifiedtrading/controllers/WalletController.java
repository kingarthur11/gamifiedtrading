package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.dto.BuyAssetDto;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;
import com.trove.gamifiedtrading.services.IWalletService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final IWalletService iWalletService;

    public WalletController(IWalletService iWalletService) {
        this.iWalletService = iWalletService;
    }

    @PostMapping("/credit-wallet")
    public String credit(@RequestBody CreditWalletDto creditWalletDto) {
        iWalletService.creditFunds(creditWalletDto);
        return "this user has been updated";
    }

    @PostMapping("/transfer-funds")
    public String transfer(@RequestBody TransferFromWalletDto transferFromWalletDto) {
        iWalletService.transferFunds(transferFromWalletDto);
        return "this user has been updated";
    }
}
