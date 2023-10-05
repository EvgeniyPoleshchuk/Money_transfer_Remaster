package ru.netoology.service;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netoology.exception.InvalidCardData;

import ru.netoology.model.ConfirmOperation;
import ru.netoology.model.CreditCart;
import ru.netoology.web.response.TransferResponse;
import ru.netoology.repository.TransferRepository;


import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class TransferService {
    private final TransferRepository repository;


    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    public TransferResponse doTransfer(CreditCart creditCart) {
        cardNumberVerification(creditCart);
        cardCvvVerification(creditCart);
        cardDataVerification(creditCart);
        return repository.addCard(creditCart);
    }

    public void cardNumberVerification(CreditCart creditCart) {
        if (creditCart.getCardFromNumber().isEmpty() && creditCart.getCardToNumber().isEmpty()) {
            throw new InvalidCardData("Номер карты не может быть пустым");
        } else if (!creditCart.getCardFromNumber().matches("[0-9]{16}")) {
            throw new InvalidCardData("Номер карты отправителя должна быть из 16 цифр");
        } else if (!creditCart.getCardToNumber().matches("[0-9]{16}")) {
            throw new InvalidCardData("Номер карты получателя должна быть из 16 цифр");
        }

        if (creditCart.getCardFromNumber().equals(creditCart.getCardToNumber())) {
            throw new InvalidCardData("Номер карты отправителя и получателя должны быть разными");
        }
    }

    public void cardCvvVerification(CreditCart creditCart) {
        if (!creditCart.getCardFromCVV().matches("[0-9]{3}")) {
            throw new InvalidCardData("CVV должен состоять из 3 цифр" + repository.getId().toString());
        }
    }

    @SneakyThrows
    public void cardDataVerification(CreditCart creditCart) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        simpleDateFormat.setLenient(false);
        Date expiry = simpleDateFormat.parse(creditCart.getCardFromValidTill());
        boolean expired = expiry.before(new Date());
        if (expired) {
            throw new InvalidCardData("Неверно указан срок действия карты");
        }

    }
    public TransferResponse doConfirm(ConfirmOperation operation){
        return repository.confirmOperation(operation);
    }


}
