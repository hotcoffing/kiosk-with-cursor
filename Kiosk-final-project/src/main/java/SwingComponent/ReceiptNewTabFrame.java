package SwingComponent;

import Domain.Order;
import Domain.OrderItem;
import kioskService.PrintReceiptService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// 영수증 출력 프레임 클래스
// 주문 완료 후 주문 내역, 총계, 주문 시간을 표시하는 영수증 새 창
public class ReceiptNewTabFrame extends JFrame {
    private final PrintReceiptService printReceiptService;

    private JLabel titleLabel;
    private JLabel tableInfoLabel;
    private JPanel itemsPanel;
    private JLabel totalLabel;
    private JLabel orderTimeLabel;
    private JLabel footerLabel;

    public ReceiptNewTabFrame(SwingGraphic swingGraphic, PrintReceiptService printReceiptService) {
        this.printReceiptService = printReceiptService;
        setTitle("Receipt");
        setBounds(1150, 100, 550, 800);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 상단 패널 (제목, 부제목, 테이블 번호)
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        topPanel.setBackground(Color.WHITE);

        titleLabel = new JLabel("영수증", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));

        JLabel subTitle = new JLabel("주문서", SwingConstants.CENTER);
        subTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        tableInfoLabel = new JLabel("테이블 번호 TB -", SwingConstants.CENTER);
        tableInfoLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        topPanel.add(titleLabel);
        topPanel.add(subTitle);
        topPanel.add(tableInfoLabel);

        // 중앙 패널 (주문 항목) - 스크롤 가능
        itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 하단 패널 (총계, 주문시간)
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomPanel.setBackground(Color.WHITE);

        // 총계와 주문시간을 한 줄에 표시
        JPanel totalTimePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        totalTimePanel.setBackground(Color.WHITE);
        
        totalLabel = new JLabel("총계: 0원", SwingConstants.LEFT);
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        
        orderTimeLabel = new JLabel("주문시간: -", SwingConstants.RIGHT);
        orderTimeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        orderTimeLabel.setForeground(Color.GRAY);
        
        totalTimePanel.add(totalLabel);
        totalTimePanel.add(orderTimeLabel);

        footerLabel = new JLabel("-------------", SwingConstants.CENTER);

        bottomPanel.add(totalTimePanel);
        bottomPanel.add(footerLabel);

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    public void displayReceipt(Long orderId) {
        itemsPanel.removeAll();

        if (orderId == null) {
            tableInfoLabel.setText("주문 정보가 없습니다.");
            totalLabel.setText("총계: 0원");
            orderTimeLabel.setText("주문시간: -");
            itemsPanel.setLayout(new GridLayout(1, 1));
            revalidate();
            repaint();
            return;
        }

        Order order = printReceiptService != null ? printReceiptService.getOrderData(orderId) : null;

        if (order == null) {
            tableInfoLabel.setText("주문 정보를 찾을 수 없습니다.");
            totalLabel.setText("총계: 0원");
            orderTimeLabel.setText("주문시간: -");
            itemsPanel.setLayout(new GridLayout(1, 1));
        } else {
            tableInfoLabel.setText("테이블 번호 TB " + order.getTableNumber());
            
            // 주문시간 표시
            if (order.getOrderTime() != null) {
                java.time.format.DateTimeFormatter formatter = 
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                orderTimeLabel.setText("주문시간: " + order.getOrderTime().format(formatter));
            } else {
                orderTimeLabel.setText("주문시간: -");
            }
            
            if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                int itemCount = order.getOrderItems().size();
                
                // 4개 이하는 4행으로 고정, 4개 이상은 실제 개수만큼 행 생성 (스크롤 가능)
                int gridRows = itemCount < 4 ? 4 : itemCount;
                itemsPanel.setLayout(new GridLayout(gridRows, 1, 0, 10));
                
                for (OrderItem item : order.getOrderItems()) {
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
            } else {
                // 주문 항목이 없는 경우 4행으로 고정
                itemsPanel.setLayout(new GridLayout(4, 1, 0, 10));
                for (int i = 0; i < 4; i++) {
                    JPanel emptyPanel = new JPanel();
                    emptyPanel.setBackground(Color.WHITE);
                    itemsPanel.add(emptyPanel);
                }
            }
            
            totalLabel.setText("총계: " + order.getTotalPrice() + "원");
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
        setVisible(true);
    }
}
