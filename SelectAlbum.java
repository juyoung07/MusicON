import javax.swing.*;
import java.awt.*;

public class SelectAlbum extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public SelectAlbum() {
        setTitle("MusicOn"); // 타이틀 설정
        setSize(1200, 800); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 배경 이미지를 표시할 JPanel 생성
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon("C:\\MusicON\\Img\\Bg\\BgSongselection.png").getImage(); // 배경 이미지 로드

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
        addSongPanel("Kinky Boots", "Land of Lola", "Img\\Album\\AlbumKinkyboots.png");
        addSongPanel("Wicked", "Defying Gravity", "Img\\Album\\AlbumWicked.png");
        addSongPanel("Chicago", "All That Jazz", "Img\\Album\\AlbumChicago.png");

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

        setContentPane(backgroundPanel); // 프레임의 컨텐트팬을 커스텀 패널로 설정
        setVisible(true); // 프레임 보이기
    }

    // 곡 표지와 제목을 나타낼 패널을 추가하는 메소드
    private void addSongPanel(String title, String song, String imagePath) {
        JPanel panel = new JPanel() {
            Image coverImage = new ImageIcon(imagePath).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(coverImage, getWidth() / 2 - 150, getHeight() / 2 - 150, 300, 300, this); // 앨범 이미지 크기 300x300으로 설정
            }
        };
        panel.setOpaque(false); // 곡 패널도 투명하게 설정
        panel.setLayout(new BorderLayout());

        // 제목과 노래 레이블을 담을 패널 생성
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS)); // 수직으로 쌓이도록 설정
        labelPanel.setOpaque(false); // 배경 투명하게 설정
        labelPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬 설정

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬

        JLabel songLabel = new JLabel(song, SwingConstants.CENTER);
        songLabel.setFont(new Font("Serif", Font.BOLD, 90));
        songLabel.setForeground(Color.WHITE);
        songLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬

        labelPanel.add(titleLabel);
        labelPanel.add(songLabel);

        // labelPanel을 하단 중앙에 위치하도록 설정
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setOpaque(false); // 투명하게 설정
        southPanel.add(labelPanel);

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

    public static void main(String[] args) {
        new SelectAlbum();
    }
}
