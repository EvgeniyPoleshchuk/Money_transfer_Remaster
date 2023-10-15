package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.CreditCard;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepositoryImpl implements TransferRepository {
    private final AtomicInteger operationId = new AtomicInteger(0);
    private final Map<String, CreditCard> cardList = new ConcurrentHashMap<>();


    @Override
    public void addCard(String operation, CreditCard cart) {
        cardList.put(operation, cart);
    }

    @Override
    public Integer getId() {
        return operationId.incrementAndGet();
    }
}
