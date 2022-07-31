package ru.pnz.floridov.RestDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnz.floridov.RestDemo.exception.creditProductException.CreditProductNotFoundException;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.CreditProduct;
import ru.pnz.floridov.RestDemo.repository.CreditProductRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CreditProductService {



    private final CreditProductRepository creditProductRepository;

//    private BigDecimal monthPay = getMonthPay();

    @Autowired
    public CreditProductService(CreditProductRepository creditProductRepository) {
        this.creditProductRepository = creditProductRepository;
    }


    public List<CreditProduct> findAll() {
            return creditProductRepository.findAll();
    }



    public List<CreditProduct> findWithPagination(Integer page, Integer creditProductPerPage) {
            return creditProductRepository.findAll(PageRequest.of(page, creditProductPerPage)).getContent();
    }



    public CreditProduct findOne(Long id) {
        Optional<CreditProduct> foundCreditProduct = creditProductRepository.findById(id);
        return foundCreditProduct.orElseThrow(CreditProductNotFoundException::new);
    }

    @Transactional
    public void save(CreditProduct creditProduct) {
        creditProductRepository.save(creditProduct);
//        BigDecimal loanBalance = creditProduct.getLoanBalance();   Заготовка для уменьшения суммы кредита
//        loanBalance = creditProduct.getAmount();
    }

    @Transactional
    public void update(Long id, CreditProduct updatedCreditProduct) {
        updatedCreditProduct.setId(id);
        creditProductRepository.save(updatedCreditProduct);
    }

    @Transactional
    public void delete(Long id) {
        creditProductRepository.deleteById(id);
    }





    // Returns null if creditProduct has no client
    public Client getClient(Long id) {
        // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается нелениво
        return creditProductRepository.findById(id).map(CreditProduct::getClient).orElse(null);
    }



    @Transactional
    public void release(Long id) {
        creditProductRepository.findById(id).ifPresent(
                creditProduct -> {
                    creditProduct.setClient(null);
                    creditProduct.setTakenAt(null);
                });
    }







//Набросок для расчета ежемесячного платежа
//    формула расчета платежа x = amount * ((i*(i+1)'n)/ (((i+1)'n) - 1)   особо вникать не нужно, взято из справочных материалов
//    при расчете формулы нужно учитывать особенности арифметических операций с BigDecimal (add, subtract, divide, multiply)
    @Transactional
    public BigDecimal getMonthPay (Long id){
        Optional<CreditProduct> foundCreditProduct = creditProductRepository.findById(id);
        CreditProduct creditProduct = foundCreditProduct.orElseThrow(CreditProductNotFoundException::new);
        BigDecimal monthProcent = (creditProduct.getRate()).divide(BigDecimal.valueOf(1200),6, RoundingMode.UP);   // рассчитываем месячную процентную ставку i
        BigDecimal add1 = monthProcent.add(BigDecimal.valueOf(1));                                                      // промежуточная переменная расчета
        BigDecimal pow1 = add1.pow(creditProduct.getLoanPeriodInMonth());                                               // промежуточная переменная расчета
        BigDecimal multiply1 = monthProcent.multiply(pow1);                                                             // промежуточная переменная расчета
        BigDecimal subtract1 = pow1.subtract(BigDecimal.valueOf(1));                                                    // промежуточная переменная расчета
        BigDecimal divide1 = multiply1.divide(subtract1,6, RoundingMode.UP);                                      // промежуточная переменная расчета
        BigDecimal monthPay = divide1.multiply(creditProduct.getAmount());      // ежемесячный платеж
        return monthPay;
    }


//    Расчет переплаты за кредит
    @Transactional
    public BigDecimal getOverPayment (Long id){
        Optional<CreditProduct> foundCreditProduct = creditProductRepository.findById(id);
        CreditProduct creditProduct = foundCreditProduct.orElseThrow(CreditProductNotFoundException::new);
        BigDecimal totalPayment = CreditProductService.this.getMonthPay(id).multiply(BigDecimal.valueOf(creditProduct.getLoanPeriodInMonth()));
        BigDecimal overPayment = totalPayment.subtract(creditProduct.getAmount());
        return overPayment;
    }


//    Расчет суммы процентов за платежный период, списание суммы с основного долга (за вычетом начисленных процентов)

//    доделать дату взятия платежа и последнего платежа
    @Transactional
    public BigDecimal payProcent (Long id){
        Optional<CreditProduct> foundCreditProduct = creditProductRepository.findById(id);
        CreditProduct creditProduct = foundCreditProduct.orElseThrow(CreditProductNotFoundException::new);
        long betweenPays = ChronoUnit.DAYS.between(LocalDate.now(), creditProduct.getLastPaidAt());      // рассчитываем количество дней между платежами
        BigDecimal procentPerPeriod = (creditProduct.getAmount().multiply(creditProduct.getRate().multiply(BigDecimal.valueOf(betweenPays)))).divide(BigDecimal.valueOf(36500));
        return CreditProductService.this.getMonthPay(id).subtract(procentPerPeriod);
    }

}

