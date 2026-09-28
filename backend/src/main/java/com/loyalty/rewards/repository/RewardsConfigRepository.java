package com.loyalty.rewards.repository;

import com.loyalty.rewards.model.RewardsConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardsConfigRepository extends JpaRepository<RewardsConfig, Long> {
}
