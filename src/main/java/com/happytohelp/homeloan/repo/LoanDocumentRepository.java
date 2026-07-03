/*
 * package com.happytohelp.homeloan.repo;
 * 
 * 
 * import com.happytohelp.homeloan.model.LoanDocument; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import java.util.List;
 * 
 * public interface LoanDocumentRepository extends JpaRepository<LoanDocument,
 * Long> { List<LoanDocument> findByLoanApplicationIdOrderByIdDesc(Long
 * loanApplicationId); }
 */
/*
 * package com.happytohelp.homeloan.repo;
 * 
 * import com.happytohelp.homeloan.model.LoanDocument; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import java.util.List; import java.util.Optional;
 * 
 * public interface LoanDocumentRepository extends JpaRepository<LoanDocument,
 * Long> { List<LoanDocument> findByLoanApplicationIdOrderByIdDesc(Long
 * loanApplicationId); Optional<LoanDocument> findByIdAndLoanApplicationId(Long
 * id, Long loanApplicationId); }
 */


/*
 * package com.happytohelp.homeloan.repo;
 * 
 * import com.happytohelp.homeloan.model.LoanDocument; import
 * org.springframework.data.jpa.repository.JpaRepository;
 * 
 * import java.util.List; import java.util.Optional;
 * 
 * public interface LoanDocumentRepository extends JpaRepository<LoanDocument,
 * Long> { List<LoanDocument> findByLoanApplicationIdOrderByIdDesc(Long
 * loanApplicationId); Optional<LoanDocument> findByIdAndLoanApplicationId(Long
 * id, Long loanApplicationId); }
 */

package com.happytohelp.homeloan.repo;

import com.happytohelp.homeloan.model.LoanDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanDocumentRepository extends JpaRepository<LoanDocument, Long> {
    List<LoanDocument> findByLoanApplicationIdOrderByIdDesc(Long loanApplicationId);
    Optional<LoanDocument> findByIdAndLoanApplicationId(Long id, Long loanApplicationId);
}