package src.musicOn;

import javax.swing.*;

public class EndingTest {
    public static void main(String[] args) {
        // 테스트용 JFrame 생성
        JFrame frame = new JFrame("Ending Screen Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024); // Ending 패널 크기와 동일하게 설정
        frame.setLocationRelativeTo(null); // 화면 중앙에 배치

        // Ending 화면 생성 및 추가
        Ending endingPanel = new Ending(12345); // 테스트용 점수 12345 전달
        frame.add(endingPanel);

        // 화면 표시
        frame.setVisible(true);
    }
}
