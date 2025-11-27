package kioskService;

import Domain.*;
import Repository.*;
import Static.*;

import java.util.*;

import static Domain.OrderState.*;

// 옵션 선택 서비스 구현 클래스
// 메뉴 옵션을 조회하고 장바구니에 추가하는 기능을 구현
public class SelectOptionServiceImpl extends CalcMoneyAdapter implements SelectOptionService {

    public SelectOptionServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        super(shoppingCartRepository);
    }

    List<Option> options = new ArrayList<>();

    @Override
    public List<Option> completeOption(Category type, String optionName) {
        if (type.equals(Category.CHICKEN)) {
            // 단일 옵션
            if (optionName.equals("기본")) {
                options.add(OptionStatic.getNormal());
            }
            else if (optionName.equals("순살")) {
                options.add(OptionStatic.getBoneless());
            }
            else if (optionName.equals("콤보")) {
                options.add(OptionStatic.getCombo());
            }
            else if (optionName.equals("윙봉")) {
                options.add(OptionStatic.getWingbong());
            }
            else if (optionName.equals("다리만")) {
                options.add(OptionStatic.getOnlyleg());
            }
            
            // 중복 가능 옵션
            if (optionName.equals("콜라")) {
                options.add(OptionStatic.getCoke());
            }
            if (optionName.equals("사이다")) {
                options.add(OptionStatic.getCider());
            }
        }

        return null;
    }

    @Override
    public int getLocalPriceInOption(MenuItem item, List<Option> options) {
        int optionMoney = 0;

        for (Option option : options) {
            optionMoney += option.getPrice();
        }

        return item.getOriginalPrice() + optionMoney;
    }

    @Override
    public void goMenu(Order order) {
        order.setOrderState(CHOOSE_ORDER);
    }

    @Override
    public void goShoppingCart(Order order) {
        order.setOrderState(SHOPPING_CART);
    }
}
