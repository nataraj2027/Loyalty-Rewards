package com.loyalty.rewards.controller;

import com.loyalty.rewards.dto.TransactionDto;
import com.loyalty.rewards.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionDto> getAllTransactions(@RequestParam(required = false) Long memberId) {
        if (memberId != null) {
            return transactionService.getTransactionsForMember(memberId);
        }
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public ResponseEntity<TransactionDto> recordTransaction(@Valid @RequestBody TransactionDto dto) {
        TransactionDto created = transactionService.recordTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
