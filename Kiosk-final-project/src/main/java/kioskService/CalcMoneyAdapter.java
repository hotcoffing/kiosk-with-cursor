package kioskService;

import java.util.List;

import Domain.MenuItem;
import Domain.Option;
import Domain.OrderItem;
import Repository.ShoppingCartRepository;

// 금액 계산 기본 구현 클래스
public class CalcMoneyAdapter implements CalcMoneyInterface {

    protected final ShoppingCartRepository shoppingCartRepository;

    // 금액 계산 어댑터 생성자
    public CalcMoneyAdapter(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
    
    // 장바구니의 모든 주문 항목 총 가격 계산 메서드
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

    // 옵션 포함 가격 계산 메서드
    @Override
    public int getLocalPriceInOption(MenuItem item, List<Option> options) {
        return 0;
    }
}
