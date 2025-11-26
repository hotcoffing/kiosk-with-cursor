package SwingComponent;


import javax.swing.*;

public class OrderFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingAction swingAction;
    private final SwingController swingController;

    public OrderFrame(SwingGraphic swingGraphic, SwingAction swingAction, SwingController swingController) {
        // swingGraphic, swingAction, swingController 객체 config 활용
        this.swingGraphic = swingGraphic;
        this.swingAction = swingAction;
        this.swingController = swingController;
    }
}
