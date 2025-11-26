package kioskService;

import Domain.*;
import java.util.*;

public interface CalcMoneyInterface {
    public int getTotalPriceUseShoppingCart();
    public int getLocalPriceInOption(MenuItem item, List<Option> options);
}
