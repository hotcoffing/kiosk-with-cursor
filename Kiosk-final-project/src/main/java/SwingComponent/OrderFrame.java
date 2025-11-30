package SwingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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

import Domain.MenuItem;
import Domain.Order;
import Domain.OrderItem;
import Domain.OrderState;
import Domain.OrderType;
import Domain.PaymentType;
import Static.MenuItemStatic;
import kioskService.OrderService;
import kioskService.PaymentService;

// 주문 및 결제 화면 프레임 클래스
public class OrderFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;
    private OrderService orderService;
    private PaymentService paymentService;
    private Repository.ShoppingCartRepository shoppingCartRepository;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 22);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 15);
    Font itemFont = new Font(Font.DIALOG, Font.PLAIN, 13);
    Font priceFont = new Font(Font.DIALOG, Font.BOLD, 18);
    Font paymentButtonFont = new Font(Font.DIALOG, Font.BOLD, 15);

    // 현대적인 색상 팔레트
    Color primaryColor = SwingGraphic.PRIMARY_COLOR;
    Color primaryDark = SwingGraphic.PRIMARY_DARK;
    Color secondaryColor = SwingGraphic.SECONDARY_COLOR;
    Color secondaryDark = SwingGraphic.SECONDARY_DARK;
    Color borderColor = SwingGraphic.BORDER_COLOR;
    Color textPrimary = SwingGraphic.TEXT_PRIMARY;
    Color dangerColor = SwingGraphic.DANGER_COLOR;

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

    // 주문 및 결제 화면 초기화 생성자
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
        setBounds(0, 0, 1100, 700);
        setContentPane(contentPane);
    }

    public void setServices(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    // 장바구니 저장소 설정 메서드
    public void setShoppingCartRepository(Repository.ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    // 주문 정보 설정 및 주문 항목 업데이트 메서드
    public void setOrder(Order order) {
        this.nowOrder = order;
        updateOrderItems();
    }

    // 주문 및 결제 화면 컴포넌트 초기화 메서드
    private void initOrderFrame() {
        // 제목 라벨
        Font titleFont = new Font(Font.DIALOG, Font.BOLD, 24);
        titleLabel = swingGraphic.createLabel("주문 / 결제", titleFont, textPrimary, SwingConstants.CENTER);

        // 테이블 번호 선택
        tableNumberLabel = swingGraphic.createLabel("테이블 번호", labelFont, textPrimary);

        String[] tableNumbers = {"TB 1", "TB 2", "TB 3", "TB 4", "TB 5", "TB 6", "TB 7", "TB 8"};
        tableNumberComboBox = swingGraphic.createComboBox(tableNumbers, labelFont);
        tableNumberComboBox.setSelectedItem("TB 1"); // 기본값 설정
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
        orderItemsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // 금액 라벨
        priceLabel = swingGraphic.createLabel("금액: 0원", priceFont, dangerColor);

        // 포장/매장 선택 라디오 버튼
        orderTypeButtonGroup = new ButtonGroup();
        dineInRadioButton = swingGraphic.createRadioButton("매장", labelFont, textPrimary, true); // 기본 선택
        takeoutRadioButton = swingGraphic.createRadioButton("포장", labelFont, textPrimary, false);
        
        orderTypeButtonGroup.add(dineInRadioButton);
        orderTypeButtonGroup.add(takeoutRadioButton);
        
        dineInRadioButton.addActionListener(e -> {
            nowOrder.setOrderType(OrderType.DINE_IN);
        });
        
        takeoutRadioButton.addActionListener(e -> {
            nowOrder.setOrderType(OrderType.TAKE_OUT);
        });

        // 결제 방식 버튼 패널
        paymentButtonsPanel = new JPanel(new GridLayout(1, 3, 8, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SwingGraphic.BACKGROUND_LIGHT);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(borderColor);
                g2d.setStroke(new java.awt.BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 12, 12);
                g2d.dispose();
            }
        };
        paymentButtonsPanel.setOpaque(false);
        paymentButtonsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "결제 방식 선택",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            labelFont.deriveFont(Font.BOLD, 13f), primaryColor
        ));

        JButton creditCardButton = swingGraphic.createModernButton("신용카드\n(삼성페이)", 0, 70, paymentButtonFont, primaryColor, primaryDark);
        creditCardButton.addActionListener(SwingSoundEventListener.click(() -> {
            selectedPaymentType = PaymentType.CREDIT_CARD;
            processPayment();
        }));

        JButton naverPayButton = swingGraphic.createModernButton("네이버 페이", 0, 70, paymentButtonFont, primaryColor, primaryDark);
        naverPayButton.addActionListener(SwingSoundEventListener.click(() -> {
            selectedPaymentType = PaymentType.NAVER_PAY;
            processPayment();
        }));

        JButton tossPayButton = swingGraphic.createModernButton("토스 페이", 0, 70, paymentButtonFont, primaryColor, primaryDark);
        tossPayButton.addActionListener(SwingSoundEventListener.click(() -> {
            selectedPaymentType = PaymentType.TOSS_PAY;
            processPayment();
        }));

        paymentButtonsPanel.add(creditCardButton);
        paymentButtonsPanel.add(naverPayButton);
        paymentButtonsPanel.add(tossPayButton);

        // 뒤로가기 버튼
        backButton = swingGraphic.createModernButton("뒤로 가기", 200, 38, buttonFont, primaryColor, primaryDark);
        backButton.addActionListener(SwingSoundEventListener.click(() -> {
            if (orderService != null) {
                orderService.goBack(nowOrder);
            }
            swingController.moveShoppingCart(this);
        }));

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }

    // 주문 항목 목록 업데이트 메서드
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
                emptyLabel.setForeground(SwingGraphic.TEXT_MUTED);
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
        JPanel panel = new JPanel(new BorderLayout(5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SwingGraphic.BACKGROUND_LIGHT);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(borderColor);
                g2d.setStroke(new java.awt.BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 12, 12);
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160)); // 최대 높이 제한

        // 이미지 아이콘 로드
        MenuItem menuItem = MenuItemStatic.findMenuItemByName(item.getMenuName());
        ImageIcon imageIcon = null;
        if (menuItem != null) {
            imageIcon = loadImageIcon(menuItem.getImagePath(), 130, 130);
        }
        
        // 이미지 라벨
        JLabel imageLabel = new JLabel(imageIcon, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SwingGraphic.BACKGROUND_LIGHT);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.setColor(borderColor);
                g2d.setStroke(new java.awt.BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        imageLabel.setPreferredSize(new Dimension(130, 130));
        imageLabel.setOpaque(false);
        if (imageIcon == null) {
            imageLabel.setText(menuItem == null ? "메뉴 없음" : "이미지 없음");
        }

        // 메뉴 정보 패널
        JPanel infoPanel = new JPanel(new BorderLayout(3, 3));
        infoPanel.setOpaque(false);

        // 메뉴 이름과 수량
        String menuInfo = item.getMenuName() + " x" + item.getQuantity();
        JLabel menuLabel = new JLabel(menuInfo);
        menuLabel.setFont(itemFont);
        menuLabel.setForeground(textPrimary);

        // 옵션 정보 (순수 Java/Swing 방식 - JTextArea 사용)
        String optionsStr = item.getOptionsString();
        JTextArea optionTextArea = new JTextArea();
        Font optionFont = new Font(Font.DIALOG, Font.PLAIN, 10);
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
        menuInfoPanel.setOpaque(false);
        menuInfoPanel.add(menuLabel, BorderLayout.NORTH);
        menuInfoPanel.add(optionTextArea, BorderLayout.CENTER);

        // 가격
        int itemPrice = item.getPriceWithOptions() * item.getQuantity();
        JLabel itemPriceLabel = new JLabel(itemPrice + "원");
        itemPriceLabel.setFont(itemFont);
        itemPriceLabel.setForeground(dangerColor);

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

        SwingSoundEventListener.playOrderSound();
        
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

    // 이미지 아이콘 로드 및 크기 조정 메서드
    private ImageIcon loadImageIcon(String imagePath, int width, int height) {
        try {
            if (imagePath != null && !imagePath.isEmpty() && !imagePath.equals("/")) {
                java.net.URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl != null) {
                    ImageIcon originalIcon = new ImageIcon(imageUrl);
                    Image originalImage = originalIcon.getImage();
                    Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImage);
                }
            }
        } catch (Exception e) {
            // 이미지 로드 실패 시 null 반환
        }
        return null; // 이미지가 없거나 로드 실패 시 null 반환
    }

    // 컨텐츠펜 판넬에 추가
    private void addComponentsToPanel() {
        // 그라데이션 배경
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, secondaryColor,
                    getWidth(), getHeight(), secondaryDark
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        contentPane.setLayout(new BorderLayout(5, 5));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        // 상단 패널 (제목 + 테이블 번호)
        JPanel topPanel = new JPanel(new BorderLayout(5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 230));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.dispose();
            }
        };
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(8, 12, 8, 12));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tablePanel.setOpaque(false);
        tablePanel.add(tableNumberLabel);
        tablePanel.add(tableNumberComboBox);
        topPanel.add(tablePanel, BorderLayout.CENTER);

        // 중앙 패널 (주문 항목)
        JScrollPane itemsScrollPane = new JScrollPane(orderItemsPanel);
        itemsScrollPane.setOpaque(false);
        itemsScrollPane.getViewport().setOpaque(false);
        itemsScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "주문 내역",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            labelFont.deriveFont(Font.BOLD, 13f), primaryColor
        ));

        // 하단 패널 (금액 + 포장 옵션 + 결제 방식 + 뒤로가기)
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 230));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.dispose();
            }
        };
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(8, 12, 8, 12));

        // 금액 패널
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pricePanel.setOpaque(false);
        pricePanel.add(priceLabel);
        
        // 주문 유형 선택 패널
        JPanel orderTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        orderTypePanel.setOpaque(false);
        orderTypePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "주문 유형",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            labelFont.deriveFont(Font.BOLD, 13f), primaryColor
        ));
        orderTypePanel.add(dineInRadioButton);
        orderTypePanel.add(takeoutRadioButton);
        
        JPanel topBottomPanel = new JPanel(new BorderLayout(5, 5));
        topBottomPanel.setOpaque(false);
        topBottomPanel.add(pricePanel, BorderLayout.WEST);
        topBottomPanel.add(orderTypePanel, BorderLayout.EAST);
        bottomPanel.add(topBottomPanel, BorderLayout.NORTH);

        // 결제 방식 패널
        bottomPanel.add(paymentButtonsPanel, BorderLayout.CENTER);

        // 뒤로가기 버튼 패널
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);
        bottomPanel.add(backButtonPanel, BorderLayout.SOUTH);

        // 패널 적용
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(itemsScrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}
