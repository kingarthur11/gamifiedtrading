package com.trove.gamifiedtrading.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	private Integer gemCount = 0;

	private Integer buyCount = 0;

	private Integer sellCount = 0;

	private Integer numMilestone = 0;

	private Integer totalTrade = 0;

    private Integer rank = 0;
}
