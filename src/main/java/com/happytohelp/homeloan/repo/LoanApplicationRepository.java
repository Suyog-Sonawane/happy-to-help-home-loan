package com.happytohelp.homeloan.repo;

/*
 * 
 * import com.happytohelp.homeloan.model.ApplicationStatus; import
 * com.happytohelp.homeloan.model.LoanApplication; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import java.util.List; import java.util.Optional;
 * 
 * public interface LoanApplicationRepository extends
 * JpaRepository<LoanApplication, Long> { List<LoanApplication>
 * findByUserIdOrderByIdDesc(Long userId); Optional<LoanApplication>
 * findByIdAndUserId(Long id, Long userId); List<LoanApplication>
 * findByStatusOrderByIdDesc(ApplicationStatus status); }
 */



import com.happytohelp.homeloan.model.ApplicationStatus;
import com.happytohelp.homeloan.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByUserIdOrderByIdDesc(Long userId);
    Optional<LoanApplication> findByIdAndUserId(Long id, Long userId);
    List<LoanApplication> findByStatusOrderByIdDesc(ApplicationStatus status);

    long countByStatus(ApplicationStatus status);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, ApplicationStatus status);

    @Query("select coalesce(sum(a.disbursedAmount), 0) from LoanApplication a where a.status = 'DISBURSED'")
    BigDecimal sumTotalDisbursed();
}