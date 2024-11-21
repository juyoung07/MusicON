package src.musicOn;

import javax.swing.*;
import java.awt.*;

public class SelectAlbum extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public SelectAlbum() {
        setLayout(new BorderLayout()); // 메인 패널 레이아웃 설정

        // 배경 이미지를 표시할 JPanel 생성
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon(getClass().getResource("../img/bg/BgSongselection.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 그리기
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // CardLayout을 사용하여 노래 커버와 제목 패널 전환
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false); // 카드 패널을 투명하게 설정

        // 각 노래 패널 추가
        addSongPanel("Kinky Boots", "Land of Lola", "../img/album/AlbumKinkyboots.png");
        addSongPanel("Wicked", "Defying Gravity", "../img/album/AlbumWicked.png");
        addSongPanel("Chicago", "All That Jazz", "../img/album/AlbumChicago.png");

        backgroundPanel.add(cardPanel, BorderLayout.CENTER);

        // 좌우 이동 버튼 생성
        JButton leftButton = new JButton("<");
        leftButton.addActionListener(e -> switchCard(false));
        JButton rightButton = new JButton(">");
        rightButton.addActionListener(e -> switchCard(true));

        // 버튼 패널을 만들고 버튼을 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // 버튼 패널도 투명하게 설정
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(leftButton, BorderLayout.WEST);
        buttonPanel.add(rightButton, BorderLayout.EAST);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH); // 버튼 패널을 배경 패널의 남쪽에 추가

        add(backgroundPanel, BorderLayout.CENTER); // 메인 패널에 추가
    }

    // 곡 표지와 제목을 나타낼 패널을 추가하는 메소드
    private void addSongPanel(String title, String song, String imagePath) {
        // 앨범 이미지를 버튼으로 설정
        JButton albumButton = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        albumButton.setPreferredSize(new Dimension(200, 200)); // 버튼 크기 설정
        albumButton.setContentAreaFilled(false);
        albumButton.setBorderPainted(false);
        albumButton.setFocusPainted(false);

        albumButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Selected album: " + title + "\nSong: " + song);
        });

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

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

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setOpaque(false);
        southPanel.add(labelPanel);

        panel.add(albumButton, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        cardPanel.add(panel);
    }

    // 좌우 이동 버튼에 따른 패널 전환 메소드
    private void switchCard(boolean forward) {
        if (forward) {
            cardLayout.next(cardPanel);
        } else {
            cardLayout.previous(cardPanel);
        }
    }
}
