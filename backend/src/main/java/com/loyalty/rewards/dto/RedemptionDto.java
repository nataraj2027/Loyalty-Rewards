package com.loyalty.rewards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class RedemptionDto {

    private Long id;

    @NotNull(message = "memberId is required")
    private Long memberId;

    @NotNull(message = "pointsRedeemed is required")
    @Positive(message = "pointsRedeemed must be greater than zero")
    private Integer pointsRedeemed;

    private Double cashValue;

    private LocalDateTime redemptionDate;

    private String description;

    public RedemptionDto() {
    }

    public RedemptionDto(Long id, Long memberId, Integer pointsRedeemed, Double cashValue,
                          LocalDateTime redemptionDate, String description) {
        this.id = id;
        this.memberId = memberId;
        this.pointsRedeemed = pointsRedeemed;
        this.cashValue = cashValue;
        this.redemptionDate = redemptionDate;
        this.description = description;
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getPointsRedeemed() {
        return pointsRedeemed;
    }

    public void setPointsRedeemed(Integer pointsRedeemed) {
        this.pointsRedeemed = pointsRedeemed;
    }

    public Double getCashValue() {
        return cashValue;
    }

    public void setCashValue(Double cashValue) {
        this.cashValue = cashValue;
    }

    public LocalDateTime getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(LocalDateTime redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
