package ru.pnz.floridov.RestDemo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.CreditProduct;
import ru.pnz.floridov.RestDemo.repository.ClientRepository;
import ru.pnz.floridov.RestDemo.util.ClientNotFoundException;
import ru.pnz.floridov.RestDemo.util.Type;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;



@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }



    public List<Client> findAll() {

        return clientRepository.findAll();
    }



    public Client findOne(Long id) {
        Optional<Client> foundClient = clientRepository.findById(id);
        return foundClient.orElseThrow(ClientNotFoundException::new);
    }



    @Transactional
    public void save(Client client) {clientRepository.save(client);
    }

    @Transactional
    public void update(Long id, Client updatedClient) {
        updatedClient.setId(id);
       clientRepository.save(updatedClient);
    }



    @Transactional
    public void delete(Long id) {
        clientRepository.deleteById(id);


    }

        public List<CreditProduct> getCreditProductsByClientId(Long id) {
            Optional<Client> client = clientRepository.findById(id);

            if (client.isPresent()) {
                Hibernate.initialize(client.get().getCredits());
                // Мы внизу итерируемся по кредитным продуктам, поэтому они точно будут загружены, но на всякий случай
                // не мешает всегда вызывать Hibernate.initialize()
                // (на случай, например, если код в дальнейшем поменяется и итерация по кредитам удалится)

                // Проверка просроченности кредитных продуктов
//                client.get().getCredits().forEach(creditProduct -> {
//                    Long diffInMillies = Math.abs(creditProduct.getTakenAt().getTime() - new Date().getTime());  ////доделать дату оплаты кредита
//                    // 2592000000 милисекунд = 30 суток
//
//                    if (diffInMillies > 2592000000L)
//                        creditProduct.setExpired(true); // оплата кредита просрочена
//                });

                return client.get().getCredits();
            }
            else {
                return Collections.emptyList();
            }
        }

//
////        public Optional<Person> getPersonByFullName(String fullName) {
////            return peopleRepository.findByFullName(fullName);
////        }
////
//    public Client replaceClient(Client newClient, Long id) {
//        return clientRepository.findById(id)
//                .map(client -> {
//                    client.setFirstName((newClient.getFirstName());
//
//
//
//                    /////доделать
//
//
//                    return clientRepository.save(client);
//                })
//                .orElseGet(() -> {
//                    newClient.setId(id);
//                    return clientRepository.save(newClient);
//                });
//    }







//    public LoanApplication makeLoanApplication(Integer id, BigDecimal amount, BigDecimal rate, Integer loanPeriod) {
//        Client client = clientRepository.findById(id).orElseThrow();
//        return new LoanApplication(client, amount, rate, loanPeriod);
//    }
//
//    public void pay(Client client, Loan loan, BigDecimal amount) {
//        Repayment repayment = new Repayment();
//        repayment.setAmount(amount);
//        repayment.setLoan(loan);
//        repayment.setRepaymentDate(LocalDate.now());
//    }
}