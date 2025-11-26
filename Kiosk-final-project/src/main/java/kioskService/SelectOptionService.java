package kioskService;

import Domain.*;
import java.util.*;

public interface SelectOptionService {
    public List<Option> completeOption(Category type, String optionName);
    public void goMenu(Order order);
    public void goShoppingCart(Order order);
}
