package kioskService;

import Domain.Category;
import Domain.Option;
import Domain.Order;

import java.util.List;

// 옵션 선택 서비스 인터페이스
// 메뉴 옵션 조회 및 주문 상태 변경 기능을 정의
public interface SelectOptionService {
    public List<Option> completeOption(Category type, String optionName);
    public void goMenu(Order order);
    public void goShoppingCart(Order order);
}
