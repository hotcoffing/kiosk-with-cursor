// AppConfig.java (새로 생성하거나, 기존 Config 중 하나를 통합)

package Config;

import SwingComponent.*;

public class SwingConfig {

    // 1. SwingGraphic 객체 생성 (Impl 필요)
    public SwingGraphic swingGraphic() {
        return new SwingGraphicImpl(); // SwingGraphicImpl 클래스가 정의되어 있어야 함
    }

    // 2. SwingAction 객체 생성 (Impl 필요)
    public SwingAction swingAction() {
        return new SwingActionImpl(); // SwingActionImpl 클래스가 정의되어 있어야 함
    }

    // 3. SwingController 객체 생성 (Impl 필요)
    public SwingController swingController() {
        // SwingController가 SwingAction 객체를 의존하는 경우, 여기서 주입
        // return new SwingControllerImpl(swingAction()); 
        return new SwingControllerImpl(); // SwingControllerImpl 클래스가 정의되어 있어야 함
    }

    // 4. StartFrame 객체 생성 및 의존성 주입
    public StartFrame startFrame() {
        return new StartFrame(
                swingGraphic(),      // SwingGraphic 주입
                swingController()    // SwingController 주입
        );
    }
}