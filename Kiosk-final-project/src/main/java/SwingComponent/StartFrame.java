package SwingComponent;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {
    // config 연결을 위한 필드 생성
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정
    Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 50);
    Font labelFont = new Font(Font.DIALOG, Font.BOLD, 80);

    // 컬러 리스트 { ForeGround, Border, BackGround }
    Color [] labelColorList = new  Color[]{
            new Color(255,0,0),
            new Color(255,0,0)
    };
    Color [] buttonColorList = new  Color[]{
            new Color(255,0,0),
            new Color(255,0,0),
            new Color(255,255,255)
    };

    // 컴포넌트 필드 선언
    JButton selectMenuButton;
    JButton CheckOrderListButton;
    JLabel mainTitle;

    // StartFrame 생성자
    public StartFrame(SwingGraphic swingGraphic, SwingController swingController) {
        // swingGraphic과 swingController 객체 config 활용
        this.swingGraphic = swingGraphic;
        this.swingController = swingController;
        
        // 메인 콘텐츠펜 판넬 생성
        this.contentPane = new JPanel();

        // 컴포넌트 초기화
        initStartFrame();

        // StartFrame 기본 프레임 설정
        setTitle("주문 시작 화면");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 800);
        setContentPane(contentPane);
        setVisible(true);
    }

    private void initStartFrame() {
        // 컴포넌트 초기화 및 스타일링
        mainTitle = this.swingGraphic.makeLabel("HS 치킨", 500, 100, labelFont, labelColorList);
        selectMenuButton = this.swingGraphic.makeButton("메뉴 선택", 500, 80, buttonFont, buttonColorList);
        CheckOrderListButton = this.swingGraphic.makeButton("주문 내역", 500, 80, buttonFont, buttonColorList);

        // swingController 연결
        selectMenuButton.addActionListener(e -> swingController.moveSelectMenuFrame(this, e));
        CheckOrderListButton.addActionListener(e -> swingController.moveOrderInfoFrame(this, e));

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }
    
    // 컨텐츠펜 판넬에 추가
    private void addComponentsToPanel() {
        JPanel mainPanel = contentPane;

        // 메인 레이아웃 절반으로 분할
        mainPanel.setLayout(new GridLayout(2, 1));

        // 상단 패널 설정
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout()); // 내용물 중앙 정렬을 위해 GridBagLayout 사용
        topPanel.setBackground(Color.WHITE);

        // 상단 패널 추가
        topPanel.add(mainTitle);

        // 하단 패널 설정
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout()); // 버튼들을 중앙에 모으기 위해 GridBagLayout 사용
        bottomPanel.setBackground(Color.WHITE);

        // 버튼 배치
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // 버튼 사이의 간격 (상하 20px)

        // 메뉴 선택 버튼
        gbc.gridy = 0;
        bottomPanel.add(selectMenuButton, gbc);

        // 주문 내역 버튼
        gbc.gridy = 1;
        bottomPanel.add(CheckOrderListButton, gbc);

        // 메인 패널에 추가
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);
    }
}