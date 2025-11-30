package SwingComponent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

// 현대적인 스타일의 Swing UI 컴포넌트 생성 구현 클래스
public class SwingGraphicImpl implements SwingGraphic {
    
    private static class RoundedBorder implements Border {
        private final int radius;
        private final Color color;
        private final int thickness;
        
        // 둥근 테두리 생성자
        public RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }
        
        // 테두리 그리기 메서드
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawRoundRect(x + thickness/2, y + thickness/2, 
                             width - thickness, height - thickness, radius, radius);
            g2d.dispose();
        }
        
        // 테두리 여백 반환 메서드
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }
        
        // 테두리 불투명도 확인 메서드
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
    
    private static class ModernGradientButton extends JButton {
        private final Color bgColor1;
        private final Color bgColor2;
        private boolean isHovered = false;
        
        // 그라데이션 버튼 생성자
        public ModernGradientButton(String text, int width, int height, Font font, Color bgColor1, Color bgColor2) {
            super(text);
            this.bgColor1 = bgColor1;
            this.bgColor2 = bgColor2;
            setPreferredSize(new Dimension(width, height));
            setFont(font);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }
            });
        }
        
        // 버튼 그라데이션 배경 및 텍스트 그리기 메서드
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            Color c1 = isHovered ? brighten(bgColor1) : bgColor1;
            Color c2 = isHovered ? brighten(bgColor2) : bgColor2;
            GradientPaint gradient = new GradientPaint(0, 0, c1, width, height, c2);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, width, height, 10, 10);
            g2d.setColor(getForeground());
            FontMetrics fm = g2d.getFontMetrics();
            String text = getText();
            int x = (width - fm.stringWidth(text)) / 2;
            int y = (height + fm.getAscent() - fm.getDescent()) / 2;
            g2d.drawString(text, x, y);
            
            g2d.dispose();
        }
        
        // 색상 밝기 증가 메서드
        private Color brighten(Color color) {
            int r = Math.min(255, color.getRed() + 20);
            int g = Math.min(255, color.getGreen() + 20);
            int b = Math.min(255, color.getBlue() + 20);
            return new Color(r, g, b);
        }
    }
    
    // 둥근 모서리 테두리 생성 메서드
    @Override
    public Border createRoundedBorder(int radius, Color color, int thickness) {
        return new RoundedBorder(radius, color, thickness);
    }
    
    // 그라데이션 버튼 생성 메서드
    @Override
    public JButton createModernButton(String text, int width, int height, Font font, Color bgColor1, Color bgColor2) {
        return new ModernGradientButton(text, width, height, font, bgColor1, bgColor2);
    }
    
    // 라벨 생성 메서드
    @Override
    public JLabel createLabel(String text, Font font, Color foregroundColor) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(foregroundColor);
        return label;
    }
    
    // 정렬 옵션이 있는 라벨 생성 메서드
    @Override
    public JLabel createLabel(String text, Font font, Color foregroundColor, int horizontalAlignment) {
        JLabel label = new JLabel(text, horizontalAlignment);
        label.setFont(font);
        label.setForeground(foregroundColor);
        return label;
    }
    
    // 콤보박스 생성 메서드
    @Override
    public JComboBox<String> createComboBox(String[] items, Font font) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(font);
        comboBox.setBorder(createRoundedBorder(8, BORDER_COLOR, 2));
        return comboBox;
    }
    
    // 리스트 생성 메서드
    @Override
    public JList<String> createList(String[] items, Font font) {
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(font);
        list.setForeground(TEXT_PRIMARY);
        list.setBackground(BACKGROUND_LIGHT);
        list.setSelectionBackground(PRIMARY_COLOR);
        list.setSelectionForeground(Color.WHITE);
        list.setBorder(createRoundedBorder(12, BORDER_COLOR, 2));
        return list;
    }
    
    // 라디오 버튼 생성 메서드
    @Override
    public JRadioButton createRadioButton(String text, Font font, Color foregroundColor, boolean selected) {
        JRadioButton radioButton = new JRadioButton(text, selected);
        radioButton.setFont(font);
        radioButton.setForeground(foregroundColor);
        radioButton.setOpaque(false);
        return radioButton;
    }
    
    // 체크박스 생성 메서드
    @Override
    public JCheckBox createCheckBox(String text, Font font, Color foregroundColor) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(font);
        checkBox.setForeground(foregroundColor);
        checkBox.setOpaque(false);
        return checkBox;
    }
    
    // 텍스트 필드 생성 메서드
    @Override
    public JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setBorder(createRoundedBorder(8, BORDER_COLOR, 2));
        return textField;
    }
    
    // 텍스트 영역 생성 메서드
    @Override
    public JTextArea createTextArea(Font font, Color foregroundColor, boolean editable) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setForeground(foregroundColor);
        textArea.setEditable(editable);
        textArea.setBorder(createRoundedBorder(8, BORDER_COLOR, 2));
        return textArea;
    }
}

