package performance.chapter1201.controller;

import org.springframework.web.bind.annotation.*;
import performance.chapter1201.model.Purchase;
import performance.chapter1201.repository.PurchaseRepository;

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
