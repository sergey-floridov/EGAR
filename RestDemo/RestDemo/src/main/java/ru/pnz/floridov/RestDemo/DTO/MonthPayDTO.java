package ru.pnz.floridov.RestDemo.DTO;

import ru.pnz.floridov.RestDemo.exception.creditProductException.CreditProductNotFoundException;
import ru.pnz.floridov.RestDemo.model.CreditProduct;
import ru.pnz.floridov.RestDemo.repository.CreditProductRepository;

import java.math.BigDecimal;
import java.util.Optional;



public class MonthPayDTO {

    private final CreditProductRepository creditProductRepository;


    public MonthPayDTO(CreditProductRepository creditProductRepository) {
        this.creditProductRepository = creditProductRepository;
    }

    //    формула расчета платежа x = amount * ((i*(i+1)'n)/ (((i+1)'n) - 1)   особо вникать не нужно, взято из справочных материалов
//    при расчете формулы нужно учитывать особенности арифметических операций с BigDecimal (add, subtract, divide, multiply)
    public BigDecimal getMonthPay (Long id){
        Optional<CreditProduct> foundCreditProduct = creditProductRepository.findById(id);
        CreditProduct creditProduct = foundCreditProduct.orElseThrow(CreditProductNotFoundException::new);
        BigDecimal i = (creditProduct.getRate()).divide(BigDecimal.valueOf(1200));   // рассчитываем месячную процентную ставку i
        BigDecimal add1 = i.add(BigDecimal.valueOf(1));                          // промежуточная переменная расчета
        BigDecimal pow1 = add1.pow(creditProduct.getLoanPeriodInMonth());       // промежуточная переменная расчета
        BigDecimal multiply1 = i.multiply(pow1);                                // промежуточная переменная расчета
        BigDecimal subtract1 = pow1.subtract(BigDecimal.valueOf(1));            // промежуточная переменная расчета
        BigDecimal divide1 = multiply1.divide(subtract1);                       // промежуточная переменная расчета
        BigDecimal monthPay = divide1.multiply(creditProduct.getAmount());      // ежемесячный платеж
        return monthPay;
    }


}
