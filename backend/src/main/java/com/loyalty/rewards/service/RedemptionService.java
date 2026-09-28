package com.loyalty.rewards.service;

import com.loyalty.rewards.dto.RedemptionDto;
import com.loyalty.rewards.exception.BadRequestException;
import com.loyalty.rewards.model.Member;
import com.loyalty.rewards.model.Redemption;
import com.loyalty.rewards.model.RewardsConfig;
import com.loyalty.rewards.repository.MemberRepository;
import com.loyalty.rewards.repository.RedemptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedemptionService {

    private final RedemptionRepository redemptionRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final RewardsConfigService rewardsConfigService;

    public RedemptionService(RedemptionRepository redemptionRepository,
                              MemberRepository memberRepository,
                              MemberService memberService,
                              RewardsConfigService rewardsConfigService) {
        this.redemptionRepository = redemptionRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.rewardsConfigService = rewardsConfigService;
    }

    public List<RedemptionDto> getAllRedemptions() {
        return redemptionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<RedemptionDto> getRedemptionsForMember(Long memberId) {
        return redemptionRepository.findByMemberIdOrderByRedemptionDateDesc(memberId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Validation lives here, not in the UI: minimum redeemable points and
     * sufficient balance are both enforced server-side.
     */
    @Transactional
    public RedemptionDto recordRedemption(RedemptionDto dto) {
        Member member = memberService.findMemberOrThrow(dto.getMemberId());
        RewardsConfig config = rewardsConfigService.getActiveConfig();

        if (dto.getPointsRedeemed() < config.getMinRedeemPoints()) {
            throw new BadRequestException(
                    "Minimum redemption is " + config.getMinRedeemPoints() + " points");
        }
        if (member.getPointsBalance() < dto.getPointsRedeemed()) {
            throw new BadRequestException(
                    "Insufficient points balance. Available: " + member.getPointsBalance());
        }

        double cashValue = dto.getPointsRedeemed() * config.getRedeemValuePerPoint();

        Redemption redemption = new Redemption(
                member,
                dto.getPointsRedeemed(),
                cashValue,
                dto.getRedemptionDate() != null ? dto.getRedemptionDate() : LocalDateTime.now(),
                dto.getDescription()
        );
        Redemption saved = redemptionRepository.save(redemption);

        member.setPointsBalance(member.getPointsBalance() - dto.getPointsRedeemed());
        memberRepository.save(member);

        return toDto(saved);
    }

    private RedemptionDto toDto(Redemption redemption) {
        return new RedemptionDto(
                redemption.getId(),
                redemption.getMember().getId(),
                redemption.getPointsRedeemed(),
                redemption.getCashValue(),
                redemption.getRedemptionDate(),
                redemption.getDescription()
        );
    }
}
