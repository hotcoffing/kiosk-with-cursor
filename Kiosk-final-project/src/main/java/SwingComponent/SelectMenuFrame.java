package SwingComponent;

import javax.swing.*;
import java.awt.*;

public class SelectMenuFrame extends JFrame {
    // config 연결을 위한 기능 필드
    private final SwingGraphic swingGraphic;
    private final SwingAction swingAction;
    private final SwingController swingController;

    // 컨텐츠펜 선언
    JPanel contentPane;

    // 스타일 및 폰트 설정 (Graphic에 패밀리 형태로 분할 가능)
    /*Font buttonFont = new Font(Font.DIALOG, Font.BOLD, 50);
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
    };*/

    // 컴포넌트 필드 선언
    JLabel tableNumberLabel;
    JComboBox tableNumberComboBox;

    // SelectMenuFrame 생성자
    public SelectMenuFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
        // swingGraphic, swingAction, swingController 객체 config 활용
        this.swingGraphic = swingGraphic;
        this.swingAction = swingAction;
        this.swingController = swingController;

        setTitle("메뉴 선택");
        setBounds(0, 0, 800, 800); // 크기 지정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}