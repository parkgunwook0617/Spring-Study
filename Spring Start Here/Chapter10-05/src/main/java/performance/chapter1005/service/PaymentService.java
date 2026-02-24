package performance.chapter1005.service;

import org.springframework.stereotype.Service;
import performance.chapter1005.exception.NotEnoughMoneyException;
import performance.chapter1005.model.PaymentDetails;

@Service
public class PaymentService {
    public PaymentDetails processPayment() {
        throw new NotEnoughMoneyException();
    }
}
