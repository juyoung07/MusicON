package src.musicOn;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Ending extends JPanel {
    private final int PANEL_WIDTH = 1440; // 화면 너비
    private final int PANEL_HEIGHT = 1024; // 화면 높이
    private int finalScore; // 최종 점수
    private Image backgroundImage; // 배경 이미지를 위한 Image 객체
    private JLabel scoreLabel; // 최종 점수 표시를 위한 JLabel
    private String tableName; // 게임 테이블 이름
    private Database db;
    private JLabel rankingLabel;

    public Ending(int score, String tableName) {
        this.finalScore = score;
        this.tableName = tableName;
        db = new Database();

        // 패널 설정
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        // GIF 로드 및 크기 조정
        JLabel gifLabel = new JLabel();
        gifLabel.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT); // 패널 전체 크기로 설정
        ImageIcon gifIcon = new ImageIcon("src/img/bg/curtain.gif");
        Image scaledGif = gifIcon.getImage().getScaledInstance(PANEL_WIDTH, PANEL_HEIGHT, Image.SCALE_DEFAULT);
        gifLabel.setIcon(new ImageIcon(scaledGif)); // 리사이즈된 GIF 설정
        this.add(gifLabel);

        // 최종 점수 표시용 JLabel 설정 (초기에는 보이지 않도록)
        scoreLabel = new JLabel(finalScore + "점", SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("DungGeunMo", Font.BOLD, 100));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(823, 85, 400, 200); // 오른쪽 위에 배치
        scoreLabel.setVisible(false); // 초기에는 숨김
        this.add(scoreLabel);

        // 2초 후에 GIF 제거 및 배경 이미지 표시
        Timer gifTimer = new Timer(2000, e -> {
            this.remove(gifLabel); // GIF 제거
            showBackgroundImage(); // 배경 이미지 표시
            this.revalidate();
            this.repaint();
        });
        gifTimer.setRepeats(false);
        gifTimer.start();

        // 랭킹 라벨 추가
        rankingLabel = new JLabel(getRankingString(), SwingConstants.CENTER);
        rankingLabel.setFont(new Font("DungGeunMo", Font.BOLD, 80));
        rankingLabel.setForeground(Color.WHITE);
        rankingLabel.setBounds(500, 400, 400, 300); // 위치는 적절히 조정
        this.add(rankingLabel);
    }

    private String getRankingString() {
        ArrayList<Integer> topScores = db.getTop5Scores(tableName);
        StringBuilder sb = new StringBuilder("<html><center><br>");
        for (int i = 0; i < topScores.size(); i++) {
            sb.append(i + 1).append(". ").append(topScores.get(i)).append("<br>");
        }
        sb.append("</center></html>");
        return sb.toString();
    }

    // 배경 이미지 표시 메서드
    private void showBackgroundImage() {
        // 배경 이미지 로드
        backgroundImage = new ImageIcon("src/img/bg/BgScore.png").getImage(); // 배경 이미지를 Image 객체로 로드

        // 배경 이미지를 그리기 전에 버튼들을 먼저 그리기 위해 버튼 추가
        addButtons();

        // 점수 표시를 다시 보이도록 설정
        scoreLabel.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    private void addButtons() {
        // Home Button
        ImageIcon homeIcon = new ImageIcon("src/img/btn/BtnHome.png");
        Image homeScaled = homeIcon.getImage().getScaledInstance(105, 98, Image.SCALE_SMOOTH);
        JButton homeButton = new JButton(new ImageIcon(homeScaled));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);
        homeButton.setBounds(1290, 650, 105, 98);

        homeButton.addActionListener(e -> {
            // Home 버튼 클릭 시 Main 화면으로 이동
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // 현재 Ending 패널이 포함된 JFrame 가져오기
            frame.dispose(); // 현재 JFrame 닫기

            // 새로운 JFrame 생성 및 Main 화면 표시
            JFrame mainFrame = new JFrame("Main Screen");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1440, 1024); // 창 크기 설정
            Main mainPanel = new Main(); // Main 객체 생성
            mainFrame.getContentPane().add(mainPanel); // Main 패널 추가
            mainFrame.setVisible(true); // 새로운 JFrame 표시
        });

        this.add(homeButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 이미지를 화면 크기에 맞게 조정하여 그리기
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
