package SwingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Domain.Category;
import Domain.MenuItem;
import Domain.Option;
import Domain.OptionType;
import Domain.Order;
import Domain.OrderItem;
import Static.OptionStatic;
import kioskService.SelectOptionService;
import kioskService.ShoppingCartService;

public class SelectOptionNewTabFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingAction swingAction;
    private final SwingController swingController;
    private SelectOptionService selectOptionService;
    private ShoppingCartService shoppingCartService;
    private Repository.ShoppingCartRepository shoppingCartRepository;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 30);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 24);
    Font optionFont = new Font(Font.DIALOG, Font.PLAIN, 18);
    Font priceFont = new Font(Font.DIALOG, Font.BOLD, 20);

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
    JLabel menuNameLabel;
    JLabel menuImageLabel;
    JPanel optionsPanel;
    JLabel priceLabel;
    JButton cancelButton;
    JButton addToCartButton;

    // 현재 선택된 메뉴 아이템
    private MenuItem currentMenuItem;
    private Order currentOrder;
    private List<JCheckBox> singleOptionCheckBoxes = new ArrayList<>();
    private List<JCheckBox> multipleOptionCheckBoxes = new ArrayList<>();
    private Option selectedSingleOption = OptionStatic.getNormal(); // 기본값

    public SelectOptionNewTabFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
        this.swingGraphic = swingGraphic;
        this.swingAction = swingAction;
        this.swingController = swingController;

        // 메인 콘텐츠펜 판넬 생성
        this.contentPane = new JPanel();

        // 컴포넌트 초기화
        initSelectOptionNewTabFrame();

        // SelectOptionNewTabFrame 기본 프레임 설정
        setTitle("옵션 선택");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(900, 0, 600, 700);
        setContentPane(contentPane);
    }

    public void setServices(SelectOptionService selectOptionService, ShoppingCartService shoppingCartService) {
        this.selectOptionService = selectOptionService;
        this.shoppingCartService = shoppingCartService;
    }

    public void setShoppingCartRepository(Repository.ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.currentMenuItem = menuItem;
        // 옵션 상태 초기화 (이전 메뉴의 옵션이 남지 않도록)
        selectedSingleOption = OptionStatic.getNormal();
        singleOptionCheckBoxes.clear();
        multipleOptionCheckBoxes.clear();
        updateMenuDisplay();
    }

    public void setOrder(Order order) {
        this.currentOrder = order;
    }

    private void initSelectOptionNewTabFrame() {
        // 메뉴 이름 라벨
        menuNameLabel = new JLabel("메뉴 선택", SwingConstants.CENTER);
        menuNameLabel.setFont(labelFont);

        // 메뉴 이미지 라벨 (임시로 텍스트로 표시)
        menuImageLabel = new JLabel("사진", SwingConstants.CENTER);
        menuImageLabel.setPreferredSize(new Dimension(200, 200));
        menuImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        menuImageLabel.setOpaque(true);
        menuImageLabel.setBackground(Color.WHITE);

        // 옵션 패널
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("옵션 선택"));

        // 금액 라벨
        priceLabel = new JLabel("금액: 0원", SwingConstants.CENTER);
        priceLabel.setFont(priceFont);
        priceLabel.setForeground(Color.RED);

        // 버튼 생성
        cancelButton = swingGraphic.makeButton("취소", 150, 50, buttonFont, buttonColorList);
        addToCartButton = swingGraphic.makeButton("장바구니", 150, 50, buttonFont, buttonColorList);

        cancelButton.addActionListener(e -> {
            // 옵션 선택 화면 닫기
            setVisible(false);
        });

        addToCartButton.addActionListener(e -> {
            addToShoppingCart();
        });

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }

    private void updateMenuDisplay() {
        if (currentMenuItem == null) return;

        menuNameLabel.setText(currentMenuItem.getName());
        
        // 옵션 체크박스 생성 (치킨 메뉴인 경우)
        if (currentMenuItem.getCategory() == Category.CHICKEN) {
            createChickenOptions();
        } else {
            // 다른 메뉴는 옵션 없음
            optionsPanel.removeAll();
            JLabel noOptionLabel = new JLabel("옵션이 없습니다.", SwingConstants.CENTER);
            noOptionLabel.setFont(optionFont);
            optionsPanel.add(noOptionLabel);
        }

        updatePrice();
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void createChickenOptions() {
        optionsPanel.removeAll();
        singleOptionCheckBoxes.clear();
        multipleOptionCheckBoxes.clear();
        
        // 단일 옵션을 기본값으로 초기화
        selectedSingleOption = OptionStatic.getNormal();

        // 단일 선택 옵션 (라디오 버튼처럼 동작)
        ButtonGroup singleOptionGroup = new ButtonGroup();
        
        Option[] options = OptionStatic.getChickenOption();
        for (Option option : options) {
            if (option.getType() == OptionType.SINGLE) {
                JCheckBox checkBox = new JCheckBox(option.getName() + " +" + option.getPrice() + "원");
                checkBox.setFont(optionFont);
                boolean isDefault = (option == OptionStatic.getNormal());
                checkBox.setSelected(isDefault); // 기본 선택
                
                // 기본 옵션이면 selectedSingleOption 업데이트
                if (isDefault) {
                    selectedSingleOption = option;
                }
                
                // 단일 옵션은 하나만 선택 가능하도록
                checkBox.addActionListener(e -> {
                    if (checkBox.isSelected()) {
                        // 다른 단일 옵션 해제
                        for (JCheckBox cb : singleOptionCheckBoxes) {
                            if (cb != checkBox) {
                                cb.setSelected(false);
                            }
                        }
                        selectedSingleOption = option;
                        updatePrice();
                    } else {
                        // 기본 옵션은 항상 선택되어야 함
                        if (option == OptionStatic.getNormal()) {
                            checkBox.setSelected(true);
                        }
                    }
                });
                
                singleOptionCheckBoxes.add(checkBox);
                singleOptionGroup.add(checkBox);
                optionsPanel.add(checkBox);
            } else {
                // 다중 선택 옵션
                JCheckBox checkBox = new JCheckBox(option.getName() + " +" + option.getPrice() + "원");
                checkBox.setFont(optionFont);
                checkBox.setSelected(false); // 다중 옵션은 기본적으로 선택되지 않음
                checkBox.addActionListener(e -> updatePrice());
                
                multipleOptionCheckBoxes.add(checkBox);
                optionsPanel.add(checkBox);
            }
        }
    }

    private void updatePrice() {
        if (currentMenuItem == null) return;

        int totalPrice = currentMenuItem.getOriginalPrice();
        
        // 단일 옵션 가격 추가
        if (selectedSingleOption != null) {
            totalPrice += selectedSingleOption.getPrice();
        }
        
        // 다중 옵션 가격 추가
        for (JCheckBox checkBox : multipleOptionCheckBoxes) {
            if (checkBox.isSelected()) {
                String optionText = checkBox.getText();
                // 옵션 이름 추출
                String optionName = optionText.split(" \\+")[0];
                Option option = findOptionByName(optionName);
                if (option != null) {
                    totalPrice += option.getPrice();
                }
            }
        }

        priceLabel.setText("금액: " + totalPrice + "원");
    }

    private Option findOptionByName(String name) {
        Option[] options = OptionStatic.getChickenOption();
        for (Option option : options) {
            if (option.getName().equals(name)) {
                return option;
            }
        }
        return null;
    }

    private void addToShoppingCart() {
        if (currentMenuItem == null || currentOrder == null) return;

        // OrderItem 생성
        OrderItem orderItem = new OrderItem(currentMenuItem);

        // 옵션이 있는 메뉴(치킨)인 경우에만 옵션 설정
        if (currentMenuItem.getCategory() == Category.CHICKEN) {
            // 옵션 설정
            List<Option> multipleOptions = new ArrayList<>();
            for (JCheckBox checkBox : multipleOptionCheckBoxes) {
                if (checkBox.isSelected()) {
                    String optionText = checkBox.getText();
                    String optionName = optionText.split(" \\+")[0];
                    Option option = findOptionByName(optionName);
                    if (option != null) {
                        multipleOptions.add(option);
                    }
                }
            }

            // 단일 옵션이 null이면 기본 옵션으로 설정
            if (selectedSingleOption == null) {
                selectedSingleOption = OptionStatic.getNormal();
            }
            
            orderItem.setOptions(selectedSingleOption, multipleOptions);
        } else {
            // 옵션이 없는 메뉴는 기본 옵션만 설정 (옵션 없음)
            orderItem.setOptions(OptionStatic.getNormal(), new ArrayList<>());
        }

        // 장바구니에 추가 (Config를 통해 주입받은 Repository 인스턴스 사용)
        if (shoppingCartRepository != null) {
            shoppingCartRepository.addOrderItem(orderItem);
        }

        // 메뉴 선택 화면으로 돌아가기
        if (selectOptionService != null) {
            selectOptionService.goMenu(currentOrder);
        }
        
        // 옵션 선택 화면 닫기
        setVisible(false);
        
        // 메뉴 선택 화면 업데이트 (총계 갱신)
        if (swingController instanceof SwingControllerImpl) {
            SwingControllerImpl controller = (SwingControllerImpl) swingController;
            controller.updateSelectMenuTotal();
        }
    }

    // 컨텐츠펜 판넬에 추가
    private void addComponentsToPanel() {
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 상단 패널 (메뉴 이름 + 이미지)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(menuNameLabel, BorderLayout.NORTH);
        
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(menuImageLabel);
        topPanel.add(imagePanel, BorderLayout.CENTER);

        // 중앙 패널 (옵션)
        JScrollPane optionsScrollPane = new JScrollPane(optionsPanel);
        optionsScrollPane.setPreferredSize(new Dimension(0, 300));

        // 하단 패널 (금액 + 버튼)
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(priceLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(cancelButton);
        buttonPanel.add(addToCartButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 패널 적용
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(optionsScrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}

