package kioskService;

import Repository.ShoppingCartRepository;

public class CheckOrderListServiceImpl implements CheckOrderListService {

    private final ShoppingCartRepository shoppingCartRepository;

    public CheckOrderListServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
}
