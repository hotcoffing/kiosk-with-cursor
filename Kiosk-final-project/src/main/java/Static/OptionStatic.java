package Static;

import Domain.Option;
import Domain.OptionType;
import static Domain.OptionType.MULTIPLE;
import static Domain.OptionType.SINGLE;

// 옵션 정적 데이터 클래스
// 모든 메뉴 옵션(단일/복수 선택 옵션)을 정적으로 정의하고 제공
public class OptionStatic {
    private static final Option normal = createOption("기본", 0, SINGLE);
    private static final Option boneless = createOption("순살", 2000, SINGLE);
    private static final Option combo = createOption("콤보", 3000, SINGLE);
    private static final Option wingbong = createOption("윙봉", 3000, SINGLE);
    private static final Option onlyleg = createOption("다리만", 4000, SINGLE);
    private static final Option coke = createOption("콜라", 1000, MULTIPLE);
    private static final Option cider = createOption("사이다", 1000, MULTIPLE);

    private static final Option[] chickenOption = new Option[] {
        normal, boneless, combo, wingbong, onlyleg, coke, cider
    };

    private OptionStatic() {
    }

    private static Option createOption (String name, int price, OptionType type) {
        return new Option(name, price, type);
    }

    public static Option getNormal() {
        return normal;
    }

    public static Option getBoneless() {
        return boneless;
    }

    public static Option getCombo() {
        return combo;
    }

    public static Option getWingbong() {
        return wingbong;
    }

    public static Option getOnlyleg() {
        return onlyleg;
    }

    public static Option getCoke() {
        return coke;
    }

    public static Option getCider() {
        return cider;
    }

    public static Option [] getChickenOption() {
        return chickenOption;
    }
}
