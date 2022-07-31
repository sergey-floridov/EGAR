package ru.pnz.floridov.RestDemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.CreditProduct;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CreditProductRepository extends JpaRepository<CreditProduct,Long> {

//   List<CreditProduct> findByTypeStartingWith(String title);    /// Переделать

    @Query(value = "select sum (amount) as total from credit_product where client_id?1", nativeQuery = true)
    BigDecimal findAllCreditBalanceDetailsById(Long id);

}



