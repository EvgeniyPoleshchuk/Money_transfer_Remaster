package ru.netoology.repository;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;
import ru.netoology.exception.ConfirmOperationException;
import ru.netoology.model.ConfirmOperation;
import ru.netoology.model.CreditCart;
import ru.netoology.web.response.TransferResponse;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Slf4j
public class TransferRepository {
    private final AtomicInteger integer = new AtomicInteger(1);
    private final Map<Integer, CreditCart> cardList = new ConcurrentHashMap<>();

    public TransferResponse addCard(CreditCart creditCart) {
        cardList.put(integer.incrementAndGet(), creditCart);
        log.info("Новый перевод : OperationID {} CardFrom {} CardTo {} amount {} currency {}",
                integer.get(), creditCart.getCardFromNumber(), creditCart.getCardFromNumber()
                , creditCart.getAmount(), creditCart.getCurrency());
        return new TransferResponse(integer.toString());

    }

    public TransferResponse confirmOperation(ConfirmOperation operation) {
        if (operation.getCode().equals("0000")) {
            return new TransferResponse(operation.getOperationId());
        }
        throw new ConfirmOperationException("Неверный код");
    }

    public Integer getId() {
        return integer.get();
    }

}
