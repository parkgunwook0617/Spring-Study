package performance.chapter1203.controller;

import org.springframework.web.bind.annotation.*;
import performance.chapter1203.model.Purchase;
import performance.chapter1203.repository.PurchaseRepository;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseRepository purchaseRepository;

    public PurchaseController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @PostMapping
    public void storePurchase(@RequestBody Purchase purchase) {
        purchaseRepository.storePurchase(purchase);
    }

    @GetMapping
    public List<Purchase> findPurchase() {
        return purchaseRepository.findAllPurchases();
    }
}
