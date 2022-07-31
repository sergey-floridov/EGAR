package ru.pnz.floridov.RestDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.DebetAccount;

import java.math.BigDecimal;

@Repository
public interface DebetAccountRepository extends JpaRepository<DebetAccount,Long> {

    //@Query(value = "select sum (amount) as total from debet_account where client_id?1", nativeQuery = true)
    @Query(value = "select (select sum (amount) as total from debet_account where client_id?1 AND currency = 'RUB'," +
            "select sum (amount) as total from debet_account where client_id?1 AND currency = 'USD'" +
            ")", nativeQuery = true)
    BigDecimal findAllDebetBalanceDetailsById(Long id);
}
