package kioskService;

import Domain.MenuItem;
import Domain.Option;
import Domain.OrderItem;
import Repository.ShoppingCartRepository;

import java.util.List;

public class CalcMoneyAdapter implements CalcMoneyInterface {

    protected final ShoppingCartRepository shoppingCartRepository;

    // 에러 발생시 강제 종료
    public CalcMoneyAdapter() {
        shoppingCartRepository = null;
        System.out.println("Error : there is no repository");
        System.exit(0);
    }

    public CalcMoneyAdapter(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
    
    // ShoppingCart 총 금액 갱신
    @Override
    public int getTotalPriceUseShoppingCart() {
        List<OrderItem> orderItems = shoppingCartRepository.getAllOrderItems();
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getQuantity() != 0) {
                totalPrice += orderItem.getPriceWithOptions() * orderItem.getQuantity();
            }
        }

        return totalPrice;
    }

    @Override
    public int getLocalPriceInOption(MenuItem item, List<Option> options) {
        return 0;
    }
}
