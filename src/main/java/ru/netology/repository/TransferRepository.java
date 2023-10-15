package ru.netology.repository;

import ru.netology.model.CreditCard;

public interface TransferRepository {
    void addCard(String operation, CreditCard cart);
    Integer getId();
}
