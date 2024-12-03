package src.musicOn;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Chicago extends JPanel implements ActionListener {
    private final int PANEL_WIDTH = 1440; // 화면 너비
    private final int PANEL_HEIGHT = 1024; // 화면 높이
    private final int BLOCK_HEIGHT = 96; // 블록 높이
    private final int NUM_COLUMNS = 4; // 열 개수

    private ArrayList<Block> blocks = new ArrayList<>();
    private Timer timer;
    private Random random = new Random();

    private JLabel feedbackLabel; // 피드백 텍스트를 위한 JLabel
    private JLabel scoreLabel; // 점수를 표시할 JLabel
    private Image backgroundImage; // 배경 이미지
    private Clip backgroundMusicClip; // 배경 음악 클립

    private int score = 0; // 점수 초기화
    private boolean musicEnded = false; // 음악 종료 여부

    private boolean isGameStarted = false; // 게임 시작 여부 플래그

    public Chicago() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        // 배경 이미지 로드
        try {
            backgroundImage = new ImageIcon("C:\\MusicON\\src\\img\\bg\\chicagoInGame.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 피드백을 화면에 표시할 JLabel 설정
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 40));
        feedbackLabel.setForeground(Color.WHITE);
        feedbackLabel.setBounds(0, PANEL_HEIGHT / 2, PANEL_WIDTH, 50);
        this.setLayout(null);
        this.add(feedbackLabel);

        // 점수 표시용 JLabel 설정
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(400, 25, PANEL_WIDTH, 50);
        this.add(scoreLabel);

        setFocusable(true);
    }

    public void startGame() {
        if (isGameStarted) return;

        isGameStarted = true;

        // 포커스 요청
        this.requestFocusInWindow();

        // 배경 음악 로드 및 재생
        playBackgroundMusic("C:\\MusicON\\src\\songs\\WeBothReachedForTheGun.wav");

        // 타이머: 10ms 간격으로 동작
        timer = new Timer(10, this);
        timer.start();

        // 새로운 블록을 일정 간격으로 추가하는 타이머
        new Timer(1000, e -> spawnBlock()).start();

        // 키보드 입력 처리
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyChar());
            }
        });
    }


    // 배경 음악 재생
    private void playBackgroundMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.start(); // 음악을 한 번만 재생

            // 음악이 종료되면 점수 표시
            backgroundMusicClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    if (!musicEnded) {
                        musicEnded = true;
                        showFinalScore(); // 음악 종료 시 점수 표시
                    }
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // 최종 점수 화면 표시
    private void showFinalScore() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Ending endingPanel = new Ending(score);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(endingPanel);
        frame.revalidate();
        frame.repaint();
    }

    // 블록 생성
    private void spawnBlock() {
        int column = random.nextInt(NUM_COLUMNS); // 랜덤한 열 선택
        int columnWidth = PANEL_WIDTH / NUM_COLUMNS; // 열 너비 계산
        int x = column * columnWidth; // 블록의 x 좌표
        int blockWidth = columnWidth; // 블록의 가로 크기
        blocks.add(new Block(x, 0, column, blockWidth, BLOCK_HEIGHT));
    }

    // 키 입력 처리
    private void handleKeyPress(char key) {
        int column = -1;
        switch (key) {
            case 'd': column = 0; break;
            case 'f': column = 1; break;
            case 'j': column = 2; break;
            case 'k': column = 3; break;
        }

        if (column != -1) {
            boolean hit = false;
            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                if (block.column == column) {
                    if (block.y > PANEL_HEIGHT) {
                        setFeedback("miss", -2);
                    } else if (block.y >= 0 && block.y < 400) {
                        setFeedback("bad", -1);
                    } else if (block.y >= 400 && block.y < 500) {
                        setFeedback("good", 3);
                    } else if (block.y >= 500 && block.y < 600) {
                        setFeedback("great", 5);
                    } else if (block.y >= 600 && block.y < 700) {
                        setFeedback("bad", -1);
                    }
                    blocks.remove(i);
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                setFeedback("miss", -2);
            }
        }
    }

    // 피드백 텍스트와 점수 설정
    private void setFeedback(String text, int point) {
        feedbackLabel.setText(text);
        score += point;
        scoreLabel.setText("Score: " + score);

        Timer timer = new Timer(500, e -> feedbackLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Block> toRemove = new ArrayList<>();

        for (Block block : blocks) {
            block.y += 5; // 블록 이동 속도
        }

        for (Block block : blocks) {
            if (block.y > 700) {
                toRemove.add(block);
                setFeedback("miss", -2); // 화면을 넘어간 블록은 miss 처리
            }
        }

        blocks.removeAll(toRemove);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);
        }

        g.setColor(Color.GRAY);
        for (int i = 1; i < NUM_COLUMNS; i++) {
            int x = i * (PANEL_WIDTH / NUM_COLUMNS);
            g.drawLine(x, 0, x, PANEL_HEIGHT);
        }

        g.setColor(Color.RED);
        g.drawLine(0, 500, PANEL_WIDTH, 500);

        g.setColor(Color.YELLOW);
        g.drawLine(0, 600, PANEL_WIDTH, 600);

        g.setColor(Color.GREEN);
        g.drawLine(0, 700, PANEL_WIDTH, 700);

        Color customColor = new Color(0xA3060F); // #FF5733 색상
        g.setColor(customColor);

        for (Block block : blocks) {
            g.fillRect(block.x, block.y, block.width, block.height);
        }
    }

    private static class Block {
        int x, y, column, width, height;

        public Block(int x, int y, int column, int width, int height) {
            this.x = x;
            this.y = y;
            this.column = column;
            this.width = width;
            this.height = height;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chicago");
        Chicago game = new Chicago();

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
