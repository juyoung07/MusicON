package src.musicOn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class Main extends JFrame {
    public Main() {
        setTitle("MusicOn");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        setVisible(true);
    }

    class GamePanel extends JPanel {
        private Image backgroundImage;
        private JButton startButton, endUpButton;

        public GamePanel() {
            setLayout(null); // Absolute Positioning 사용
            loadBackgroundImage();
            loadButtonImages();

            // 버튼 크기 조정 및 위치 설정
            startButton.setBounds(50, 50, startButton.getPreferredSize().width, startButton.getPreferredSize().height);
            endUpButton.setBounds(50, 150, endUpButton.getPreferredSize().width, endUpButton.getPreferredSize().height);

            add(startButton);
            add(endUpButton);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> new SelectAlbum()); // 새로운 SelectAlbum 창 생성
                }
            });

            endUpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); // 프로그램 종료
                }
            });
        }

        private void loadBackgroundImage() {
            backgroundImage = loadImage("../img/bg/BgStart.png"); // 클래스패스 기준 경로 사용
            if (backgroundImage == null) {
                System.err.println("배경 이미지 로드 실패!");
            }
        }

        private void loadButtonImages() {
            Image startImage = loadImage("../img/btn/BtnStart.png");
            Image endImage = loadImage("../img/btn/BtnEnd.png");

            if(startImage != null && endImage != null) {
                startButton = new JButton(new ImageIcon(startImage));
                endUpButton = new JButton(new ImageIcon(endImage));
            } else {
                System.err.println("버튼 이미지 로드 실패!");
                startButton = new JButton("Start"); // 이미지 로드 실패시 기본 버튼 표시
                endUpButton = new JButton("End");
            }
        }

        // 클래스 패스로부터 이미지 로드
        private Image loadImage(String path) {
            try (InputStream inputStream = getClass().getResourceAsStream(path)) {
                if (inputStream == null) {
                    System.err.println("이미지 파일을 찾을 수 없습니다: " + path);
                    return null;
                }
                return ImageIO.read(inputStream);
            } catch (IOException e) {
                System.err.println("이미지 로드 실패: " + e.getMessage());
                e.printStackTrace();
                return null;
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
        SwingUtilities.invokeLater(() -> new Main());
    }
}