package SwingComponent;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

// 버튼 클릭 및 주문 완료 시 사운드 재생 이벤트 리스너 클래스
public class SwingSoundEventListener {
    
    // 클릭 사운드 재생 메서드
    private static void playClickSound() {
        playSound("/sound/클릭.wav");
    }
    
    // 주문 사운드 재생 메서드
    public static void playOrderSound() {
        playSound("/sound/주문.wav");
    }
    
    // 지정된 경로의 사운드 파일 재생 메서드
    private static void playSound(String soundPath) {
        try {
            InputStream audioStream = SwingSoundEventListener.class.getResourceAsStream(soundPath);
            if (audioStream != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (Exception e) {
        }
    }
    
    // 클릭 사운드와 함께 실행되는 ActionListener 생성 메서드
    public static ActionListener click(ActionListener originalListener) {
        return e -> {
            playClickSound();
            if (originalListener != null) {
                originalListener.actionPerformed(e);
            }
        };
    }
    
    // 클릭 사운드와 함께 실행되는 Runnable 래핑 메서드
    public static ActionListener click(Runnable runnable) {
        return e -> {
            playClickSound();
            if (runnable != null) {
                runnable.run();
            }
        };
    }
    
    // 클릭 사운드와 함께 실행되는 MouseAdapter 생성 메서드
    public static MouseAdapter click(MouseAdapter originalAdapter) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound();
                if (originalAdapter != null) {
                    originalAdapter.mouseClicked(e);
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if (originalAdapter != null) {
                    originalAdapter.mouseEntered(e);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (originalAdapter != null) {
                    originalAdapter.mouseExited(e);
                }
            }
        };
    }
    
    // 주문 사운드와 함께 실행되는 ActionListener 생성 메서드
    public static ActionListener order(ActionListener originalListener) {
        return e -> {
            playOrderSound();
            if (originalListener != null) {
                originalListener.actionPerformed(e);
            }
        };
    }
}

