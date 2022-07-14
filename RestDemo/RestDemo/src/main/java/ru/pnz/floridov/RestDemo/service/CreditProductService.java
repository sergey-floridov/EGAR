package ru.pnz.floridov.RestDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.CreditProduct;
import ru.pnz.floridov.RestDemo.repository.CreditProductRepository;
import ru.pnz.floridov.RestDemo.util.Type;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CreditProductService {


    private final CreditProductRepository creditProductRepository;


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
        return foundCreditProduct.orElse(null);
    }

    @Transactional
    public void save(CreditProduct creditProduct) {
        creditProductRepository.save(creditProduct);
    }

    @Transactional
    public void update(Long id, CreditProduct updatedCreditProduct) {
        CreditProduct creditProductToBeUpdated = creditProductRepository.findById(id).get();

        // добавляем по сути новый кредитный продукт (который не находится в Persistence context), поэтому нужен save()
        updatedCreditProduct.setId(id);
        updatedCreditProduct.setClient(creditProductToBeUpdated.getClient()); // чтобы не терялась связь при обновлении

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



    // Освбождает книгу (этот метод вызывается, когда человек возвращает книгу в библиотеку)
    @Transactional
    public void release(Long id) {
        creditProductRepository.findById(id).ifPresent(
                creditProduct -> {
                    creditProduct.setClient(null);
                    creditProduct.setTakenAt(null);
                });
    }






//    Переделать (возможно в REST controller)  под дату оплаты кредита
// Назначает кредит человеку
    @Transactional
    public void assign(Long id, Client selectedClient) {
       creditProductRepository.findById(id).ifPresent(
                creditProduct -> {
                    creditProduct.setClient(selectedClient);
//                    creditProduct.setTakenAt(new Date()); // текущее время
                }
        );
    }


}

