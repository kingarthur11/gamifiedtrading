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
public class PortfolioEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Integer quantity = 0;

	@Column(precision = 20, scale = 2)
    private BigDecimal price;

	private Long userId;

	private Long assetId;
}
