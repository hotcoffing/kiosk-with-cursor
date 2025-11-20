package kioskService;

import Domain.Category;
import Domain.MenuItem;
import Domain.Option;

import java.util.List;

public interface SelectOptionService {
    public List<Option> completeOption(Category type, String optionName);
    public int getLocalMoney(MenuItem item);
}
