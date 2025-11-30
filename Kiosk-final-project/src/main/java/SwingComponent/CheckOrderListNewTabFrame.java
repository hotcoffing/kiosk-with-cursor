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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Domain.MenuItem;
import Domain.OrderItem;
import Static.MenuItemStatic;
import kioskService.CheckOrderListService;

// 주문 내역 조회 화면 프레임 클래스
public class CheckOrderListNewTabFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final CheckOrderListService checkOrderListService;

    private JComboBox<String> tableComboBox;
    private JTextField customerNameField;
    private JPanel itemsPanel;
    private JLabel totalLabel;
    private JLabel statusLabel;

    // 주문 내역 조회 화면 초기화 생성자
    public CheckOrderListNewTabFrame(SwingGraphic swingGraphic, CheckOrderListService checkOrderListService) {
        this.swingGraphic = swingGraphic;
        this.checkOrderListService = checkOrderListService;

        setTitle("주문 내역");
        setBounds(1150, 0, 550, 800);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
    }

    // 주문 내역 조회 화면 컴포넌트 초기화 메서드
    private void initComponents() {
        // 현대적인 색상 팔레트
        Color primaryColor = SwingGraphic.PRIMARY_COLOR;
        Color primaryDark = SwingGraphic.PRIMARY_DARK;
        Color secondaryColor = SwingGraphic.SECONDARY_COLOR;
        Color secondaryDark = SwingGraphic.SECONDARY_DARK;
        Color textPrimary = SwingGraphic.TEXT_PRIMARY;
        
        // 그라데이션 배경
        JPanel contentPane = new JPanel(new BorderLayout(10, 10)) {
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
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 상단 패널 (제목과 입력 폼을 합침)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10)) {
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
        
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        JLabel titleLabel = swingGraphic.createLabel("주문 내역", titleFont, textPrimary, SwingConstants.CENTER);
        
        // 입력 폼
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);

        Font formLabelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        JLabel tableLabel = swingGraphic.createLabel("테이블 번호", formLabelFont, textPrimary);
        tableComboBox = swingGraphic.createComboBox(new String[]{"TB 1", "TB 2", "TB 3", "TB 4", "TB 5", "TB 6", "TB 7", "TB 8"}, formLabelFont);

        JLabel nameLabel = swingGraphic.createLabel("주문자", formLabelFont, textPrimary);
        customerNameField = swingGraphic.createTextField(formLabelFont);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        JButton submitButton = swingGraphic.createModernButton("입력", 0, 35, 
            buttonFont, primaryColor, primaryDark);
        JButton clearButton = swingGraphic.createModernButton("지우기", 0, 35, 
            buttonFont, 
            new Color(113, 128, 150), new Color(74, 85, 104)); // 회색 계열
        submitButton.addActionListener(SwingSoundEventListener.click(() -> fetchOrder()));
        clearButton.addActionListener(SwingSoundEventListener.click(() -> clearForm()));
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        formPanel.add(tableLabel);
        formPanel.add(tableComboBox);
        formPanel.add(nameLabel);
        formPanel.add(customerNameField);
        formPanel.add(new JLabel("")); // 빈 셀
        formPanel.add(buttonPanel);
        
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);

        // 주문 정보 패널 (스크롤 가능)
        itemsPanel = new JPanel();
        itemsPanel.setOpaque(false);
        itemsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "주문 정보",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font(Font.SANS_SERIF, Font.BOLD, 14), primaryColor
        ));

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 하단 패널 (총계, 상태)
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10)) {
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

        totalLabel = new JLabel("총계: 0원", SwingConstants.CENTER);
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        totalLabel.setForeground(textPrimary);

        statusLabel = new JLabel("주문 정보를 조회하세요.", SwingConstants.CENTER);
        statusLabel.setForeground(SwingGraphic.TEXT_MUTED);

        bottomPanel.add(totalLabel);
        bottomPanel.add(statusLabel);

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    // 주문 내역 조회 및 표시 메서드
    private void fetchOrder() {
        String selectedTable = (String) tableComboBox.getSelectedItem();
        String customerName = customerNameField.getText().trim();

        if (selectedTable == null || customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "테이블 번호와 주문자명을 입력해주세요.",
                    "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int tableNumber = Integer.parseInt(selectedTable.replace("TB ", ""));

        List<OrderItem> orderItems = checkOrderListService != null
                ? checkOrderListService.getOrderItemsByUserInfo(tableNumber, customerName)
                : null;

        itemsPanel.removeAll();

        if (orderItems == null || orderItems.isEmpty()) {
            statusLabel.setText("해당 조건의 주문이 없습니다.");
            totalLabel.setText("총계: 0원");
            // 주문 항목이 없는 경우 4행으로 고정
            itemsPanel.setLayout(new GridLayout(4, 1, 0, 10));
            for (int i = 0; i < 4; i++) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setOpaque(false);
                itemsPanel.add(emptyPanel);
            }
        } else {
            int itemCount = orderItems.size();
            
            // 4개 이하는 4행으로 고정, 4개 이상은 실제 개수만큼 행 생성 (스크롤 가능)
            int gridRows = itemCount < 4 ? 4 : itemCount;
            itemsPanel.setLayout(new GridLayout(gridRows, 1, 0, 10));
            
            Color textPrimary = SwingGraphic.TEXT_PRIMARY;
            Color dangerColor = SwingGraphic.DANGER_COLOR;
            Color borderColor = SwingGraphic.BORDER_COLOR;
            
            for (OrderItem item : orderItems) {
                // 각 OrderItem을 표시하는 패널 생성
                JPanel itemPanel = new JPanel(new BorderLayout(10, 5)) {
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
                itemPanel.setOpaque(false);
                itemPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
                
                // 이미지 아이콘 로드
                MenuItem menuItem = MenuItemStatic.findMenuItemByName(item.getMenuName());
                ImageIcon imageIcon = null;
                if (menuItem != null) {
                    imageIcon = loadImageIcon(menuItem.getImagePath(), 80, 80);
                }
                
                // 이미지 라벨 (왼쪽)
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
                imageLabel.setPreferredSize(new Dimension(80, 80));
                imageLabel.setOpaque(false);
                if (imageIcon == null) {
                    imageLabel.setText(menuItem == null ? "메뉴 없음" : "이미지 없음");
                }
                
                // 정보 패널 (오른쪽)
                JPanel infoPanel = new JPanel(new BorderLayout(5, 5));
                infoPanel.setOpaque(false);
                
                // 상단: 메뉴명, 수량, 가격
                JPanel headerPanel = new JPanel(new BorderLayout(50, 0));
                headerPanel.setOpaque(false);
                
                // 왼쪽: 메뉴명과 수량
                JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                leftPanel.setOpaque(false);
                
                JLabel menuNameLabel = new JLabel(item.getMenuName());
                menuNameLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
                menuNameLabel.setForeground(textPrimary);
                
                JLabel quantityLabel = new JLabel("x" + item.getQuantity());
                quantityLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
                quantityLabel.setForeground(textPrimary);
                
                leftPanel.add(menuNameLabel);
                leftPanel.add(quantityLabel);
                
                // 오른쪽: 가격
                int totalItemPrice = item.getPriceWithOptions() * item.getQuantity();
                JLabel priceLabel = new JLabel(totalItemPrice + "원", SwingConstants.RIGHT);
                priceLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
                priceLabel.setForeground(dangerColor);
                
                headerPanel.add(leftPanel, BorderLayout.WEST);
                headerPanel.add(priceLabel, BorderLayout.EAST);
                
                // 하단: 옵션 정보
                JPanel optionPanel = new JPanel();
                optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
                optionPanel.setOpaque(false);
                optionPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
                
                String optionSummary = item.getOptionsString();
                if (optionSummary != null && !optionSummary.isEmpty()) {
                    String[] optionLines = optionSummary.split("\n");
                    for (String line : optionLines) {
                        if (!line.trim().isEmpty()) {
                            JLabel optionLabel = new JLabel(line);
                            optionLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                            optionLabel.setForeground(SwingGraphic.TEXT_MUTED);
                            optionPanel.add(optionLabel);
                        }
                    }
                }
                
                infoPanel.add(headerPanel, BorderLayout.NORTH);
                if (optionPanel.getComponentCount() > 0) {
                    infoPanel.add(optionPanel, BorderLayout.CENTER);
                }
                
                itemPanel.add(imageLabel, BorderLayout.WEST);
                itemPanel.add(infoPanel, BorderLayout.CENTER);
                
                itemsPanel.add(itemPanel);
            }
            
            // 4개 미만인 경우 빈 패널로 채움
            while (itemCount < 4) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setOpaque(false);
                itemsPanel.add(emptyPanel);
                itemCount++;
            }

            int total = checkOrderListService.gettotalPriceByUserInfo(tableNumber, customerName);
            totalLabel.setText("총계: " + total + "원");
            statusLabel.setText("조회 완료");
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private void clearForm() {
        customerNameField.setText("");
        itemsPanel.removeAll();
        totalLabel.setText("총계: 0원");
        statusLabel.setText("주문 정보를 조회하세요.");
        // 4행으로 고정
        itemsPanel.setLayout(new GridLayout(4, 1, 0, 10));
        for (int i = 0; i < 4; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            itemsPanel.add(emptyPanel);
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
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
}
