package ru.pnz.floridov.RestDemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.CreditProduct;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface CreditProductRepository extends JpaRepository<CreditProduct,Long> {

    List<CreditProduct> findByTypeStartingWith(String title);    /// Переделать

}



