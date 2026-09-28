package com.loyalty.rewards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class TransactionDto {

    private Long id;

    @NotNull(message = "memberId is required")
    private Long memberId;

    @NotNull(message = "amountSpent is required")
    @Positive(message = "amountSpent must be greater than zero")
    private Double amountSpent;

    private Integer pointsEarned;

    private LocalDateTime transactionDate;

    private String description;

    public TransactionDto() {
    }

    public TransactionDto(Long id, Long memberId, Double amountSpent, Integer pointsEarned,
                           LocalDateTime transactionDate, String description) {
        this.id = id;
        this.memberId = memberId;
        this.amountSpent = amountSpent;
        this.pointsEarned = pointsEarned;
        this.transactionDate = transactionDate;
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

    public Double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(Double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
