package desafio.picpay.controller;

import desafio.picpay.domain.transaction.Transaction;
import desafio.picpay.domain.transaction.TransactionDTO;
import desafio.picpay.services.TransactionService;
import desafio.picpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Transaction save(@RequestBody TransactionDTO transactionDTO) throws Exception {
        return transactionService.save(transactionDTO);
    }

}
