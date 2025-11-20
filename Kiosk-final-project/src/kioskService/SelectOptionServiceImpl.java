package kioskService;

import Domain.Category;
import Domain.MenuItem;
import Domain.Option;
import Repository.ShoppingCartRepository;
import Static.OptionStatic;

import java.util.ArrayList;
import java.util.List;

public class SelectOptionServiceImpl implements SelectOptionService {
    private final ShoppingCartRepository shoppingCartRepository;

    public SelectOptionServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
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
    public int getLocalMoney(MenuItem item) {
        int localMoney = 0;

        for (Option option : options) {
            localMoney += option.getPrice();
        }

        return item.getOriginalPrice() + localMoney;
    }
}
