package src.musicOn;

import javax.swing.*;
import java.awt.*;

public class Ending extends JPanel {
    private final int PANEL_WIDTH = 1440; // 화면 너비
    private final int PANEL_HEIGHT = 1024; // 화면 높이
    private int finalScore; // 최종 점수
    private Timer gifTimer; // GIF 타이머
    private JLabel backgroundLabel; // 배경 이미지를 위한 JLabel
    private JLabel scoreLabel; // 최종 점수 표시를 위한 JLabel

    public Ending(int score) {
        this.finalScore = score;

        // 패널 설정
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        // GIF 로드 및 크기 조정
        JLabel gifLabel = new JLabel();
        gifLabel.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT); // 패널 전체 크기로 설정
        ImageIcon gifIcon = new ImageIcon("src/img/bg/curtain.gif");

        // ImageIcon 크기 조정
        Image scaledGif = gifIcon.getImage().getScaledInstance(PANEL_WIDTH, PANEL_HEIGHT, Image.SCALE_DEFAULT);
        gifLabel.setIcon(new ImageIcon(scaledGif)); // 리사이즈된 GIF 설정
        this.add(gifLabel);

        // 최종 점수 표시용 JLabel 설정 (초기에는 보이지 않도록)
        scoreLabel = new JLabel(finalScore + "점", SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("DungGeunMo", Font.BOLD, 100));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(823, 130, 400, 200); // 오른쪽 위에 배치
        scoreLabel.setVisible(false); // 초기에는 숨김
        this.add(scoreLabel);

        // 2초 후에 GIF 제거 및 배경 이미지 표시
        gifTimer = new Timer(2000, e -> {
            this.remove(gifLabel); // GIF 제거
            showBackgroundImage(); // 배경 이미지 표시
            this.revalidate();
            this.repaint();
        });
        gifTimer.setRepeats(false);
        gifTimer.start();
    }

    // 배경 이미지 표시 메서드
    private void showBackgroundImage() {
        // 배경 이미지 로드
        ImageIcon backgroundImage = new ImageIcon("src/img/bg/BgScore.png"); // 배경 이미지 경로 설정
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        this.add(backgroundLabel, 0); // 배경 이미지를 가장 아래에 추가

        // 점수 표시를 다시 보이도록 설정
        scoreLabel.setVisible(true);
        this.setComponentZOrder(scoreLabel, 0); // 점수를 최상위로 설정
        this.setComponentZOrder(backgroundLabel, 1); // 배경을 그 아래로 설정
        this.revalidate();
        this.repaint();
    }
}
