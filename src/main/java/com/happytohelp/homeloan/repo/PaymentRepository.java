/*
 * package com.happytohelp.homeloan.repo;
 * 
 * 
 * import com.happytohelp.homeloan.model.Payment; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * public interface PaymentRepository extends JpaRepository<Payment, Long> { }
 */
/*
 * package com.happytohelp.homeloan.repo;
 * 
 * import com.happytohelp.homeloan.model.Payment; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import java.util.Optional;
 * 
 * public interface PaymentRepository extends JpaRepository<Payment, Long> {
 * Optional<Payment> findByIdAndLoanApplicationId(Long id, Long
 * loanApplicationId); }
 */
/*
 * 
 * package com.happytohelp.homeloan.repo;
 * 
 * import com.happytohelp.homeloan.model.Payment; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import java.util.Optional;
 * 
 * public interface PaymentRepository extends JpaRepository<Payment, Long> {
 * Optional<Payment> findByIdAndLoanApplicationId(Long id, Long
 * loanApplicationId); }
 */

package com.happytohelp.homeloan.repo;

import com.happytohelp.homeloan.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByIdAndLoanApplicationId(Long id, Long loanApplicationId);
}