package src.musicOn;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {

    public Main() {
        setTitle("MusicOn");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }

    class GamePanel extends JPanel {
        private Image backgroundImage;

        public GamePanel() {
            loadBackgroundImage();
        }

        private void loadBackgroundImage() {
            try {
                // 절대 경로로 변경
                String currentPath = System.getProperty("user.dir");
                File imagePath = new File(currentPath + "/src/img/bg/BgStart.png");
                // 파일 존재 여부 확인을 위한 디버깅 출력
                System.out.println("이미지 파일 경로: " + imagePath.getAbsolutePath());
                System.out.println("파일 존재 여부: " + imagePath.exists());

                backgroundImage = ImageIO.read(imagePath);
            } catch (IOException e) {
                System.err.println("배경 이미지를 불러오는데 실패했습니다: " + e.getMessage());
                e.printStackTrace(); // 상세한 에러 정보 출력
                backgroundImage = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new
                Main());
    }
}