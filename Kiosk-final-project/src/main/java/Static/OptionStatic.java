package Static;

import Domain.Option;
import Domain.OptionType;
import static Domain.OptionType.MULTIPLE;
import static Domain.OptionType.SINGLE;

// 옵션 정적 데이터 클래스
// 모든 메뉴 옵션(단일/복수 선택 옵션) 정적 정의 및 제공 클래스
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

    // 옵션 생성 메서드
    private static Option createOption (String name, int price, OptionType type) {
        return new Option(name, price, type);
    }

    // 기본 옵션 반환 메서드
    public static Option getNormal() {
        return normal;
    }

    // 순살 옵션 반환 메서드
    public static Option getBoneless() {
        return boneless;
    }

    // 콤보 옵션 반환 메서드
    public static Option getCombo() {
        return combo;
    }

    // 윙봉 옵션 반환 메서드
    public static Option getWingbong() {
        return wingbong;
    }

    // 다리만 옵션 반환 메서드
    public static Option getOnlyleg() {
        return onlyleg;
    }

    // 콜라 옵션 반환 메서드
    public static Option getCoke() {
        return coke;
    }

    // 사이다 옵션 반환 메서드
    public static Option getCider() {
        return cider;
    }

    // 치킨 옵션 목록 반환 메서드
    public static Option [] getChickenOption() {
        return chickenOption;
    }
}
