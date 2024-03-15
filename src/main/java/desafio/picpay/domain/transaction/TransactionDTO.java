package desafio.picpay.domain.transaction;

import java.math.BigDecimal;

public record TransactionDTO(
        BigDecimal amout,
        Long payerId,
        Long payeeId
) {
}
