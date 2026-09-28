package com.loyalty.rewards.controller;

import com.loyalty.rewards.dto.StatementEntryDto;
import com.loyalty.rewards.service.StatementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statement")
@CrossOrigin(origins = "*")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/{memberId}")
    public List<StatementEntryDto> getStatement(@PathVariable Long memberId) {
        return statementService.getStatementForMember(memberId);
    }
}
