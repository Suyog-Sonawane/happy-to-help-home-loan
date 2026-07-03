/*
 * package com.happytohelp.homeloan.repo;
 * 
 * 
 * import com.happytohelp.homeloan.model.EmiSchedule; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import java.util.List; import java.util.Optional;
 * 
 * public interface EmiScheduleRepository extends JpaRepository<EmiSchedule,
 * Long> { List<EmiSchedule> findByLoanApplicationIdOrderByInstallmentNoAsc(Long
 * loanApplicationId); Optional<EmiSchedule> findByIdAndLoanApplicationId(Long
 * id, Long loanApplicationId); }
 */


package com.happytohelp.homeloan.repo;

import com.happytohelp.homeloan.model.EmiSchedule;
import com.happytohelp.homeloan.model.EmiStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmiScheduleRepository extends JpaRepository<EmiSchedule, Long> {
    List<EmiSchedule> findByLoanApplicationIdOrderByInstallmentNoAsc(Long loanApplicationId);
    Optional<EmiSchedule> findByIdAndLoanApplicationId(Long id, Long loanApplicationId);

    @Query("select count(e) from EmiSchedule e where e.status='DUE' and e.loanApplication.status='DISBURSED'")
    long countDueEmisForDisbursedLoans();

    @Query("""
           select e from EmiSchedule e
           where e.status = 'DUE'
             and e.loanApplication.status = 'DISBURSED'
             and e.loanApplication.user.id = :userId
           order by e.dueDate asc
           """)
    List<EmiSchedule> findNextDueEmiForUser(Long userId, Pageable pageable);

    long countByLoanApplicationUserIdAndStatus(Long userId, EmiStatus status);
}