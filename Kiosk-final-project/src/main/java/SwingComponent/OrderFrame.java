package SwingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Domain.Order;
import Domain.OrderItem;
import Domain.OrderState;
import Domain.OrderType;
import Domain.PaymentType;
import kioskService.OrderService;
import kioskService.PaymentService;

// 주문/결제 프레임 클래스
// 주문 내역 확인, 주문 유형 선택(매장/포장), 결제 수단 선택 및 주문 완료 처리 화면
public class OrderFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;
    private OrderService orderService;
    private PaymentService paymentService;
    private Repository.ShoppingCartRepository shoppingCartRepository;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 30);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 20);
    Font itemFont = new Font(Font.DIALOG, Font.PLAIN, 18);
    Font priceFont = new Font(Font.DIALOG, Font.BOLD, 24);
    Font paymentButtonFont = new Font(Font.DIALOG, Font.BOLD, 20);

    // 컬러 리스트 { ForeGround, Border, BackGround }
    Color[] buttonColorList = new Color[]{
            new Color(0, 0, 0),
            new Color(100, 100, 100),
            new Color(240, 240, 240)
    };

    // 컴포넌트 필드 선언
    JLabel titleLabel;
    JLabel tableNumberLabel;
    JComboBox<String> tableNumberComboBox;
    JPanel orderItemsPanel;
    JLabel priceLabel;
    JRadioButton dineInRadioButton;
    JRadioButton takeoutRadioButton;
    ButtonGroup orderTypeButtonGroup;
    JPanel paymentButtonsPanel;
    JButton backButton;

    private Order nowOrder;
    private PaymentType selectedPaymentType;

    public OrderFrame(SwingGraphic swingGraphic, SwingController swingController) {
        this.swingGraphic = swingGraphic;
        this.swingController = swingController;

        // 메인 콘텐츠펜 판넬 생성
        this.contentPane = new JPanel();

        // Order 객체 초기화
        nowOrder = new Order();
        nowOrder.setOrderState(OrderState.ORDERING);
        nowOrder.setTableNumber(1); // 기본값 TB 1
        nowOrder.setOrderType(OrderType.DINE_IN); // 기본값 매장

        // 컴포넌트 초기화
        initOrderFrame();

        // OrderFrame 기본 프레임 설정
        setTitle("주문 / 결제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1100, 900);
        setContentPane(contentPane);
    }

    public void setServices(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public void setShoppingCartRepository(Repository.ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void setOrder(Order order) {
        this.nowOrder = order;
        updateOrderItems();
    }

    private void initOrderFrame() {
        // 제목 라벨
        titleLabel = new JLabel("주문 / 결제", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 28));

        // 테이블 번호 선택
        tableNumberLabel = new JLabel("테이블 번호");
        tableNumberLabel.setFont(labelFont);

        String[] tableNumbers = {"TB 1", "TB 2", "TB 3", "TB 4", "TB 5", "TB 6", "TB 7", "TB 8"};
        tableNumberComboBox = new JComboBox<>(tableNumbers);
        tableNumberComboBox.setSelectedItem("TB 1"); // 기본값 설정
        tableNumberComboBox.setFont(labelFont);
        tableNumberComboBox.addActionListener(e -> {
            String selected = (String) tableNumberComboBox.getSelectedItem();
            if (selected != null) {
                String tableNum = selected.replace("TB ", "");
                nowOrder.setTableNumber(Integer.parseInt(tableNum));
            }
        });

        // 주문 항목 패널
        orderItemsPanel = new JPanel();
        orderItemsPanel.setLayout(new BoxLayout(orderItemsPanel, BoxLayout.Y_AXIS));
        orderItemsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 금액 라벨
        priceLabel = new JLabel("금액: 0원");
        priceLabel.setFont(priceFont);
        priceLabel.setForeground(Color.RED);

        // 포장/매장 선택 라디오 버튼
        orderTypeButtonGroup = new ButtonGroup();
        dineInRadioButton = new JRadioButton("매장", true); // 기본 선택
        takeoutRadioButton = new JRadioButton("포장", false);
        dineInRadioButton.setFont(labelFont);
        takeoutRadioButton.setFont(labelFont);
        
        orderTypeButtonGroup.add(dineInRadioButton);
        orderTypeButtonGroup.add(takeoutRadioButton);
        
        dineInRadioButton.addActionListener(e -> {
            nowOrder.setOrderType(OrderType.DINE_IN);
        });
        
        takeoutRadioButton.addActionListener(e -> {
            nowOrder.setOrderType(OrderType.TAKE_OUT);
        });

        // 결제 방식 버튼 패널
        paymentButtonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        paymentButtonsPanel.setBorder(BorderFactory.createTitledBorder("결제 방식 선택"));

        JButton creditCardButton = new JButton("신용카드\n(삼성페이)");
        creditCardButton.setFont(paymentButtonFont);
        creditCardButton.addActionListener(e -> {
            selectedPaymentType = PaymentType.CREDIT_CARD;
            processPayment();
        });

        JButton naverPayButton = new JButton("네이버 페이");
        naverPayButton.setFont(paymentButtonFont);
        naverPayButton.addActionListener(e -> {
            selectedPaymentType = PaymentType.NAVER_PAY;
            processPayment();
        });

        JButton tossPayButton = new JButton("토스 페이");
        tossPayButton.setFont(paymentButtonFont);
        tossPayButton.addActionListener(e -> {
            selectedPaymentType = PaymentType.TOSS_PAY;
            processPayment();
        });

        paymentButtonsPanel.add(creditCardButton);
        paymentButtonsPanel.add(naverPayButton);
        paymentButtonsPanel.add(tossPayButton);

        // 뒤로가기 버튼
        backButton = swingGraphic.makeButton("뒤로 가기", 200, 50, buttonFont, buttonColorList);
        backButton.addActionListener(e -> {
            if (orderService != null) {
                orderService.goBack(nowOrder);
            }
            swingController.moveShoppingCart(this);
        });

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }

    public void updateOrderItems() {
        orderItemsPanel.removeAll();

        if (orderService instanceof kioskService.CalcMoneyInterface) {
            kioskService.CalcMoneyInterface calcMoney = (kioskService.CalcMoneyInterface) orderService;
            
            // 장바구니에서 주문 항목 가져오기 (Config를 통해 주입받은 Repository 인스턴스 사용)
            if (shoppingCartRepository == null) {
                return;
            }
            List<OrderItem> orderItems = shoppingCartRepository.getAllOrderItems();

            if (orderItems.isEmpty()) {
                JLabel emptyLabel = new JLabel("주문 항목이 없습니다.", SwingConstants.CENTER);
                emptyLabel.setFont(itemFont);
                orderItemsPanel.add(emptyLabel);
            } else {
                for (OrderItem item : orderItems) {
                    JPanel itemPanel = createOrderItemPanel(item);
                    orderItemsPanel.add(itemPanel);
                }
            }

            // 총계 업데이트
            int total = calcMoney.getTotalPriceUseShoppingCart();
            priceLabel.setText("금액: " + total + "원");
        }

        orderItemsPanel.revalidate();
        orderItemsPanel.repaint();
    }

    private JPanel createOrderItemPanel(OrderItem item) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // 최대 높이 제한

        // 이미지 라벨 (임시)
        JLabel imageLabel = new JLabel("사진", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.WHITE);

        // 메뉴 정보 패널
        JPanel infoPanel = new JPanel(new BorderLayout(5, 5));
        infoPanel.setBackground(Color.WHITE);

        // 메뉴 이름과 수량
        String menuInfo = item.getMenuName() + " x" + item.getQuantity();
        JLabel menuLabel = new JLabel(menuInfo);
        menuLabel.setFont(itemFont);

        // 옵션 정보 (순수 Java/Swing 방식 - JTextArea 사용)
        String optionsStr = item.getOptionsString();
        JTextArea optionTextArea = new JTextArea();
        Font optionFont = new Font(Font.DIALOG, Font.PLAIN, 14);
        optionTextArea.setFont(optionFont);
        optionTextArea.setForeground(Color.GRAY);
        optionTextArea.setBackground(Color.WHITE);
        optionTextArea.setEditable(false);
        optionTextArea.setFocusable(false);
        optionTextArea.setOpaque(false);
        optionTextArea.setBorder(null);
        if (!optionsStr.isEmpty()) {
            optionTextArea.setText(optionsStr);
        } else {
            optionTextArea.setText("");
        }

        // 메뉴 정보와 옵션을 세로로 배치
        JPanel menuInfoPanel = new JPanel(new BorderLayout(0, 3));
        menuInfoPanel.setBackground(Color.WHITE);
        menuInfoPanel.add(menuLabel, BorderLayout.NORTH);
        menuInfoPanel.add(optionTextArea, BorderLayout.CENTER);

        // 가격
        int itemPrice = item.getPriceWithOptions() * item.getQuantity();
        JLabel itemPriceLabel = new JLabel(itemPrice + "원");
        itemPriceLabel.setFont(itemFont);
        itemPriceLabel.setForeground(Color.RED);

        infoPanel.add(menuInfoPanel, BorderLayout.CENTER);
        infoPanel.add(itemPriceLabel, BorderLayout.EAST);

        panel.add(imageLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }

    private void processPayment() {
        if (nowOrder == null || selectedPaymentType == null) return;

        // Order 상태 확인 및 재설정 (ORDERING 상태가 아니면 재설정)
        if (nowOrder.getOrderState() != OrderState.ORDERING) {
            nowOrder.setOrderState(OrderState.ORDERING);
        }

        // 장바구니가 비어있는지 확인
        if (shoppingCartRepository != null) {
            List<OrderItem> orderItems = shoppingCartRepository.getAllOrderItems();
            
            if (orderItems == null || orderItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "장바구니가 비어있습니다.\n메뉴를 선택해주세요.", 
                    "주문 불가", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // 주문자 이름 입력 받기
        String customerName = JOptionPane.showInputDialog(this, "주문자 이름을 입력하세요:", "주문자 정보", JOptionPane.PLAIN_MESSAGE);

        if (customerName == null) {
            return;
        }

        customerName = customerName.trim();
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "주문자 이름을 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 주문 완료 처리
        if (orderService != null) {
            orderService.completeOrder(nowOrder, selectedPaymentType, customerName);

            // 저장된 주문 ID 가져오기 (addOrder에서 업데이트된 ID)
            Long savedOrderId = null;
            if (paymentService != null) {
                savedOrderId = paymentService.saveOrder(nowOrder);
            }
            
            // 저장 실패 시 현재 ID 사용
            if (savedOrderId == null) {
                savedOrderId = nowOrder.getId();
            }
            
            // 새로운 Order 객체 생성 (다음 주문을 위해)
            // Order의 기본 생성자는 CANCELED 상태로 초기화하므로 ORDERING으로 변경 필요
            nowOrder = new Order();
            nowOrder.setOrderState(OrderState.ORDERING);
            nowOrder.setTableNumber(1); // 기본값 TB 1
            nowOrder.setOrderType(OrderType.DINE_IN); // 기본값 매장
            setOrder(nowOrder);
            
            // 라디오 버튼 초기화
            dineInRadioButton.setSelected(true);
            takeoutRadioButton.setSelected(false);

            swingController.openReceipt(this, savedOrderId);
            swingController.moveStartFrame(this);
        }
    }

    // 컨텐츠펜 판넬에 추가
    private void addComponentsToPanel() {
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 상단 패널 (제목 + 테이블 번호)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(tableNumberLabel);
        tablePanel.add(tableNumberComboBox);
        topPanel.add(tablePanel, BorderLayout.CENTER);

        // 중앙 패널 (주문 항목)
        JScrollPane itemsScrollPane = new JScrollPane(orderItemsPanel);
        itemsScrollPane.setBorder(BorderFactory.createTitledBorder("주문 내역"));

        // 하단 패널 (금액 + 포장 옵션 + 결제 방식 + 뒤로가기)
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);

        // 금액 패널
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pricePanel.setBackground(Color.WHITE);
        pricePanel.add(priceLabel);
        
        // 주문 유형 선택 패널
        JPanel orderTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        orderTypePanel.setBackground(Color.WHITE);
        orderTypePanel.setBorder(BorderFactory.createTitledBorder("주문 유형"));
        orderTypePanel.add(dineInRadioButton);
        orderTypePanel.add(takeoutRadioButton);
        
        JPanel topBottomPanel = new JPanel(new BorderLayout(10, 10));
        topBottomPanel.setBackground(Color.WHITE);
        topBottomPanel.add(pricePanel, BorderLayout.WEST);
        topBottomPanel.add(orderTypePanel, BorderLayout.EAST);
        bottomPanel.add(topBottomPanel, BorderLayout.NORTH);

        // 결제 방식 패널
        bottomPanel.add(paymentButtonsPanel, BorderLayout.CENTER);

        // 뒤로가기 버튼 패널
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);
        bottomPanel.add(backButtonPanel, BorderLayout.SOUTH);

        // 패널 적용
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(itemsScrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}
