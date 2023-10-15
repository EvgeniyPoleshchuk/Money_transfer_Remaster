package ru.netology.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.exception.ConfirmOperationException;
import ru.netology.exception.InvalidCardData;

import ru.netology.exception.TransferException;
import ru.netology.dto.ConfirmOperation;
import ru.netology.model.CreditCard;
import ru.netology.dto.TransferResponse;
import ru.netology.repository.TransferRepositoryImpl;


import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepositoryImpl repository;


    @Override
    public TransferResponse doTransfer(CreditCard creditCard) {
        String operationId = String.valueOf(repository.getId());
        String cartFrom = creditCard.cardFromNumber();
        String cartTo = creditCard.cardToNumber();
        String CvvCode = creditCard.cardFromCVV();
        String date = creditCard.cardFromValidTill();
        String currency = creditCard.amount().currency();
        int amount = creditCard.amount().value() / 100;

        cardNumberVerification(cartFrom, cartTo);
        cardCvvVerification(CvvCode);
        cardDataVerification(date);
        moneyInputValid(amount);
        double resultCommission = commission(amount);
        repository.addCard(operationId, creditCard);
        log.info("Новый перевод : OperationId {}, CardFrom {}, CardTo {}, Amount {}, Commission {}, Currency {}, Status {}",
                operationId, cartFrom, cartTo, amount, resultCommission, currency, "Успешно");

        return new TransferResponse(operationId);
    }

    @Override
    public void cardNumberVerification(String cardFrom, String cardTo) {
        if (cardFrom.isEmpty() && cardTo.isEmpty()) {
            throw new InvalidCardData("Номер карты не может быть пустым");
        } else if (!cardFrom.matches("[0-9]{16}")) {
            throw new InvalidCardData("Номер карты отправителя должна быть из 16 цифр");
        } else if (!cardTo.matches("[0-9]{16}")) {
            throw new InvalidCardData("Номер карты получателя должна быть из 16 цифр");
        }
        if (cardFrom.equals(cardTo)) {
            throw new InvalidCardData("Номер карты отправителя и получателя должны быть разными");
        }

    }

    @Override
    public void cardCvvVerification(String Cvv) {
        if (!Cvv.matches("[0-9]{3}")) {
            throw new InvalidCardData("CVV должен состоять из 3 цифр");
        }

    }

    @SneakyThrows
    @Override
    public void cardDataVerification(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        simpleDateFormat.setLenient(false);
        Date expiry = simpleDateFormat.parse(date);
        boolean expired = expiry.before(new Date());
        if (expired) {
            throw new InvalidCardData("Неверно указан срок действия карты");
        }

    }

    @Override
    public TransferResponse doConfirm(ConfirmOperation operation) {
        if (operation.code().equals("0000")) {
            return new TransferResponse(operation.operationId());
        }
        throw new ConfirmOperationException("Неверный код");
    }

    @Override
    public int moneyInputValid(int money) {
        if (money <= 0) {
            throw new TransferException("Сумма должна быть больше 0");
        }
        return money;
    }

    @Override
    public double commission(double money) {
        return money / 100;
    }
}