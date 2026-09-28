package com.loyalty.rewards.repository;

import com.loyalty.rewards.model.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedemptionRepository extends JpaRepository<Redemption, Long> {

    List<Redemption> findByMemberIdOrderByRedemptionDateDesc(Long memberId);
}
