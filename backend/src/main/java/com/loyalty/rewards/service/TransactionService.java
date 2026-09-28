package com.loyalty.rewards.service;

import com.loyalty.rewards.dto.TransactionDto;
import com.loyalty.rewards.model.Member;
import com.loyalty.rewards.model.RewardsConfig;
import com.loyalty.rewards.model.Transaction;
import com.loyalty.rewards.repository.MemberRepository;
import com.loyalty.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final RewardsConfigService rewardsConfigService;

    public TransactionService(TransactionRepository transactionRepository,
                               MemberRepository memberRepository,
                               MemberService memberService,
                               RewardsConfigService rewardsConfigService) {
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.rewardsConfigService = rewardsConfigService;
    }

    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsForMember(Long memberId) {
        return transactionRepository.findByMemberIdOrderByTransactionDateDesc(memberId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Points math happens here, server-side, so the frontend cannot be
     * tricked into posting an arbitrary points value.
     */
    @Transactional
    public TransactionDto recordTransaction(TransactionDto dto) {
        Member member = memberService.findMemberOrThrow(dto.getMemberId());
        RewardsConfig config = rewardsConfigService.getActiveConfig();

        int pointsEarned = (int) Math.floor(dto.getAmountSpent() * config.getEarnRatePerAmount());

        Transaction transaction = new Transaction(
                member,
                dto.getAmountSpent(),
                pointsEarned,
                dto.getTransactionDate() != null ? dto.getTransactionDate() : LocalDateTime.now(),
                dto.getDescription()
        );
        Transaction saved = transactionRepository.save(transaction);

        member.setPointsBalance(member.getPointsBalance() + pointsEarned);
        memberRepository.save(member);

        return toDto(saved);
    }

    private TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getMember().getId(),
                transaction.getAmountSpent(),
                transaction.getPointsEarned(),
                transaction.getTransactionDate(),
                transaction.getDescription()
        );
    }
}
