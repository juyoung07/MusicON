package src.musicOn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        setTitle("MusicOn");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        GamePanel gamePanel = new GamePanel();
        SelectAlbum selectAlbum = new SelectAlbum();

        mainPanel.add(gamePanel, "GamePanel");
        mainPanel.add(selectAlbum, "SelectAlbum");

        add(mainPanel);
        setVisible(true);
    }

    class GamePanel extends JPanel {
        private Image backgroundImage;
        private JButton startButton, endUpButton;

        public GamePanel() {
            setLayout(null);
            loadBackgroundImage();
            loadButtonImages();

            // 버튼 위치 설정
            setButtonPosition(startButton, 65, 373); // Start 버튼 위치
            setButtonPosition(endUpButton, 65, 566); // End 버튼 위치

            add(startButton);
            add(endUpButton);

            startButton.addActionListener(e -> cardLayout.show(mainPanel, "SelectAlbum"));
            endUpButton.addActionListener(e -> System.exit(0));
        }

        private void loadBackgroundImage() {
            backgroundImage = loadImage("../img/bg/BgStart.png");
        }

        private void loadButtonImages() {
            Image startImage = loadImage("../img/btn/BtnStart.png");
            Image endImage = loadImage("../img/btn/BtnEnd.png");

            if (startImage != null && endImage != null) {
                startButton = createTransparentButton(new ImageIcon(startImage));
                endUpButton = createTransparentButton(new ImageIcon(endImage));
            } else {
                startButton = new JButton("Start");
                endUpButton = new JButton("End");
            }
        }

        private JButton createTransparentButton(ImageIcon icon) {
            JButton button = new JButton(icon);
            button.setContentAreaFilled(false); // 배경색 제거
            button.setBorderPainted(false);    // 테두리 제거
            button.setFocusPainted(false);    // 포커스 표시 제거
            return button;
        }

        // 버튼 위치를 설정하는 메서드
        private void setButtonPosition(JButton button, int x, int y) {
            button.setBounds(x, y, button.getPreferredSize().width, button.getPreferredSize().height);
        }

        private Image loadImage(String path) {
            try (InputStream inputStream = getClass().getResourceAsStream(path)) {
                if (inputStream != null) {
                    return ImageIO.read(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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
        SwingUtilities.invokeLater(Main::new);
    }
}
