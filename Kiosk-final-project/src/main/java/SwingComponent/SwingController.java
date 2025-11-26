package SwingComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public interface SwingController {
    void moveSelectMenuFrame(JFrame nowFrame, ActionEvent e);
    void moveOrderInfoFrame(JFrame nowFrame, ActionEvent e);
}