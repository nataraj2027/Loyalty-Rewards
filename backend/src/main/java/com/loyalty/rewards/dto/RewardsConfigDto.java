package com.loyalty.rewards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RewardsConfigDto {

    private Long id;

    @NotNull(message = "earnRatePerAmount is required")
    @Positive(message = "earnRatePerAmount must be greater than zero")
    private Double earnRatePerAmount;

    @NotNull(message = "minRedeemPoints is required")
    @Positive(message = "minRedeemPoints must be greater than zero")
    private Integer minRedeemPoints;

    @NotNull(message = "redeemValuePerPoint is required")
    @Positive(message = "redeemValuePerPoint must be greater than zero")
    private Double redeemValuePerPoint;

    public RewardsConfigDto() {
    }

    public RewardsConfigDto(Long id, Double earnRatePerAmount, Integer minRedeemPoints, Double redeemValuePerPoint) {
        this.id = id;
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
