package src.musicOn;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class KinkyBoots extends JPanel implements ActionListener {
    private final int PANEL_WIDTH = 1440;
    private final int PANEL_HEIGHT = 1024;
    private final int BLOCK_HEIGHT = 96;
    private final int NUM_COLUMNS = 4;

    private ArrayList<Block> blocks = new ArrayList<>();
    private Timer timer;
    private Random random = new Random();

    private JLabel feedbackLabel;
    private JLabel scoreLabel;
    private Image backgroundImage;
    private Clip backgroundMusicClip;

    private int score = 0;
    private boolean musicEnded = false;
    private boolean isGameStarted = false;

    public KinkyBoots() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        try {
            backgroundImage = new ImageIcon("src/img/bg/kinkybootsInGame.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("DungGeunMo", Font.BOLD, 40));
        feedbackLabel.setForeground(Color.WHITE);
        feedbackLabel.setBounds(0, PANEL_HEIGHT / 2, PANEL_WIDTH, 50);
        this.setLayout(null);
        this.add(feedbackLabel);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("DungGeunMo", Font.BOLD, 40));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(550, 20, PANEL_WIDTH, 50);
        this.add(scoreLabel);

        setFocusable(true);
    }

    public void startGame() {
        if (isGameStarted) return;

        isGameStarted = true;
        this.requestFocusInWindow();

        playBackgroundMusic("src/songs/LandOfLola.wav");

        timer = new Timer(10, this);
        timer.start();

        new Timer(1000, e -> spawnBlock()).start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyChar());
            }
        });
    }

    private void playBackgroundMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.start();

            backgroundMusicClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && !musicEnded) {
                    musicEnded = true;
                    showFinalScore();
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void showFinalScore() {
        Database db = new Database();
        try {
            db.saveScore("kinkyboots", score); // 점수 저장
            System.out.println("점수 저장 완료: " + score);

        } catch (Exception e) {
            System.out.println("점수 저장 실패: " + e.getMessage());
        }

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Ending endingPanel = new Ending(score, "kinkyboots");

        frame.getContentPane().removeAll();
        frame.getContentPane().add(endingPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void spawnBlock() {
        int column = random.nextInt(NUM_COLUMNS);
        int columnWidth = PANEL_WIDTH / NUM_COLUMNS;
        int x = column * columnWidth;
        blocks.add(new Block(x, 0, column, columnWidth, BLOCK_HEIGHT));
    }

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
            block.y += 5;
        }

        for (Block block : blocks) {
            if (block.y > 700) {
                toRemove.add(block);
                setFeedback("miss", -2);
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

        Color customColor = new Color(0xEA0010);
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
        JFrame frame = new JFrame("KinkyBoots");
        KinkyBoots game = new KinkyBoots();

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
