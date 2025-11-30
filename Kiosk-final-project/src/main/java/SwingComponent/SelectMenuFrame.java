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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Domain.MenuItem;
import Domain.Order;
import Domain.OrderItem;
import Domain.OrderState;
import Static.MenuItemStatic;
import kioskService.SelectMenuService;
import kioskService.ShoppingCartService;

// 메뉴 선택 화면 프레임 클래스
public class SelectMenuFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;
    private SelectMenuService selectMenuService;
    private ShoppingCartService shoppingCartService;
    private Repository.ShoppingCartRepository shoppingCartRepository;

    JPanel contentPane;
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 22);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 15);
    Font menuItemFont = new Font(Font.DIALOG, Font.PLAIN, 12);
    Font totalFont = new Font(Font.DIALOG, Font.BOLD, 18);
    Color primaryColor = SwingGraphic.PRIMARY_COLOR;
    Color primaryDark = SwingGraphic.PRIMARY_DARK;
    Color secondaryColor = SwingGraphic.SECONDARY_COLOR;
    Color secondaryDark = SwingGraphic.SECONDARY_DARK;
    Color borderColor = SwingGraphic.BORDER_COLOR;
    Color textPrimary = SwingGraphic.TEXT_PRIMARY;
    Color dangerColor = SwingGraphic.DANGER_COLOR;

    JLabel tableNumberLabel;
    JComboBox<String> tableNumberComboBox;
    JList<String> categoryList;
    JPanel menuItemsPanel;
    JLabel totalLabel;
    JButton mainButton;
    JButton shoppingCartButton;
    JButton orderButton;
    
    private String selectedCategory = "치킨";
    private Order nowOrder;
    private int nowTotal = 0;

    // 메뉴 선택 화면 초기화 생성자
    public SelectMenuFrame(SwingGraphic swingGraphic, SwingController swingController) {
        this.swingGraphic = swingGraphic;
        this.swingController = swingController;
        nowOrder = new Order();
        nowOrder.setOrderState(OrderState.CHOOSE_ORDER);
        this.contentPane = new JPanel();
        initSelectMenuFrame();
        setTitle("메뉴 선택");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1100, 700);
        setContentPane(contentPane);
    }

    // 서비스 설정 메서드
    public void setServices(SelectMenuService selectMenuService, ShoppingCartService shoppingCartService) {
        this.selectMenuService = selectMenuService;
        this.shoppingCartService = shoppingCartService;
    }

    // 장바구니 저장소 설정 메서드
    public void setShoppingCartRepository(Repository.ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    // 주문 정보 설정 및 총계 업데이트 메서드
    public void setOrder(Order order) {
        this.nowOrder = order;
        updateTotal();
    }

    // 메뉴 선택 화면 컴포넌트 초기화 메서드
    private void initSelectMenuFrame() {
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

        String[] categories = {"치킨", "국물류", "안주류", "사이드", "음료/술"};
        categoryList = swingGraphic.createList(categories, labelFont);
        categoryList.setSelectedIndex(0);
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedCategory = categoryList.getSelectedValue();
                updateMenuItems();
            }
        });

        menuItemsPanel = new JPanel();
        menuItemsPanel.setLayout(new GridLayout(0, 3, 5, 5));
        menuItemsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        totalLabel = swingGraphic.createLabel("총계: 0원", totalFont, textPrimary, SwingConstants.RIGHT);
        mainButton = swingGraphic.createModernButton("메인", 150, 38, buttonFont, primaryColor, primaryDark);
        shoppingCartButton = swingGraphic.createModernButton("장바구니", 200, 38, buttonFont, primaryColor, primaryDark);
        orderButton = swingGraphic.createModernButton("주문하기", 200, 38, buttonFont, primaryColor, primaryDark);

        mainButton.addActionListener(SwingSoundEventListener.click(() -> swingController.moveStartFrame(this)));

        shoppingCartButton.addActionListener(SwingSoundEventListener.click(() -> {
            if (selectMenuService != null) {
                selectMenuService.goShoppingCart(nowOrder);
            }
            swingController.moveShoppingCart(this);
        }));

        orderButton.addActionListener(SwingSoundEventListener.click(() -> {
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
            if (selectMenuService != null) {
                selectMenuService.goOrder(nowOrder);
            }
            swingController.moveOrder(this);
        }));

        updateMenuItems();
        addComponentsToPanel();
    }

    // 선택된 카테고리에 맞는 메뉴 아이템 목록 업데이트 메서드
    private void updateMenuItems() {
        menuItemsPanel.removeAll();
        
        MenuItem[] items = null;
        if ("치킨".equals(selectedCategory)) {
            items = MenuItemStatic.getChickenList();
        } else if ("국물류".equals(selectedCategory)) {
            items = MenuItemStatic.getSoupList();
        } else if ("안주류".equals(selectedCategory)) {
            items = MenuItemStatic.getSnacksList();
        } else if ("사이드".equals(selectedCategory)) {
            items = MenuItemStatic.getSidesList();
        } else if ("음료/술".equals(selectedCategory)) {
            items = MenuItemStatic.getDrinkList();
        }

        if (items != null) {
            for (MenuItem item : items) {
                JPanel itemPanel = createMenuItemPanel(item);
                menuItemsPanel.add(itemPanel);
            }
        }

        menuItemsPanel.revalidate();
        menuItemsPanel.repaint();
    }

    // 메뉴 아이템 패널 생성 메서드
    private JPanel createMenuItemPanel(MenuItem item) {
        JPanel panel = new JPanel() {
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
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(180, 197));
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        // 이미지 아이콘 로드
        ImageIcon imageIcon = loadImageIcon(item.getImagePath(), 130, 130);
        
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
        
        // 메뉴 이름과 가격
        JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
        nameLabel.setFont(menuItemFont);
        nameLabel.setForeground(textPrimary);
        
        JLabel priceLabel = new JLabel(item.getOriginalPrice() + "원", SwingConstants.CENTER);
        priceLabel.setFont(menuItemFont);
        priceLabel.setForeground(dangerColor);

        panel.add(imageLabel, BorderLayout.CENTER);
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        panel.add(infoPanel, BorderLayout.SOUTH);

        // 클릭 이벤트 및 호버 효과
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openOptionFrame(item);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        };
        panel.addMouseListener(SwingSoundEventListener.click(mouseAdapter));

        return panel;
    }

    // 옵션 선택 화면 열기 메서드
    private void openOptionFrame(MenuItem item) {
        // 옵션 선택 새 창 열기 (메뉴 아이템 전달)
        swingController.openSelectOptionNewTab(this, item);
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
        }
        return null;
    }

    public void updateTotal() {
        if (shoppingCartService instanceof kioskService.CalcMoneyInterface) {
            kioskService.CalcMoneyInterface calcMoney = (kioskService.CalcMoneyInterface) shoppingCartService;
            nowTotal = calcMoney.getTotalPriceUseShoppingCart();
        }
        totalLabel.setText("총계: " + nowTotal + "원");
    }

    // 패널에 컴포넌트 배치 및 그라데이션 배경 설정 메서드
    private void addComponentsToPanel() {
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
        contentPane.setLayout(new BorderLayout());

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
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(tableNumberLabel);
        leftPanel.add(tableNumberComboBox);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(mainButton);
        
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.dispose();
            }
        };
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
        categoryScrollPane.setPreferredSize(new Dimension(150, 0));
        categoryScrollPane.setOpaque(false);
        categoryScrollPane.getViewport().setOpaque(false);
        categoryScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "카테고리",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            labelFont.deriveFont(Font.BOLD, 13f), primaryColor
        ));

        JScrollPane menuScrollPane = new JScrollPane(menuItemsPanel);
        menuScrollPane.setOpaque(false);
        menuScrollPane.getViewport().setOpaque(false);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "메뉴",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            labelFont.deriveFont(Font.BOLD, 13f), primaryColor
        ));

        centerPanel.add(categoryScrollPane, BorderLayout.WEST);
        centerPanel.add(menuScrollPane, BorderLayout.CENTER);

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
        buttonPanel.add(shoppingCartButton);
        buttonPanel.add(orderButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}
