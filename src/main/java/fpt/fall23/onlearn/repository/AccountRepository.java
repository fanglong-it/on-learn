package fpt.fall23.onlearn.repository;

import fpt.fall23.onlearn.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findFirstByEmail(String email);

    Optional<Account> findFirstByCode(String code);
}
