package ru.netology.service;

import ru.netology.dto.ConfirmOperation;
import ru.netology.dto.TransferResponse;
import ru.netology.model.CreditCard;

public interface TransferService {
    TransferResponse doTransfer(CreditCard creditCard);
    void cardNumberVerification(String cardFrom,String cardTo);
    void cardCvvVerification(String Cvv);
    void cardDataVerification(String date);
    TransferResponse doConfirm(ConfirmOperation operation);
    int moneyInputValid(int money);
    double commission(double money);
}
