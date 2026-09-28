package com.loyalty.rewards.repository;

import com.loyalty.rewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByMemberIdOrderByTransactionDateDesc(Long memberId);
}
