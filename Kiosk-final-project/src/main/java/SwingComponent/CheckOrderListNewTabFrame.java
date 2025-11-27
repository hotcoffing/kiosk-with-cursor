package SwingComponent;

import Domain.OrderItem;
import kioskService.CheckOrderListService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

// 주문 내역 조회 프레임 클래스
// 테이블 번호와 고객명으로 주문 내역을 조회하고 총계를 표시하는 새 창
public class CheckOrderListNewTabFrame extends JFrame {
    private final CheckOrderListService checkOrderListService;

    private JComboBox<String> tableComboBox;
    private JTextField customerNameField;
    private JPanel itemsPanel;
    private JLabel totalLabel;
    private JLabel statusLabel;

    public CheckOrderListNewTabFrame(CheckOrderListService checkOrderListService) {
        this.checkOrderListService = checkOrderListService;

        setTitle("주문 내역");
        setBounds(1150, 0, 550, 800);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);

        // 상단 패널 (제목과 입력 폼을 합침)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("주문 내역", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        
        // 입력 폼
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        JLabel tableLabel = new JLabel("테이블 번호");
        tableLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        tableComboBox = new JComboBox<>(new String[]{"TB 1", "TB 2", "TB 3", "TB 4", "TB 5", "TB 6", "TB 7", "TB 8"});

        JLabel nameLabel = new JLabel("주문자");
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        customerNameField = new JTextField();

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton submitButton = new JButton("입력");
        JButton clearButton = new JButton("지우기");
        submitButton.addActionListener(e -> fetchOrder());
        clearButton.addActionListener(e -> clearForm());
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
        itemsPanel.setBackground(Color.WHITE);
        itemsPanel.setBorder(BorderFactory.createTitledBorder("주문 정보"));

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 하단 패널 (총계, 상태)
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomPanel.setBackground(Color.WHITE);

        totalLabel = new JLabel("총계: 0원", SwingConstants.CENTER);
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        statusLabel = new JLabel("주문 정보를 조회하세요.", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GRAY);

        bottomPanel.add(totalLabel);
        bottomPanel.add(statusLabel);

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

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
                emptyPanel.setBackground(Color.WHITE);
                itemsPanel.add(emptyPanel);
            }
        } else {
            int itemCount = orderItems.size();
            
            // 4개 이하는 4행으로 고정, 4개 이상은 실제 개수만큼 행 생성 (스크롤 가능)
            int gridRows = itemCount < 4 ? 4 : itemCount;
            itemsPanel.setLayout(new GridLayout(gridRows, 1, 0, 10));
            
            for (OrderItem item : orderItems) {
                // 각 OrderItem을 표시하는 패널 생성
                JPanel itemPanel = new JPanel(new BorderLayout(5, 5));
                itemPanel.setBackground(Color.WHITE);
                itemPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                
                // 상단: 메뉴명, 수량, 가격
                JPanel headerPanel = new JPanel(new GridLayout(1, 4, 5, 0));
                headerPanel.setBackground(Color.WHITE);
                
                JLabel menuNameLabel = new JLabel(item.getMenuName());
                menuNameLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
                
                JLabel quantityLabel = new JLabel("x" + item.getQuantity(), SwingConstants.CENTER);
                quantityLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
                
                JLabel emptyLabel = new JLabel(""); // 공백
                
                int totalItemPrice = item.getPriceWithOptions() * item.getQuantity();
                JLabel priceLabel = new JLabel(totalItemPrice + "원", SwingConstants.RIGHT);
                priceLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
                
                headerPanel.add(menuNameLabel);
                headerPanel.add(quantityLabel);
                headerPanel.add(emptyLabel);
                headerPanel.add(priceLabel);
                
                // 하단: 옵션 정보
                JPanel optionPanel = new JPanel();
                optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
                optionPanel.setBackground(Color.WHITE);
                optionPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
                
                String optionSummary = item.getOptionsString();
                if (optionSummary != null && !optionSummary.isEmpty()) {
                    String[] optionLines = optionSummary.split("\n");
                    for (String line : optionLines) {
                        if (!line.trim().isEmpty()) {
                            JLabel optionLabel = new JLabel(line);
                            optionLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                            optionLabel.setForeground(Color.GRAY);
                            optionPanel.add(optionLabel);
                        }
                    }
                }
                
                itemPanel.add(headerPanel, BorderLayout.NORTH);
                if (optionPanel.getComponentCount() > 0) {
                    itemPanel.add(optionPanel, BorderLayout.CENTER);
                }
                
                itemsPanel.add(itemPanel);
            }
            
            // 4개 미만인 경우 빈 패널로 채움
            while (itemCount < 4) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setBackground(Color.WHITE);
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
            emptyPanel.setBackground(Color.WHITE);
            itemsPanel.add(emptyPanel);
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
}
