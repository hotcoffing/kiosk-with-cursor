package SwingComponent;

import javax.swing.*;
import java.awt.*;

// Swing UI 컴포넌트 생성 구현 클래스
// 라벨과 버튼을 일관된 스타일로 생성하는 기능을 구현
public class SwingGraphicImpl implements SwingGraphic {
    // 레이블 생성 및 초기화
    @Override
    public JLabel makeLabel(String text, int width, int height, Font font, Color[] colorList) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setSize(width, height);
        label.setForeground(colorList[0]);
        label.setBorder(BorderFactory.createLineBorder(colorList[1], 5));

        return label;
    }

    // 버튼 생성 및 초기화
    @Override
    public JButton makeButton(String text, int width, int height, Font font, Color[] colorList) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setSize(width, height);
        button.setFocusPainted(false);
        button.setForeground(colorList[0]);
        button.setBorder(BorderFactory.createLineBorder(colorList[1], 5));
        button.setBackground(colorList[2]);

        return button;
    }
}
