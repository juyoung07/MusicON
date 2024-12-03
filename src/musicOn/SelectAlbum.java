package src.musicOn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent; // KeyEvent를 import합니다.
import java.util.ArrayList;
import java.util.List;

public class SelectAlbum extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String currentAlbumKey;
    private List<String> albumKeys;
    private int currentIndex;

    public SelectAlbum() {
        setLayout(new BorderLayout());

        // 앨범 키 초기화
        albumKeys = new ArrayList<>();
        currentIndex = 0;

        // 배경 설정
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon(getClass().getResource("../img/bg/BgSongselection.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // 카드 레이아웃 설정
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // 앨범 추가
        addAlbumButton("Chicago", "All That Jazz", "../img/album/AlbumChicago.png", new Chicago());
        addAlbumButton("Wicked", "Defying Gravity", "../img/album/AlbumWicked.png", new Wicked());
        addAlbumButton("Kinky Boots", "Land of Lola", "../img/album/AlbumKinkyboots.png", new KinkyBoots());

        // 키 바인딩 설정
        setupKeyBindings();

        currentAlbumKey = albumKeys.get(currentIndex);
        cardLayout.show(cardPanel, currentAlbumKey + "Album");

        backgroundPanel.add(cardPanel, BorderLayout.CENTER);
        add(backgroundPanel, BorderLayout.CENTER);
    }

    private void addAlbumButton(String title, String song, String imagePath, JPanel gamePanel) {
        albumKeys.add(title);

        JButton albumButton = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        albumButton.setContentAreaFilled(false);
        albumButton.setBorderPainted(false);
        albumButton.setFocusPainted(false);

        albumButton.addActionListener(e -> {
            // currentAlbumKey 업데이트
            currentAlbumKey = title;
            currentIndex = albumKeys.indexOf(title);

            // 게임 패널로 전환
            if (gamePanel != null) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(gamePanel);
                frame.revalidate();
                frame.repaint();

                // 게임 시작 호출
                if (gamePanel instanceof Chicago) {
                    ((Chicago) gamePanel).startGame();
                }
                else if (gamePanel instanceof Wicked){
                    ((Wicked) gamePanel).startGame();
                }
                else if (gamePanel instanceof KinkyBoots){
                    ((KinkyBoots) gamePanel).startGame();
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // 앨범 정보 표시
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        titleLabel.setForeground(Color.WHITE);

        JLabel songLabel = new JLabel(song, SwingConstants.CENTER);
        songLabel.setFont(new Font("Serif", Font.BOLD, 90));
        songLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(false);
        labelPanel.add(titleLabel);
        labelPanel.add(songLabel);

        panel.add(albumButton, BorderLayout.CENTER);
        panel.add(labelPanel, BorderLayout.SOUTH);

        cardPanel.add(panel, title + "Album");
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");

        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveToNextAlbum();
            }
        });

        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveToPreviousAlbum();
            }
        });
    }

    private void moveToNextAlbum() {
        currentIndex = (currentIndex + 1) % albumKeys.size();
        currentAlbumKey = albumKeys.get(currentIndex);
        cardLayout.show(cardPanel, currentAlbumKey + "Album");
    }

    private void moveToPreviousAlbum() {
        currentIndex = (currentIndex - 1 + albumKeys.size()) % albumKeys.size();
        currentAlbumKey = albumKeys.get(currentIndex);
        cardLayout.show(cardPanel, currentAlbumKey + "Album");
    }
}
