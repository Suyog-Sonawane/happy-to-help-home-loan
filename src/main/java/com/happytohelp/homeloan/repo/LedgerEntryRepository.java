package com.happytohelp.homeloan.repo;


import com.happytohelp.homeloan.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
    Optional<LedgerEntry> findTopByLoanApplicationIdOrderByIdDesc(Long loanApplicationId);
    List<LedgerEntry> findByLoanApplicationIdOrderByIdAsc(Long loanApplicationId);
}