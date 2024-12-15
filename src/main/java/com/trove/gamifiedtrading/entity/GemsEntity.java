package com.trove.gamifiedtrading.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class GemsEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer bonus;

	private Integer trades;

	private Long userId;
}
