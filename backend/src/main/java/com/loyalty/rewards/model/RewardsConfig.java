package com.loyalty.rewards.model;

import jakarta.persistence.*;

/**
 * Single-row configuration table that drives the points math.
 * earnRatePerAmount   -> points earned per 1 unit of currency spent
 * minRedeemPoints     -> minimum points required to make a redemption
 * redeemValuePerPoint -> currency value of 1 point when redeemed
 */
@Entity
@Table(name = "rewards_config")
public class RewardsConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double earnRatePerAmount;

    @Column(nullable = false)
    private Integer minRedeemPoints;

    @Column(nullable = false)
    private Double redeemValuePerPoint;

    public RewardsConfig() {
    }

    public RewardsConfig(Double earnRatePerAmount, Integer minRedeemPoints, Double redeemValuePerPoint) {
        this.earnRatePerAmount = earnRatePerAmount;
        this.minRedeemPoints = minRedeemPoints;
        this.redeemValuePerPoint = redeemValuePerPoint;
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEarnRatePerAmount() {
        return earnRatePerAmount;
    }

    public void setEarnRatePerAmount(Double earnRatePerAmount) {
        this.earnRatePerAmount = earnRatePerAmount;
    }

    public Integer getMinRedeemPoints() {
        return minRedeemPoints;
    }

    public void setMinRedeemPoints(Integer minRedeemPoints) {
        this.minRedeemPoints = minRedeemPoints;
    }

    public Double getRedeemValuePerPoint() {
        return redeemValuePerPoint;
    }

    public void setRedeemValuePerPoint(Double redeemValuePerPoint) {
        this.redeemValuePerPoint = redeemValuePerPoint;
    }
}
