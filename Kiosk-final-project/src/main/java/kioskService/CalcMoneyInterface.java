package kioskService;

import Domain.MenuItem;
import Domain.Option;

import java.util.List;

public interface CalcMoneyInterface {
    public int getTotalPriceUseShoppingCart();
    public int getLocalPriceInOption(MenuItem item, List<Option> options);
}
