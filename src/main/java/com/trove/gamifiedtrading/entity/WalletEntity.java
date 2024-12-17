package com.trove.gamifiedtrading.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private Long userId;

    @Column(precision = 20, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
}
