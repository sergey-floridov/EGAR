package ru.pnz.floridov.RestDemo.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnz.floridov.RestDemo.DTO.ClientBalanceDetail;
import ru.pnz.floridov.RestDemo.exception.clientException.ClientNotFoundException;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.CreditProduct;
import ru.pnz.floridov.RestDemo.repository.ClientRepository;
import ru.pnz.floridov.RestDemo.repository.CreditProductRepository;
import ru.pnz.floridov.RestDemo.repository.DebetAccountRepository;
import ru.pnz.floridov.RestDemo.util.Currency;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientService {


    private static final Integer USD_TO_RUB_STUB_RATE = 60;
    private static final Integer EURO_TO_RUB_STUB_RATE = 62;
    private final ClientRepository clientRepository;
    private final DebetAccountRepository debetAccountRepository;
    private final CreditProductRepository creditProductRepository;


    public List<Client> findAll() {
        return clientRepository.findAll();
    }


    public Client findOne(Long id) {
        Optional<Client> foundClient = clientRepository.findById(id);
        return foundClient.orElseThrow(ClientNotFoundException::new);
    }


    // расчет общего баланса клиента. Если total>0 - баланс клиента положительный (банк должен клиенту). Если total<0
    // - баланс отрицательный (суммы кредитов больше дебета, клиент должен банку)
    @Transactional
    public ClientBalanceDetail getClientBalance(Long id) {
        var rubDebetDetails = debetAccountRepository.findAllDebetBalanceDetailsById(id, Currency.RUB.name());
        var usdDebetDetails = debetAccountRepository.findAllDebetBalanceDetailsById(id, Currency.USD.name());
        var euroDebetDetails = debetAccountRepository.findAllDebetBalanceDetailsById(id, Currency.EUR.name());
        var rubCreditDetails = creditProductRepository.findAllCreditBalanceDetailsById(id, Currency.RUB.name());
        var usdCreditDetails = creditProductRepository.findAllCreditBalanceDetailsById(id, Currency.USD.name());
        var euroCreditDetails = creditProductRepository.findAllCreditBalanceDetailsById(id, Currency.EUR.name());

        var debetSum = getSafeDetails(rubDebetDetails)
                .add(convert(usdDebetDetails, Currency.USD)
                        .add(convert(euroDebetDetails, Currency.EUR)));
        var creditSum = getSafeDetails(rubCreditDetails)
                .add(convert(usdCreditDetails, Currency.USD)
                        .add(convert(euroCreditDetails, Currency.EUR)));

        return ClientBalanceDetail.builder()
                .rubDebetSum(getSafeDetails(rubDebetDetails))
                .usdDebetSum(getSafeDetails(usdDebetDetails))
                .euroDebetSum(getSafeDetails(euroDebetDetails))
                .rubCreditSum(getSafeDetails(rubCreditDetails))
                .usdCreditSum(getSafeDetails(usdCreditDetails))
                .euroCreditSum(getSafeDetails(euroCreditDetails))
                .totalDebet(debetSum)
                .totalCredit(creditSum)
                .total(debetSum.subtract(creditSum))
                .build();
    }


    private BigDecimal getSafeDetails(BigDecimal source) {
        return source == null
                ? BigDecimal.ZERO
                : source;
    }

    private BigDecimal convert(BigDecimal money, Currency currency) {
        if (money == null || currency == null) {
            return BigDecimal.ZERO;
        }
        return switch (currency) {
            case USD -> money.multiply(BigDecimal.valueOf(USD_TO_RUB_STUB_RATE));
            case RUB -> money;
            case EUR -> money.multiply(BigDecimal.valueOf(EURO_TO_RUB_STUB_RATE));
        };
    }

    public List<Client> findByLastName(String lastName) {
        Optional<Client> foundClient = Optional.ofNullable(clientRepository.findClientByLastName(lastName));
        return Collections.singletonList(foundClient.orElse(null));
    }

    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
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


            return client.get().getCredits();
        } else {
            return Collections.emptyList();
        }
    }
}