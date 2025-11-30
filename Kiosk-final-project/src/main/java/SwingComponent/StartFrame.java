package SwingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// 키오스크 시작 화면 프레임 클래스
public class StartFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;

    JPanel contentPane;
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 37);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 60);
    Color primaryColor = SwingGraphic.PRIMARY_COLOR;
    Color primaryDark = SwingGraphic.PRIMARY_DARK;
    Color textColor = Color.WHITE;
    Color skyColor = new Color(135, 206, 250); // 하늘색
    Color purpleColor = new Color(186, 85, 211); // 보라색

    JLabel startTitle;
    JButton selectMenuButton;
    JButton CheckOrderListButton;

    // 시작 화면 초기화 생성자
    public StartFrame(SwingGraphic swingGraphic, SwingController swingController) {
        this.swingGraphic = swingGraphic;
        this.swingController = swingController;
        this.contentPane = new JPanel();
        initStartFrame();
        setTitle("주문 시작 화면");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1100, 700);
        setContentPane(contentPane);
    }

    // 시작 화면 컴포넌트 초기화 메서드
    private void initStartFrame() {
        startTitle = swingGraphic.createLabel("HS 치킨", labelFont, textColor, JLabel.CENTER);
        selectMenuButton = swingGraphic.createModernButton("메뉴 선택", 500, 70, buttonFont, primaryColor, primaryDark);
        CheckOrderListButton = swingGraphic.createModernButton("주문 내역", 500, 70, buttonFont, primaryColor, primaryDark);
        selectMenuButton.addActionListener(SwingSoundEventListener.click(() -> swingController.moveSelectMenu(this)));
        CheckOrderListButton.addActionListener(SwingSoundEventListener.click(() -> swingController.openCheckOrderList(this)));
        addComponentsToPanel();
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
                    0, 0, skyColor,
                    getWidth(), 0, purpleColor
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(800, 200));
        topPanel.add(startTitle);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new GridLayout(4, 1, 0, 20));
        bottomPanel.setPreferredSize(new Dimension(800, 333));

        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setOpaque(false);
        bottomPanel.add(emptyPanel1);

        JPanel btnPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel1.setOpaque(false);
        btnPanel1.add(selectMenuButton);
        bottomPanel.add(btnPanel1);

        JPanel btnPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel2.setOpaque(false);
        btnPanel2.add(CheckOrderListButton);
        bottomPanel.add(btnPanel2);

        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setOpaque(false);
        bottomPanel.add(emptyPanel2);
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(bottomPanel, BorderLayout.CENTER);
    }
}