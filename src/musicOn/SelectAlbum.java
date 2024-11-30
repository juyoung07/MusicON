package src.musicOn;

import javax.swing.*;
import java.awt.*;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SelectAlbum extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Chicago chicagoPanel;
    private KinkyBoots kinkyBootsPanel;
    private Wicked wickedPanel;
    private String currentAlbumKey;
    private boolean isInAlbumSelection = true; // 현재 앨범 선택 화면인지 확인하는 플래그

    public SelectAlbum() {
        setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon(getClass().getResource("../img/bg/BgSongselection.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        cardPanel.setFocusable(true);
        cardPanel.requestFocusInWindow();

        chicagoPanel = new Chicago();
        kinkyBootsPanel = new KinkyBoots();
        wickedPanel = new Wicked();

        addSongPanel("Chicago", "All That Jazz", "../img/album/AlbumChicago.png", chicagoPanel);
        addSongPanel("Wicked", "Defying Gravity", "../img/album/AlbumWicked.png", wickedPanel);
        addSongPanel("Kinky Boots", "Land of Lola", "../img/album/AlbumKinkyboots.png", kinkyBootsPanel);

        currentAlbumKey = "Chicago";

        backgroundPanel.add(cardPanel, BorderLayout.CENTER);
        add(backgroundPanel, BorderLayout.CENTER);

        setupKeyBindings();
    }

    private void addSongPanel(String title, String song, String imagePath, JPanel specialPanel) {
        JButton albumButton = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        albumButton.setPreferredSize(new Dimension(200, 200));
        albumButton.setContentAreaFilled(false);
        albumButton.setBorderPainted(false);
        albumButton.setFocusPainted(false);

        albumButton.addActionListener(e -> {
            isInAlbumSelection = false; // 게임 화면으로 전환 시 플래그 변경
            currentAlbumKey = title;
            cardLayout.show(cardPanel, title);
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        titleLabel.setForeground(Color.WHITE);

        JLabel songLabel = new JLabel(song, SwingConstants.CENTER);
        songLabel.setFont(new Font("Serif", Font.BOLD, 90));
        songLabel.setForeground(Color.WHITE);

        labelPanel.add(titleLabel);
        labelPanel.add(songLabel);

        panel.add(albumButton, BorderLayout.CENTER);
        panel.add(labelPanel, BorderLayout.SOUTH);

        cardPanel.add(panel, title + "Album");
        if (specialPanel != null) {
            cardPanel.add(specialPanel, title);
        }
    }

    private void setupKeyBindings() {
        cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "previousCard");
        cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "nextCard");

        cardPanel.getActionMap().put("previousCard", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isInAlbumSelection) { // 앨범 선택 화면일 때만 작동
                    switchCard(false);
                }
            }
        });

        cardPanel.getActionMap().put("nextCard", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isInAlbumSelection) { // 앨범 선택 화면일 때만 작동
                    switchCard(true);
                }
            }
        });
    }

    private void switchCard(boolean forward) {
        String nextAlbumKey = findNextAlbumKey(forward);
        cardLayout.show(cardPanel, nextAlbumKey + "Album");
        currentAlbumKey = nextAlbumKey;
    }

    private String findNextAlbumKey(boolean forward) {
        String[] albumKeys = {"Chicago", "Wicked", "Kinky Boots"};
        int currentIndex = -1;
        for (int i = 0; i < albumKeys.length; i++) {
            if (albumKeys[i].equals(currentAlbumKey)) {
                currentIndex = i;
                break;
            }
        }

        int nextIndex;
        if (forward) {
            nextIndex = (currentIndex + 1) % albumKeys.length;
        } else {
            nextIndex = (currentIndex - 1 + albumKeys.length) % albumKeys.length;
        }

        return albumKeys[nextIndex];
    }
}