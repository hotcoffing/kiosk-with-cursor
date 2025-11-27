package SwingComponent;

import javax.swing.*;
import java.awt.*;

// 시작 화면 프레임 클래스
// 메뉴 선택, 주문 내역 조회 버튼이 있는 초기 화면
public class StartFrame extends JFrame {
    // config 연결을 위한 기능 필드
    private final SwingGraphic swingGraphic;
    private final SwingController swingController;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정 (Graphic에 패밀리 형태로 분할 가능)
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
    JLabel startTitle;
    JButton selectMenuButton;
    JButton CheckOrderListButton;

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
    }

    private void initStartFrame() {
        // 컴포넌트 초기화 및 스타일링
        startTitle = this.swingGraphic.makeLabel("HS 치킨", 500, 100, labelFont, labelColorList);
        selectMenuButton = this.swingGraphic.makeButton("메뉴 선택", 500, 80, buttonFont, buttonColorList);
        CheckOrderListButton = this.swingGraphic.makeButton("주문 내역", 500, 80, buttonFont, buttonColorList);

        // swingController 연결
        selectMenuButton.addActionListener(e -> swingController.moveSelectMenu(this));
        CheckOrderListButton.addActionListener(e -> swingController.openCheckOrderList(this));

        // 컴포넌트 배치 실행
        addComponentsToPanel();
    }
    
    // 컨텐츠펜 판넬에 추가
    private void addComponentsToPanel() {
        JPanel startPanel = contentPane;

        // 메인 레이아웃 절반으로 분할
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(Color.WHITE);

        // 상단 패널 설정
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout()); // 내용물 중앙 정렬을 위해 GridBagLayout 사용
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(800, 300));

        // 상단 패널 추가
        topPanel.add(startTitle);

        // 하단 패널 설정
        JPanel bottomPanel = new JPanel();
        //bottomPanel.setLayout(new GridBagLayout()); // 버튼들을 중앙에 모으기 위해 GridBagLayout 사용
        bottomPanel.setBackground(Color.WHITE);

        // 하단 패널 설정
        bottomPanel.setLayout(new GridLayout(4, 1, 0, 30));
        bottomPanel.setPreferredSize(new Dimension(800, 500));

        // 여백 그리드 설정
        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setBackground(Color.WHITE);
        bottomPanel.add(emptyPanel1);

        // 메뉴 선택 버튼 그리드 설정
        JPanel btnPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel1.setBackground(Color.WHITE);
        btnPanel1.add(selectMenuButton);
        bottomPanel.add(btnPanel1);

        // 주문 내역 버튼 그리드 설정
        JPanel btnPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel2.setBackground(Color.WHITE);
        btnPanel2.add(CheckOrderListButton);
        bottomPanel.add(btnPanel2);

        // 여백 그리드 설정
        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setBackground(Color.WHITE);
        bottomPanel.add(emptyPanel2);

        // 패널 적용
        startPanel.add(topPanel, BorderLayout.NORTH);
        startPanel.add(bottomPanel, BorderLayout.CENTER);
    }
}