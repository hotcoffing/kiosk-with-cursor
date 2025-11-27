package Domain;

// 주문 상태 열거형
// 주문의 진행 단계를 나타내는 상태값 (메인, 주문 선택, 장바구니, 주문 중, 주문 완료, 취소)
public enum OrderState {
    MAIN,
    CHOOSE_ORDER,
    SHOPPING_CART,
    ORDERING,
    ORDERED,
    CANCELED
}
