package SwingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

import Domain.MenuItem;
import Domain.Order;
import Domain.OrderItem;
import Domain.OrderState;
import Static.MenuItemStatic;
import kioskService.ShoppingCartService;

// 장바구니 화면 프레임 클래스
public class ShoppingCartFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;
    private ShoppingCartService shoppingCartService;
    private Repository.ShoppingCartRepository shoppingCartRepository;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 22);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 15);
    Font itemFont = new Font(Font.DIALOG, Font.PLAIN, 13);
    Font totalFont = new Font(Font.DIALOG, Font.BOLD, 18);

    // 현대적인 색상 팔레트
    Color primaryColor = SwingGraphic.PRIMARY_COLOR;
    Color primaryDark = SwingGraphic.PRIMARY_DARK;
    Color secondaryColor = SwingGraphic.SECONDARY_COLOR;
    Color secondaryDark = SwingGraphic.SECONDARY_DARK;
    Color borderColor = SwingGraphic.BORDER_COLOR;
    Color textPrimary = SwingGraphic.TEXT_PRIMARY;
    Color dangerColor = SwingGraphic.DANGER_COLOR;

    // 컴포넌트 필드 선언
    JLabel tableNumberLabel;
    JComboBox<String> tableNumberComboBox;
    JPanel orderItemsPanel;
    JLabel totalLabel;
    JButton backButton;
    JButton orderButton;

    private Order nowOrder;

    // 장바구니 화면 초기화 생성자
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
        setBounds(0, 0, 1100, 700);
        setContentPane(contentPane);
    }

    public void setService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
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

    // 장바구니 화면 컴포넌트 초기화 메서드
    private void initShoppingCartFrame() {
        // 테이블 번호 선택
        tableNumberLabel = swingGraphic.createLabel("테이블 번호", labelFont, textPrimary);

        String[] tableNumbers = {"TB 1", "TB 2", "TB 3", "TB 4", "TB 5", "TB 6", "TB 7", "TB 8"};
        tableNumberComboBox = swingGraphic.createComboBox(tableNumbers, labelFont);
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

        // 총계 라벨
        totalLabel = swingGraphic.createLabel("총계: 0원", totalFont, textPrimary, SwingConstants.RIGHT);

        // 현대적인 버튼 생성
        backButton = swingGraphic.createModernButton("뒤로가기", 200, 38, buttonFont, primaryColor, primaryDark);
        orderButton = swingGraphic.createModernButton("주문하기", 200, 38, buttonFont, primaryColor, primaryDark);

        backButton.addActionListener(SwingSoundEventListener.click(() -> {
            if (shoppingCartService != null) {
                shoppingCartService.goChooseOrder(nowOrder);
            }
            swingController.moveSelectMenu(this);
        }));

        orderButton.addActionListener(SwingSoundEventListener.click(() -> {
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
        }));

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }

    // 장바구니 주문 항목 목록 업데이트 메서드
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
            totalLabel.setText("총계: " + total + "원");
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
        menuInfoPanel.setOpaque(false);
        menuInfoPanel.add(menuLabel, BorderLayout.NORTH);
        menuInfoPanel.add(optionTextArea, BorderLayout.CENTER);

        // 가격
        int itemPrice = item.getPriceWithOptions() * item.getQuantity();
        JLabel priceLabel = new JLabel(itemPrice + "원");
        priceLabel.setFont(itemFont);
        priceLabel.setForeground(dangerColor);

        infoPanel.add(menuInfoPanel, BorderLayout.CENTER);
        infoPanel.add(priceLabel, BorderLayout.EAST);

        // 수량 조절 버튼
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);

        Font quantityButtonFont = new Font(Font.DIALOG, Font.BOLD, 16);
        
        JButton minusButton = swingGraphic.createModernButton("-", 45, 30, quantityButtonFont, primaryColor, primaryDark);
        minusButton.addActionListener(SwingSoundEventListener.click(() -> {
            if (shoppingCartService != null) {
                shoppingCartService.minusOrderItem(item.getId());
                updateOrderItems();
            }
        }));

        JButton plusButton = swingGraphic.createModernButton("+", 45, 30, quantityButtonFont, primaryColor, primaryDark);
        plusButton.addActionListener(SwingSoundEventListener.click(() -> {
            if (shoppingCartService != null) {
                shoppingCartService.plusOrderItem(item.getId());
                updateOrderItems();
            }
        }));

        controlPanel.add(minusButton);
        controlPanel.add(plusButton);

        panel.add(imageLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.EAST);

        return panel;
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

        // 상단 패널 (테이블 번호)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)) {
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
        topPanel.add(tableNumberLabel);
        topPanel.add(tableNumberComboBox);

        // 중앙 패널 (주문 항목)
        JScrollPane itemsScrollPane = new JScrollPane(orderItemsPanel);
        itemsScrollPane.setOpaque(false);
        itemsScrollPane.getViewport().setOpaque(false);
        itemsScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "주문 내역",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            labelFont.deriveFont(Font.BOLD, 13f), primaryColor
        ));

        // 하단 패널 (총계 + 버튼)
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

        bottomPanel.add(totalLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(orderButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // 패널 적용
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(itemsScrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}
