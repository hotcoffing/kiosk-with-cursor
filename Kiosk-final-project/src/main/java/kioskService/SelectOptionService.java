package kioskService;

import Domain.Category;
import Domain.MenuItem;
import Domain.Option;
import Domain.Order;

import java.util.List;

public interface SelectOptionService {
    public List<Option> completeOption(Category type, String optionName);
    public void goMenu(Order order);
    public void goShoppingCart(Order order);
}
