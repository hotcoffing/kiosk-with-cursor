package SwingComponent;

import javax.swing.*;

public class SelectOptionFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingAction  swingAction;
    private final SwingController swingController;

    public SelectOptionFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
        this.swingGraphic = swingGraphic;
        this.swingAction = swingAction;
        this.swingController = swingController;
    }
}
