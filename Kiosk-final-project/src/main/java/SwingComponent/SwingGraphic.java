package SwingComponent;

import javax.swing.*;
import java.awt.*;

public interface SwingGraphic {
    public JLabel makeLabel(String text, int width, int height, Font font, Color[] colorList);
    public JButton makeButton(String text, int width, int height, Font font, Color[] colorList);
}