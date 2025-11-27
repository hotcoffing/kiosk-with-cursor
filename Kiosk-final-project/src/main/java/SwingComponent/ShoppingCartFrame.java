package SwingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Domain.Order;
import Domain.OrderItem;
import Domain.OrderState;
import kioskService.ShoppingCartService;

// 장바구니 프레임 클래스
// 장바구니에 담긴 주문 항목을 표시하고 수량 조절, 주문하기 기능을 제공하는 화면
public class ShoppingCartFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;
    private ShoppingCartService shoppingCartService;
    private Repository.ShoppingCartRepository shoppingCartRepository;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 30);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 20);
    Font itemFont = new Font(Font.DIALOG, Font.PLAIN, 18);
    Font totalFont = new Font(Font.DIALOG, Font.BOLD, 24);

    // 컬러 리스트 { ForeGround, Border, BackGround }
    Color[] buttonColorList = new Color[]{
            new Color(0, 0, 0),
            new Color(100, 100, 100),
            new Color(240, 240, 240)
    };

    // 컴포넌트 필드 선언
    JLabel tableNumberLabel;
    JComboBox<String> tableNumberComboBox;
    JPanel orderItemsPanel;
    JLabel totalLabel;
    JButton backButton;
    JButton orderButton;

    private Order nowOrder;

    public ShoppingCartFrame(SwingGraphic swingGraphic, SwingController swingController) {
        this.swingGraphic = swingGraphic;
        this.swingController = swingController;

        // 메인 콘텐츠펜 판넬 생성
        this.contentPane = new JPanel();

        // Order 객체 초기화
        nowOrder = new Order();
        nowOrder.setOrderState(OrderState.SHOPPING_CART);

        // 컴포넌트 초기화
        initShoppingCartFrame();

        // ShoppingCartFrame 기본 프레임 설정
        setTitle("장바구니");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1100, 900);
        setContentPane(contentPane);
    }

    public void setService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public void setShoppingCartRepository(Repository.ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void setOrder(Order order) {
        this.nowOrder = order;
        updateOrderItems();
    }

    private void initShoppingCartFrame() {
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

        // 총계 라벨
        totalLabel = new JLabel("총계: 0원");
        totalLabel.setFont(totalFont);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // 버튼 생성
        backButton = swingGraphic.makeButton("뒤로가기", 200, 50, buttonFont, buttonColorList);
        orderButton = swingGraphic.makeButton("주문하기", 200, 50, buttonFont, buttonColorList);

        backButton.addActionListener(e -> {
            if (shoppingCartService != null) {
                shoppingCartService.goChooseOrder(nowOrder);
            }
            swingController.moveSelectMenu(this);
        });

        orderButton.addActionListener(e -> {
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
            
            if (shoppingCartService != null) {
                shoppingCartService.goOrder(nowOrder);
            }
            swingController.moveOrder(this);
        });

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }

    public void updateOrderItems() {
        orderItemsPanel.removeAll();

        if (shoppingCartService instanceof kioskService.CalcMoneyInterface) {
            kioskService.CalcMoneyInterface calcMoney = (kioskService.CalcMoneyInterface) shoppingCartService;
            
            // 장바구니에서 주문 항목 가져오기 (Config를 통해 주입받은 Repository 인스턴스 사용)
            if (shoppingCartRepository == null) {
                return;
            }
            List<OrderItem> orderItems = shoppingCartRepository.getAllOrderItems();

            if (orderItems.isEmpty()) {
                JLabel emptyLabel = new JLabel("장바구니가 비어있습니다.", SwingConstants.CENTER);
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
            totalLabel.setText("총계: " + total + "원");
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
        JLabel priceLabel = new JLabel(itemPrice + "원");
        priceLabel.setFont(itemFont);
        priceLabel.setForeground(Color.RED);

        infoPanel.add(menuInfoPanel, BorderLayout.CENTER);
        infoPanel.add(priceLabel, BorderLayout.EAST);

        // 수량 조절 버튼
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBackground(Color.WHITE);

        Font quantityButtonFont = new Font(Font.DIALOG, Font.BOLD, 20);
        
        JButton minusButton = new JButton("-");
        minusButton.setFont(quantityButtonFont);
        minusButton.setPreferredSize(new Dimension(50, 40));
        minusButton.addActionListener(e -> {
            if (shoppingCartService != null) {
                shoppingCartService.minusOrderItem(item.getId());
                updateOrderItems();
            }
        });

        JButton plusButton = new JButton("+");
        plusButton.setFont(quantityButtonFont);
        plusButton.setPreferredSize(new Dimension(50, 40));
        plusButton.addActionListener(e -> {
            if (shoppingCartService != null) {
                shoppingCartService.plusOrderItem(item.getId());
                updateOrderItems();
            }
        });

        controlPanel.add(minusButton);
        controlPanel.add(plusButton);

        panel.add(imageLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.EAST);

        return panel;
    }

    // 컨텐츠펜 판넬에 추가
    private void addComponentsToPanel() {
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 상단 패널 (테이블 번호)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(tableNumberLabel);
        topPanel.add(tableNumberComboBox);

        // 중앙 패널 (주문 항목)
        JScrollPane itemsScrollPane = new JScrollPane(orderItemsPanel);
        itemsScrollPane.setBorder(BorderFactory.createTitledBorder("주문 내역"));

        // 하단 패널 (총계 + 버튼)
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);

        bottomPanel.add(totalLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(backButton);
        buttonPanel.add(orderButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // 패널 적용
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(itemsScrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}
