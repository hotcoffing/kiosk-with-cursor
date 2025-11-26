package SwingComponent;

import Domain.*;
import Domain.MenuItem;
import Static.*;
import kioskService.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SelectMenuFrame extends JFrame {
    // config 연결을 위한 기능 필드
    private final SwingGraphic swingGraphic;
    private final SwingAction swingAction;
    private final SwingController swingController;
    private SelectMenuService selectMenuService;
    private ShoppingCartService shoppingCartService;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 30);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 20);
    Font menuItemFont = new Font(Font.DIALOG, Font.PLAIN, 16);
    Font totalFont = new Font(Font.DIALOG, Font.BOLD, 24);

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
    JLabel tableNumberLabel;
    JComboBox<String> tableNumberComboBox;
    JList<String> categoryList;
    JPanel menuItemsPanel;
    JLabel totalLabel;
    JButton mainButton;
    JButton shoppingCartButton;
    JButton orderButton;
    
    // 현재 선택된 카테고리
    private String selectedCategory = "치킨";
    private Order currentOrder;
    private int currentTotal = 0;

    // SelectMenuFrame 생성자
    public SelectMenuFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
        // swingGraphic, swingAction, swingController 객체 config 활용
        this.swingGraphic = swingGraphic;
        this.swingAction = swingAction;
        this.swingController = swingController;
        
        // Order 객체 초기화
        currentOrder = new Order();
        currentOrder.setOrderState(OrderState.CHOOSE_ORDER);

        // 메인 콘텐츠펜 판넬 생성
        this.contentPane = new JPanel();

        // 컴포넌트 초기화
        initSelectMenuFrame();

        // SelectMenuFrame 기본 프레임 설정
        setTitle("메뉴 선택");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1100, 900);
        setContentPane(contentPane);
    }

    public void setServices(SelectMenuService selectMenuService, ShoppingCartService shoppingCartService) {
        this.selectMenuService = selectMenuService;
        this.shoppingCartService = shoppingCartService;
    }

    public void setOrder(Order order) {
        this.currentOrder = order;
        updateTotal();
    }

    private void initSelectMenuFrame() {
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
                currentOrder.setTableNumber(Integer.parseInt(tableNum));
            }
        });

        // 카테고리 리스트
        String[] categories = {"치킨", "국물류", "안주류", "사이드", "음료/술"};
        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setSelectedIndex(0);
        categoryList.setFont(labelFont);
        categoryList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedCategory = categoryList.getSelectedValue();
                updateMenuItems();
            }
        });

        // 메뉴 아이템 패널
        menuItemsPanel = new JPanel();
        menuItemsPanel.setLayout(new GridLayout(0, 3, 10, 10));
        menuItemsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 총계 라벨
        totalLabel = new JLabel("총계: 0원");
        totalLabel.setFont(totalFont);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // 버튼 생성
        mainButton = swingGraphic.makeButton("메인", 150, 50, buttonFont, buttonColorList);
        shoppingCartButton = swingGraphic.makeButton("장바구니", 200, 50, buttonFont, buttonColorList);
        orderButton = swingGraphic.makeButton("주문하기", 200, 50, buttonFont, buttonColorList);

        mainButton.addActionListener(e -> {
            swingController.moveStartFrame(this);
        });

        shoppingCartButton.addActionListener(e -> {
            if (selectMenuService != null) {
                selectMenuService.goShoppingCart(currentOrder);
            }
            swingController.moveShoppingCart(this);
        });

        orderButton.addActionListener(e -> {
            if (selectMenuService != null) {
                selectMenuService.goOrder(currentOrder);
            }
            swingController.moveOrder(this);
        });

        // 초기 메뉴 아이템 표시
        updateMenuItems();

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }

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
            
            // 안주류처럼 아이템이 3개인 경우 빈 패널을 추가하여 레이아웃 균형 맞춤
            int itemCount = items.length;
            if (itemCount == 3) {
                // 빈 패널 1개 추가하여 4개로 만들어 2행 2열 레이아웃 유지
                JPanel emptyPanel = new JPanel();
                emptyPanel.setPreferredSize(new Dimension(200, 250));
                emptyPanel.setOpaque(false);
                menuItemsPanel.add(emptyPanel);
            }
        }

        menuItemsPanel.revalidate();
        menuItemsPanel.repaint();
    }

    private JPanel createMenuItemPanel(MenuItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setPreferredSize(new Dimension(200, 250));

        // 이미지 라벨 (임시로 텍스트로 표시)
        JLabel imageLabel = new JLabel("사진", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.WHITE);
        
        // 메뉴 이름과 가격
        JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
        nameLabel.setFont(menuItemFont);
        
        JLabel priceLabel = new JLabel(item.getOriginalPrice() + "원", SwingConstants.CENTER);
        priceLabel.setFont(menuItemFont);
        priceLabel.setForeground(Color.RED);

        panel.add(imageLabel, BorderLayout.CENTER);
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        panel.add(infoPanel, BorderLayout.SOUTH);

        // 클릭 이벤트
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openOptionFrame(item);
            }
        });

        return panel;
    }

    private void openOptionFrame(MenuItem item) {
        // 옵션 선택 새 창 열기 (메뉴 아이템 전달)
        swingController.openSelectOptionNewTab(this, item);
    }

    public void updateTotal() {
        // 장바구니의 총 금액 계산
        if (shoppingCartService instanceof kioskService.CalcMoneyInterface) {
            kioskService.CalcMoneyInterface calcMoney = (kioskService.CalcMoneyInterface) shoppingCartService;
            currentTotal = calcMoney.getTotalPriceUseShoppingCart();
        }
        totalLabel.setText("총계: " + currentTotal + "원");
    }

    // 컨텐츠펜 판넬에 추가
    private void addComponentsToPanel() {
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        // 상단 패널 (테이블 번호 + 메인 버튼)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(tableNumberLabel);
        leftPanel.add(tableNumberComboBox);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(mainButton);
        
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // 중앙 패널 (카테고리 리스트 + 메뉴 아이템)
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 카테고리 리스트 (왼쪽)
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
        categoryScrollPane.setPreferredSize(new Dimension(150, 0));
        categoryScrollPane.setBorder(BorderFactory.createTitledBorder("카테고리"));

        // 메뉴 아이템 (오른쪽)
        JScrollPane menuScrollPane = new JScrollPane(menuItemsPanel);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("메뉴"));

        centerPanel.add(categoryScrollPane, BorderLayout.WEST);
        centerPanel.add(menuScrollPane, BorderLayout.CENTER);

        // 하단 패널 (총계 + 버튼)
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        bottomPanel.add(totalLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(shoppingCartButton);
        buttonPanel.add(orderButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // 패널 적용
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}
