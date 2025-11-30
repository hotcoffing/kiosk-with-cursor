package kioskService;

import java.util.List;

import Domain.MenuItem;
import Domain.Option;

// 금액 계산 인터페이스
public interface CalcMoneyInterface {
    public int getTotalPriceUseShoppingCart();
    public int getLocalPriceInOption(MenuItem item, List<Option> options);
}
