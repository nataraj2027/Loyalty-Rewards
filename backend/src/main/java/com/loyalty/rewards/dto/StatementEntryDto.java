package com.loyalty.rewards.dto;

import java.time.LocalDateTime;

/**
 * Unified line item for the member statement — computed on the fly by
 * merging Transaction (EARN) and Redemption (REDEEM) rows, sorted by date.
 */
public class StatementEntryDto {

    private String type; // "EARN" or "REDEEM"
    private LocalDateTime date;
    private String description;
    private Integer pointsChange; // positive for earn, negative for redeem
    private Double amount; // amountSpent for EARN, cashValue for REDEEM

    public StatementEntryDto() {
    }

    public StatementEntryDto(String type, LocalDateTime date, String description,
                              Integer pointsChange, Double amount) {
        this.type = type;
        this.date = date;
        this.description = description;
        this.pointsChange = pointsChange;
        this.amount = amount;
    }

    // ---- Getters & Setters ----

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPointsChange() {
        return pointsChange;
    }

    public void setPointsChange(Integer pointsChange) {
        this.pointsChange = pointsChange;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
