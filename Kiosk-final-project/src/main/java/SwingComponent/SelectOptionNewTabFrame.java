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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

// 메뉴 옵션 선택 화면 프레임 클래스
public class SelectOptionNewTabFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;
    private SelectOptionService selectOptionService;
    private Repository.ShoppingCartRepository shoppingCartRepository;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 22);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 18);
    Font optionFont = new Font(Font.DIALOG, Font.PLAIN, 13);
    Font priceFont = new Font(Font.DIALOG, Font.BOLD, 15);

    // 현대적인 색상 팔레트
    Color primaryColor = SwingGraphic.PRIMARY_COLOR;
    Color primaryDark = SwingGraphic.PRIMARY_DARK;
    Color secondaryColor = SwingGraphic.SECONDARY_COLOR;
    Color secondaryDark = SwingGraphic.SECONDARY_DARK;
    Color borderColor = SwingGraphic.BORDER_COLOR;
    Color textPrimary = SwingGraphic.TEXT_PRIMARY;
    Color dangerColor = SwingGraphic.DANGER_COLOR;

    // 컴포넌트 필드 선언
    JLabel menuNameLabel;
    JLabel menuImageLabel;
    JPanel optionsPanel;
    JLabel priceLabel;
    JButton cancelButton;
    JButton addToCartButton;

    // 현재 선택된 메뉴 아이템
    private MenuItem nowMenuItem;
    private Order nowOrder;
    private final List<JRadioButton> singleOptionRadioButtons = new ArrayList<>();
    private final List<JCheckBox> multipleOptionCheckBoxes = new ArrayList<>();
    private Option selectedSingleOption = OptionStatic.getNormal(); // 기본값

    // 옵션 선택 화면 초기화 생성자
    public SelectOptionNewTabFrame(SwingGraphic swingGraphic, SwingController swingController) {
        this.swingGraphic = swingGraphic;
        this.swingController = swingController;

        // 메인 콘텐츠펜 판넬 생성
        this.contentPane = new JPanel();

        // 컴포넌트 초기화
        initSelectOptionNewTabFrame();

        // SelectOptionNewTabFrame 기본 프레임 설정
        setTitle("옵션 선택");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(250, 30, 600, 670);
        setContentPane(contentPane);
    }

    // 옵션 선택 서비스 설정 메서드
    public void setServices(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

    // 장바구니 저장소 설정 메서드
    public void setShoppingCartRepository(Repository.ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    // 메뉴 아이템 설정 및 옵션 초기화 메서드
    public void setMenuItem(MenuItem menuItem) {
        this.nowMenuItem = menuItem;
        // 옵션 상태 초기화 (이전 메뉴의 옵션이 남지 않도록)
        selectedSingleOption = OptionStatic.getNormal();
        singleOptionRadioButtons.clear();
        multipleOptionCheckBoxes.clear();
        updateMenuDisplay();
    }

    // 주문 정보 설정 메서드
    public void setOrder(Order order) {
        this.nowOrder = order;
    }

    // 옵션 선택 화면 컴포넌트 초기화 메서드
    private void initSelectOptionNewTabFrame() {
        // 메뉴 이름 라벨
        menuNameLabel = new JLabel("메뉴 선택", SwingConstants.CENTER);
        menuNameLabel.setFont(labelFont);
        menuNameLabel.setForeground(textPrimary);

        // 메뉴 이미지 라벨
        menuImageLabel = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SwingGraphic.BACKGROUND_LIGHT);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(borderColor);
                g2d.setStroke(new java.awt.BasicStroke(3));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 12, 12);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        menuImageLabel.setPreferredSize(new Dimension(150, 150));
        menuImageLabel.setOpaque(false);

        // 옵션 패널
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "옵션 선택",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            labelFont.deriveFont(Font.BOLD, 13f), primaryColor
        ));

        // 금액 라벨
        priceLabel = new JLabel("금액: 0원", SwingConstants.CENTER);
        priceLabel.setFont(priceFont);
        priceLabel.setForeground(dangerColor);

        // 현대적인 버튼 생성
        cancelButton = swingGraphic.createModernButton("취소", 150, 38, buttonFont, 
            new Color(113, 128, 150), new Color(74, 85, 104)); // 회색 계열
        addToCartButton = swingGraphic.createModernButton("장바구니", 150, 38, buttonFont, primaryColor, primaryDark);

        cancelButton.addActionListener(SwingSoundEventListener.click(() -> setVisible(false)));

        addToCartButton.addActionListener(SwingSoundEventListener.click(() -> addToShoppingCart()));

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }

    // 메뉴 정보 및 옵션 표시 업데이트 메서드
    private void updateMenuDisplay() {
        if (nowMenuItem == null) return;

        menuNameLabel.setText(nowMenuItem.getName());
        
        // 이미지 아이콘 로드 및 설정
        ImageIcon imageIcon = loadImageIcon(nowMenuItem.getImagePath(), 150, 150);
        menuImageLabel.setIcon(imageIcon);
        if (imageIcon == null) {
            menuImageLabel.setText("이미지 없음");
        }
        
        // 옵션 체크박스 생성 (치킨 메뉴인 경우)
        if (nowMenuItem.getCategory() == Category.CHICKEN) {
            createChickenOptions();
        } else {
            // 다른 메뉴는 옵션 없음
            optionsPanel.removeAll();
            JLabel noOptionLabel = new JLabel("옵션이 없습니다.", SwingConstants.CENTER);
            noOptionLabel.setFont(optionFont);
            noOptionLabel.setForeground(SwingGraphic.TEXT_MUTED);
            optionsPanel.add(noOptionLabel);
        }

        updatePrice();
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    // 치킨 메뉴 옵션 라디오 버튼 및 체크박스 생성 메서드
    private void createChickenOptions() {
        optionsPanel.removeAll();
        singleOptionRadioButtons.clear();
        multipleOptionCheckBoxes.clear();
        
        // 단일 옵션을 기본값으로 초기화
        selectedSingleOption = OptionStatic.getNormal();

        // 단일 선택 옵션 (라디오 버튼 사용)
        ButtonGroup singleOptionGroup = new ButtonGroup();
        
        Option[] options = OptionStatic.getChickenOption();
        for (Option option : options) {
            if (option.getType() == OptionType.SINGLE) {
                boolean isDefault = (option == OptionStatic.getNormal());
                JRadioButton radioButton = swingGraphic.createRadioButton(
                    option.getName() + " +" + option.getPrice() + "원", 
                    optionFont, textPrimary, isDefault);
                
                // 기본 옵션이면 selectedSingleOption 업데이트
                if (isDefault) {
                    selectedSingleOption = option;
                }
                
                // 라디오 버튼 선택 시 가격 업데이트
                radioButton.addActionListener(e -> {
                    if (radioButton.isSelected()) {
                        selectedSingleOption = option;
                        updatePrice();
                    }
                });
                
                singleOptionRadioButtons.add(radioButton);
                singleOptionGroup.add(radioButton);
                optionsPanel.add(radioButton);
            } else {
                // 다중 선택 옵션
                JCheckBox checkBox = swingGraphic.createCheckBox(
                    option.getName() + " +" + option.getPrice() + "원", 
                    optionFont, textPrimary);
                checkBox.setSelected(false); // 다중 옵션은 기본적으로 선택되지 않음
                checkBox.addActionListener(e -> updatePrice());
                
                multipleOptionCheckBoxes.add(checkBox);
                optionsPanel.add(checkBox);
            }
        }
    }

    // 선택된 옵션에 따른 가격 계산 및 표시 업데이트 메서드
    private void updatePrice() {
        if (nowMenuItem == null) return;

        int totalPrice = nowMenuItem.getOriginalPrice();
        
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

    // 옵션 이름으로 옵션 객체 찾기 메서드
    private Option findOptionByName(String name) {
        Option[] options = OptionStatic.getChickenOption();
        for (Option option : options) {
            if (option.getName().equals(name)) {
                return option;
            }
        }
        return null;
    }

    // 선택된 옵션과 함께 장바구니에 메뉴 추가 메서드
    private void addToShoppingCart() {
        if (nowMenuItem == null || nowOrder == null) return;

        // OrderItem 생성
        OrderItem orderItem = new OrderItem(nowMenuItem);

        // 옵션이 있는 메뉴(치킨)인 경우에만 옵션 설정
        if (nowMenuItem.getCategory() == Category.CHICKEN) {
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
            selectOptionService.goMenu(nowOrder);
        }
        
        // 옵션 선택 화면 닫기
        setVisible(false);
        
        // 메뉴 선택 화면 업데이트 (총계 갱신)
        if (swingController instanceof SwingControllerImpl) {
            SwingControllerImpl controller = (SwingControllerImpl) swingController;
            controller.updateSelectMenuTotal();
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

    // 패널에 컴포넌트 배치 및 그라데이션 배경 설정 메서드
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
        contentPane.setLayout(new BorderLayout(7, 7));
        contentPane.setBorder(new EmptyBorder(13, 13, 13, 13));

        // 상단 패널 (메뉴 이름 + 이미지)
        JPanel topPanel = new JPanel(new BorderLayout(7, 7)) {
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
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        topPanel.add(menuNameLabel, BorderLayout.NORTH);
        
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setOpaque(false);
        imagePanel.add(menuImageLabel);
        topPanel.add(imagePanel, BorderLayout.CENTER);

        // 중앙 패널 (옵션)
        JScrollPane optionsScrollPane = new JScrollPane(optionsPanel);
        optionsScrollPane.setOpaque(false);
        optionsScrollPane.getViewport().setOpaque(false);
        optionsScrollPane.setPreferredSize(new Dimension(0, 200));
        optionsScrollPane.setBorder(swingGraphic.createRoundedBorder(12, borderColor, 2));

        // 하단 패널 (금액 + 버튼)
        JPanel bottomPanel = new JPanel(new BorderLayout(7, 7)) {
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
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        bottomPanel.add(priceLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(addToCartButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 패널 적용
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(optionsScrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }
}

