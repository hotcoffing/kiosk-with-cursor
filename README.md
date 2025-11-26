## 추가 사항있으면 README 파일 최신화 해주세요
## EX 형식
- 11/12 : Domain 패캐지 Order Class 수정
  - 수정 부분 : (간략한 설명)
  - 수정 코드
  ```java
    수정 부분만 코드를 잘라내 붙여주세요.
  ```

---
- 11/12 Github Repository 제작 완료

- 11/21 Service 패캐지 일부 제작 
  - 실습 과제 : 추가 자료 첨부하였음
  - serive 패캐지 : 서비스 부분 일부 추가 완료 (중요 : 이벤트 객체에 클래스데이터를 담아서 swing 컨트롤러로 전송할 예정임)
  - Domain 패캐지 : 도메인 일부 수정 되었음, order 도메인 클래스는 서비스 클래스와 합쳐져야 할 것 같음

- 01/XX : Swing UI 컴포넌트 구현 완료
  - 수정 부분 : 메뉴 선택, 옵션 선택, 장바구니, 주문/결제 화면 구현
  - 수정 코드
  ```java
  // SelectMenuFrame.java - 메뉴 선택 화면 구현
  public SelectMenuFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
      // 메뉴 카테고리별 표시, 테이블 번호 선택, 총계 표시 기능 구현
  }
  
  // SelectOptionNewTabFrame.java - 옵션 선택 새 창 구현
  public SelectOptionNewTabFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
      // 단일/다중 옵션 선택, 실시간 가격 업데이트 기능 구현
  }
  
  // ShoppingCartFrame.java - 장바구니 화면 구현
  public ShoppingCartFrame(SwingGraphic swingGraphic, SwingController swingController) {
      // 주문 항목 표시, 수량 조절, 옵션 정보 표시 기능 구현
  }
  
  // OrderFrame.java - 주문/결제 화면 구현
  public OrderFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
      // 주문 항목 확인, 결제 방식 선택, 고객 정보 입력 기능 구현
  }
  ```

- 01/XX : Repository 인스턴스 관리 개선
  - 수정 부분 : ShoppingCartRepository 싱글톤 패턴 적용
  - 수정 코드
  ```java
  // ShoppingCartConfig.java
  private ShoppingCartRepository shoppingCartRepository;
  
  public ShoppingCartRepository shoppingCartRepository() {
      if (shoppingCartRepository == null) {
          shoppingCartRepository = new ShoppingCartRepositoryImpl();
      }
      return shoppingCartRepository;
  }
  ```

- 01/XX : Domain 패캐지 OrderItem 클래스 수정
  - 수정 부분 : 옵션 정보를 문자열로 반환하는 메서드 추가 (단일/다중 옵션 분리 표시)
  - 수정 코드
  ```java
  // OrderItem.java
  public String getOptionsString() {
      StringBuilder optionsStr = new StringBuilder();
      
      // 단일 옵션
      if (singleOption != null) {
          optionsStr.append("단일 옵션 : ").append(singleOption.getName());
      }
      
      // 다중 옵션
      if (!multipleOptions.isEmpty()) {
          if (optionsStr.length() > 0) {
              optionsStr.append("\n");
          }
          optionsStr.append("다중 옵션 : ");
          for (int i = 0; i < multipleOptions.size(); i++) {
              if (i > 0) {
                  optionsStr.append(", ");
              }
              optionsStr.append(multipleOptions.get(i).getName());
          }
      }
      
      return optionsStr.toString();
  }
  ```

- 01/XX : SelectOptionNewTabFrame 옵션 선택 버그 수정
  - 수정 부분 : 메뉴 변경 시 옵션 상태 초기화로 이전 메뉴 옵션이 다른 메뉴에 적용되지 않도록 수정
  - 수정 코드
  ```java
  // SelectOptionNewTabFrame.java
  public void setMenuItem(MenuItem menuItem) {
      this.currentMenuItem = menuItem;
      // 옵션 상태 초기화 (이전 메뉴의 옵션이 남지 않도록)
      selectedSingleOption = OptionStatic.getNormal();
      singleOptionCheckBoxes.clear();
      multipleOptionCheckBoxes.clear();
      updateMenuDisplay();
  }
  ```
  