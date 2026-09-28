package com.loyalty.rewards.service;

import com.loyalty.rewards.dto.StatementEntryDto;
import com.loyalty.rewards.model.Redemption;
import com.loyalty.rewards.model.Transaction;
import com.loyalty.rewards.repository.RedemptionRepository;
import com.loyalty.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The statement is not its own table — it's computed by merging
 * transactions (earn) and redemptions (redeem) for a member and
 * sorting by date, newest first.
 */
@Service
public class StatementService {

    private final TransactionRepository transactionRepository;
    private final RedemptionRepository redemptionRepository;

    public StatementService(TransactionRepository transactionRepository,
                             RedemptionRepository redemptionRepository) {
        this.transactionRepository = transactionRepository;
        this.redemptionRepository = redemptionRepository;
    }

    public List<StatementEntryDto> getStatementForMember(Long memberId) {
        List<StatementEntryDto> entries = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findByMemberIdOrderByTransactionDateDesc(memberId);
        for (Transaction t : transactions) {
            entries.add(new StatementEntryDto(
                    "EARN",
                    t.getTransactionDate(),
                    t.getDescription(),
                    t.getPointsEarned(),
                    t.getAmountSpent()
            ));
        }

        List<Redemption> redemptions = redemptionRepository.findByMemberIdOrderByRedemptionDateDesc(memberId);
        for (Redemption r : redemptions) {
            entries.add(new StatementEntryDto(
                    "REDEEM",
                    r.getRedemptionDate(),
                    r.getDescription(),
                    -r.getPointsRedeemed(),
                    r.getCashValue()
            ));
        }

        entries.sort(Comparator.comparing(StatementEntryDto::getDate).reversed());
        return entries;
    }
}
