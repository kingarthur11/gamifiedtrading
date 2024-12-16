package com.trove.gamifiedtrading.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;
import com.trove.gamifiedtrading.services.IWalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IWalletService iWalletService;

    @InjectMocks
    private WalletController walletController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void credit_ShouldReturnSuccessMessage_WhenServiceIsCalled() throws Exception {
        // Arrange
        CreditWalletDto creditWalletDto = new CreditWalletDto(BigDecimal.valueOf(100),1L );

        // Act & Assert
        mockMvc.perform(post("/credit-wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditWalletDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("this user has been updated"));

        verify(iWalletService, times(1)).creditFunds(any(CreditWalletDto.class));
    }

    @Test
    void transfer_ShouldReturnSuccessMessage_WhenServiceIsCalled() throws Exception {
        // Arrange
        TransferFromWalletDto transferFromWalletDto = new TransferFromWalletDto(BigDecimal.valueOf(50), 1L, 2L );

        // Act & Assert
        mockMvc.perform(post("/transfer-funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferFromWalletDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("this user has been updated"));

        verify(iWalletService, times(1)).transferFunds(any(TransferFromWalletDto.class));
    }
}