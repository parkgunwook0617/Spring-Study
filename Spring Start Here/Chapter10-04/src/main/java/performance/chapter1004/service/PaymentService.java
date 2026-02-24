package performance.chapter1004.service;

import org.springframework.stereotype.Service;
import performance.chapter1004.exception.NotEnoughMoneyException;
import performance.chapter1004.model.PaymentDetails;

@Service
public class PaymentService {
    public PaymentDetails processPayment() {
        throw new NotEnoughMoneyException();
    }
}
