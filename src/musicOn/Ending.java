package src.musicOn;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ending extends JPanel {
    private final String GIF_PATH = "src/img/bg/curtain.gif"; // Path to your GIF file
    private final String BG_SCORE_PATH = "src/img/bg/BgScore.png"; // Path to the score background image
    private final int FINAL_SCORE; // The final score to display
    private JLabel scoreLabel; // To display the score
    private Font roundedFont; // Custom font
    private Image background; // To hold the background image

    private boolean showScoreBackground = false; // Flag to toggle between GIF and background

    public Ending(int score) {
        this.FINAL_SCORE = score;

        // Load the custom font (둥근모꼴)
        loadRoundedFont();

        // Load the background image
        background = Toolkit.getDefaultToolkit().getImage(BG_SCORE_PATH);

        // Set up the panel properties
        this.setLayout(new BorderLayout());

        // Initialize score label
        scoreLabel = new JLabel("현재 점수: " + FINAL_SCORE, SwingConstants.CENTER);
        scoreLabel.setFont(roundedFont.deriveFont(Font.BOLD, 50)); // Apply custom font
        scoreLabel.setForeground(Color.BLACK);

        // Display the GIF first
        showGif();
    }

    // Show GIF on the panel
    private void showGif() {
        // Create a JLabel to display the GIF
        JLabel gifLabel = new JLabel(new ImageIcon(GIF_PATH));
        this.add(gifLabel, BorderLayout.CENTER);

        // Set a timer to switch to the background image after GIF finishes
        Timer timer = new Timer(2000, new ActionListener() { // Assuming GIF duration is 2 seconds
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(gifLabel); // Remove the GIF
                showScoreBackground = true; // Set flag to show the score background
                add(scoreLabel, BorderLayout.CENTER); // Add score label
                revalidate();
                repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Paint the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showScoreBackground && background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Load 둥근모꼴 font
    private void loadRoundedFont() {
        try {
            // Assuming the font file is located at "src/font/rounded.ttf"
            File fontFile = new File("src/font/DungGeunMo.ttf");
            roundedFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Fallback to default font if custom font fails to load
            roundedFont = new Font("DungGeunMo", Font.PLAIN, 12);
        }
    }
}
