package kioskService;

import java.util.List;

import Domain.Category;
import Domain.Option;
import Domain.Order;

// 옵션 선택 서비스 인터페이스
public interface SelectOptionService {
    public List<Option> completeOption(Category type, String optionName);
    public void goMenu(Order order);
    public void goShoppingCart(Order order);
}
