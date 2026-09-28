package com.loyalty.rewards.service;

import com.loyalty.rewards.dto.RewardsConfigDto;
import com.loyalty.rewards.model.RewardsConfig;
import com.loyalty.rewards.repository.RewardsConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardsConfigService {

    private final RewardsConfigRepository configRepository;

    public RewardsConfigService(RewardsConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    /**
     * Returns the single active config row, creating a sane default the
     * first time the app runs against an empty table.
     */
    public RewardsConfig getActiveConfig() {
        List<RewardsConfig> all = configRepository.findAll();
        if (all.isEmpty()) {
            RewardsConfig defaultConfig = new RewardsConfig(1.0, 100, 0.5);
            return configRepository.save(defaultConfig);
        }
        return all.get(0);
    }

    public RewardsConfigDto getConfigDto() {
        return toDto(getActiveConfig());
    }

    public RewardsConfigDto updateConfig(RewardsConfigDto dto) {
        RewardsConfig config = getActiveConfig();
        config.setEarnRatePerAmount(dto.getEarnRatePerAmount());
        config.setMinRedeemPoints(dto.getMinRedeemPoints());
        config.setRedeemValuePerPoint(dto.getRedeemValuePerPoint());
        return toDto(configRepository.save(config));
    }

    private RewardsConfigDto toDto(RewardsConfig config) {
        return new RewardsConfigDto(
                config.getId(),
                config.getEarnRatePerAmount(),
                config.getMinRedeemPoints(),
                config.getRedeemValuePerPoint()
        );
    }
}
