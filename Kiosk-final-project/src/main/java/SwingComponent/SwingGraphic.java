package SwingComponent;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

// 현대적인 스타일의 Swing UI 컴포넌트 생성 인터페이스
public interface SwingGraphic {
    
    Color PRIMARY_COLOR = new Color(255, 140, 66); // #FF8C42 (주황색)
    Color PRIMARY_DARK = new Color(210, 105, 30); // #D2691E (갈색)
    Color SECONDARY_COLOR = new Color(245, 247, 250); // #f5f7fa
    Color SECONDARY_DARK = new Color(195, 207, 226); // #c3cfe2
    Color TEXT_PRIMARY = new Color(45, 55, 72); // #2d3748
    Color TEXT_MUTED = new Color(113, 128, 150); // #718096
    Color DANGER_COLOR = new Color(229, 62, 62); // #e53e3e
    Color BORDER_COLOR = new Color(226, 232, 240); // #e2e8f0
    Color BACKGROUND_LIGHT = new Color(255, 255, 255);
    
    // 둥근 모서리 테두리 생성 메서드
    Border createRoundedBorder(int radius, Color color, int thickness);
    // 그라데이션 버튼 생성 메서드
    JButton createModernButton(String text, int width, int height, Font font, Color bgColor1, Color bgColor2);
    // 라벨 생성 메서드
    JLabel createLabel(String text, Font font, Color foregroundColor);
    // 정렬 옵션이 있는 라벨 생성 메서드
    JLabel createLabel(String text, Font font, Color foregroundColor, int horizontalAlignment);
    // 콤보박스 생성 메서드
    JComboBox<String> createComboBox(String[] items, Font font);
    // 리스트 생성 메서드
    JList<String> createList(String[] items, Font font);
    // 라디오 버튼 생성 메서드
    JRadioButton createRadioButton(String text, Font font, Color foregroundColor, boolean selected);
    // 체크박스 생성 메서드
    JCheckBox createCheckBox(String text, Font font, Color foregroundColor);
    // 텍스트 필드 생성 메서드
    JTextField createTextField(Font font);
    // 텍스트 영역 생성 메서드
    JTextArea createTextArea(Font font, Color foregroundColor, boolean editable);
}

