package SwingComponent;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

// Swing UI 컴포넌트 생성 인터페이스
// 라벨과 버튼을 일관된 스타일로 생성하는 기능을 정의
public interface SwingGraphic {
    public JLabel makeLabel(String text, int width, int height, Font font, Color[] colorList);
    public JButton makeButton(String text, int width, int height, Font font, Color[] colorList);
}