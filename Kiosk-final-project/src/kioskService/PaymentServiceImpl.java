package kioskService;

import Repository.ShoppingCartRepository;

public class PaymentServiceImpl implements PaymentService {

    private final ShoppingCartRepository shoppingCartRepository;

    public PaymentServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
}
