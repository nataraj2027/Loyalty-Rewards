package com.loyalty.rewards.controller;

import com.loyalty.rewards.dto.RedemptionDto;
import com.loyalty.rewards.service.RedemptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redemptions")
@CrossOrigin(origins = "*")
public class RedemptionController {

    private final RedemptionService redemptionService;

    public RedemptionController(RedemptionService redemptionService) {
        this.redemptionService = redemptionService;
    }

    @GetMapping
    public List<RedemptionDto> getAllRedemptions(@RequestParam(required = false) Long memberId) {
        if (memberId != null) {
            return redemptionService.getRedemptionsForMember(memberId);
        }
        return redemptionService.getAllRedemptions();
    }

    @PostMapping
    public ResponseEntity<RedemptionDto> recordRedemption(@Valid @RequestBody RedemptionDto dto) {
        RedemptionDto created = redemptionService.recordRedemption(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
