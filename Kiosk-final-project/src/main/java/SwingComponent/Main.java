package SwingComponent;

import Config.SwingConfig;

public class Main {
    // 원칙 준수 삭제 체크리스트
    // 1. 리스트 접근 Getter 메서드 삭제
    // 2. 사용하지 않는 Setter 메서드 삭제



    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.run();

        /*System.out.println("===== [Console Test Mode] 키오스크 로직 검증 시작 =====");

        // 1. 서비스 객체 생성 (스프링이 없으므로 직접 new로 생성)
        OrderService orderService = new OrderServiceImpl();
        selectMenuService menuService = new selectMenuServiceImpl();
        selectOptionService optionService = new selectOptionServiceImpl();

        // 2. 메뉴 데이터 잘 가져오는지 확인
        System.out.println("\n--- 1. 메뉴 데이터 로딩 테스트 ---");
        List<MenuCategory> categories = menuService.getAllMenuCategories();
        if (categories.isEmpty()) {
            System.out.println("Error: 메뉴 카테고리가 비어있습니다.");
            return;
        }

        // 첫 번째 카테고리(치킨)의 첫 번째 메뉴(후라이드) 가져오기
        MenuCategory chickenCategory = categories.get(0);
        MenuItem friedChicken = chickenCategory.getMenuItems().get(0);
        System.out.println("선택된 메뉴: " + friedChicken.getName() + ", 가격: " + friedChicken.getOriginalPrice());

        // 3. 주문(장바구니) 생성 테스트
        System.out.println("\n--- 2. 주문 생성 테스트 ---");
        Order myOrder = orderService.createNewOrder(1, "테스트고객");
        System.out.println("주문 객체 생성 완료. 테이블 번호: " + myOrder.getTableNumber());

        // 4. 장바구니 담기 및 옵션 테스트
        System.out.println("\n--- 3. 장바구니 담기 테스트 ---");
        // 메뉴 담기 (수량 2개)
        orderService.addMenuItemToOrder(myOrder, friedChicken, 2);

        // 방금 담은 주문 항목(OrderItem) 가져오기 (Order 클래스에 getOrderItems() 필요)
        // 테스트를 위해 잠시 OrderItem을 직접 조작한다고 가정
        // 실제로는 OrderService를 통해 옵션을 적용하는 것이 좋습니다.

        System.out.println("현재 장바구니 총 금액 (옵션 전): " + orderService.calculateTotalPrice(myOrder));

        // 5. 결과 확인
        System.out.println("\n--- 4. 최종 검증 ---");
        if (orderService.calculateTotalPrice(myOrder) > 0) {
            System.out.println("SUCCESS: 로직이 정상적으로 작동합니다!");
        } else {
            System.out.println("FAIL: 금액 계산에 문제가 있습니다.");
        }

        System.out.println("===============================================");*/
    }
}

