package com.loyalty.rewards.controller;

import com.loyalty.rewards.dto.RewardsConfigDto;
import com.loyalty.rewards.service.RewardsConfigService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "*")
public class RewardsConfigController {

    private final RewardsConfigService rewardsConfigService;

    public RewardsConfigController(RewardsConfigService rewardsConfigService) {
        this.rewardsConfigService = rewardsConfigService;
    }

    @GetMapping
    public RewardsConfigDto getConfig() {
        return rewardsConfigService.getConfigDto();
    }

    @PutMapping
    public RewardsConfigDto updateConfig(@Valid @RequestBody RewardsConfigDto dto) {
        return rewardsConfigService.updateConfig(dto);
    }
}
