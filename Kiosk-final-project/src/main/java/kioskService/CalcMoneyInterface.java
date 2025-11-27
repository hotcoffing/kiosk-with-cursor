package kioskService;

import Domain.MenuItem;
import Domain.Option;

import java.util.List;

// 금액 계산 인터페이스
// 장바구니 총 금액 계산 및 옵션 가격 계산 기능을 정의
public interface CalcMoneyInterface {
    public int getTotalPriceUseShoppingCart();
    public int getLocalPriceInOption(MenuItem item, List<Option> options);
}
