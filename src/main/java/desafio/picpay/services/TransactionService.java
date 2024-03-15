package desafio.picpay.services;

import desafio.picpay.domain.transaction.Transaction;
import desafio.picpay.domain.transaction.TransactionDTO;

import desafio.picpay.repositories.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction save(TransactionDTO transactionDTO) throws Exception {

        var payer = this.userService.findUserById(transactionDTO.payerId());
        var payee = this.userService.findUserById(transactionDTO.payeeId());

        userService.valdateUser(payer, transactionDTO.amout());

        if (!authorizeTransaction()) {
            throw new Exception("Transação não autorizada");
        }


        payer.setBalance(payer.getBalance().subtract(transactionDTO.amout()));
        payee.setBalance(payee.getBalance().add(transactionDTO.amout()));
        Transaction transaction = Transaction.builder()
                .amount(transactionDTO.amout())
                .payer(payer)
                .payee(payee)
                .transactionTime(LocalDateTime.now())
                .build();


        this.userService.save(payer);
        this.userService.save(payee);
        notificationService.sendNotification(payer, "transação realizada com sucesso");
        notificationService.sendNotification(payee, "Transação recebido com sucesso");
        return transactionRepository.save(transaction);
    }

    ;

    public boolean authorizeTransaction() {
        var response = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc",
                Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String message = (String) response.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }
        return false;
    }

    ;

}
