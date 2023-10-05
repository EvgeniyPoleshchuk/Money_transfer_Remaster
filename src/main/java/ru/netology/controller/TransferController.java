package ru.netoology.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netoology.model.ConfirmOperation;
import ru.netoology.model.CreditCart;
import ru.netoology.web.response.TransferResponse;
import ru.netoology.service.TransferService;

@RestController
@Slf4j
@CrossOrigin()
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("transfer")
    public ResponseEntity<TransferResponse> response (@RequestBody CreditCart creditCart){
        return ResponseEntity.ok(transferService.doTransfer(creditCart));
    }
    @PostMapping("confirmOperation")
    public ResponseEntity<TransferResponse> responseEntity(@RequestBody ConfirmOperation operation){
        return ResponseEntity.ok(transferService.doConfirm(operation));

    }

}
