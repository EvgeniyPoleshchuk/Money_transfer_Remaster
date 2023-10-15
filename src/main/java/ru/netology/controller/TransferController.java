package ru.netology.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.dto.ConfirmOperation;
import ru.netology.model.CreditCard;
import ru.netology.dto.TransferResponse;
import ru.netology.service.TransferServiceImpl;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TransferController {
    private final TransferServiceImpl transferServiceImpl;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> response (@RequestBody CreditCard creditCard){
        return ResponseEntity.ok(transferServiceImpl.doTransfer(creditCard));
    }
    @PostMapping("/confirmOperation")
    public ResponseEntity<TransferResponse> responseEntity(@RequestBody ConfirmOperation operation){
        return ResponseEntity.ok(transferServiceImpl.doConfirm(operation));

    }

}
