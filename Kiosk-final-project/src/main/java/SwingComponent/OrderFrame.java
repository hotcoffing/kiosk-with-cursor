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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Config.ShoppingCartConfig;
import Domain.Order;
import Domain.OrderItem;
import Domain.OrderState;
import Domain.OrderType;
import Domain.PaymentType;
import kioskService.OrderService;
import kioskService.PaymentService;
import kioskService.PaymentServiceImpl;

public class OrderFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingAction swingAction;
    private final SwingController swingController;
    private final ShoppingCartConfig shoppingCartConfig;
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
    Color[] labelColorList = new Color[]{
            new Color(0, 0, 0),
            new Color(200, 200, 200)
    };
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
    JCheckBox takeoutCheckBox;
    JPanel paymentButtonsPanel;
    JButton backButton;

    private Order nowOrder;
    private PaymentType selectedPaymentType;

    public OrderFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController, ShoppingCartConfig shoppingCartConfig) {
        this.swingGraphic = swingGraphic;
        this.swingAction = swingAction;
        this.swingController = swingController;
        this.shoppingCartConfig = shoppingCartConfig;
        
        // ShoppingCartConfig를 통해 서비스와 레포지토리 초기화
        this.orderService = shoppingCartConfig.orderService();
        this.shoppingCartRepository = shoppingCartConfig.shoppingCartRepository();
        // PaymentService는 별도로 생성
        this.paymentService = new PaymentServiceImpl();

        // 메인 콘텐츠펜 판넬 생성
        this.contentPane = new JPanel();

        // Order 객체 초기화
        nowOrder = new Order();
        nowOrder.setOrderState(OrderState.ORDERING);

        // 컴포넌트 초기화
        initOrderFrame();

        // OrderFrame 기본 프레임 설정
        setTitle("주문 / 결제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1100, 900);
        setContentPane(contentPane);
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

        // 포장 옵션 체크박스
        takeoutCheckBox = new JCheckBox("포장");
        takeoutCheckBox.setFont(labelFont);
        takeoutCheckBox.addActionListener(e -> {
            if (takeoutCheckBox.isSelected()) {
                nowOrder.setOrderType(OrderType.TAKE_OUT);
            } else {
                nowOrder.setOrderType(OrderType.DINE_IN);
            }
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

        // 옵션 정보
        String optionsStr = item.getOptionsString();
        JLabel optionLabel = new JLabel();
        Font optionFont = new Font(Font.DIALOG, Font.PLAIN, 14);
        optionLabel.setFont(optionFont);
        optionLabel.setForeground(Color.GRAY);
        if (!optionsStr.isEmpty()) {
            optionLabel.setText("<html>" + optionsStr.replace("\n", "<br>") + "</html>");
        } else {
            optionLabel.setText("");
        }

        // 메뉴 정보와 옵션을 세로로 배치
        JPanel menuInfoPanel = new JPanel(new BorderLayout(0, 3));
        menuInfoPanel.setBackground(Color.WHITE);
        menuInfoPanel.add(menuLabel, BorderLayout.NORTH);
        menuInfoPanel.add(optionLabel, BorderLayout.CENTER);

        // 가격
        int itemPrice = item.getPriceWithOptions() * item.getQuantity();
        JLabel priceLabel = new JLabel(itemPrice + "원");
        priceLabel.setFont(itemFont);
        priceLabel.setForeground(Color.RED);

        infoPanel.add(menuInfoPanel, BorderLayout.CENTER);
        infoPanel.add(priceLabel, BorderLayout.EAST);

        panel.add(imageLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }

    private void processPayment() {
        if (nowOrder == null || selectedPaymentType == null) return;

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

        // 고객 이름 입력 받기
        String customerName = JOptionPane.showInputDialog(this, "고객 이름을 입력하세요:", "고객 정보", JOptionPane.PLAIN_MESSAGE);
        
        if (customerName == null || customerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "고객 이름을 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 주문 완료 처리
        if (orderService != null) {
            orderService.completeOrder(nowOrder, selectedPaymentType, customerName.trim());
            
            // 영수증 화면 열기
            swingController.openReceipt(this);
            
            // 주문 화면 닫기
            setVisible(false);
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
        pricePanel.add(takeoutCheckBox);
        bottomPanel.add(pricePanel, BorderLayout.NORTH);

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
