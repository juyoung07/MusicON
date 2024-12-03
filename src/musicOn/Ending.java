package src.musicOn;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ending extends JPanel {
    private final String GIF_PATH = "C:\\MusicON\\src\\img\\bg\\curtain.gif"; // Path to your GIF file
    private final String BG_SCORE_PATH = "C:\\MusicON\\src\\img\\bg\\BgScore.png"; // Path to the score background image
    private final int FINAL_SCORE; // The final score to display
    private JLabel scoreLabel; // To display the score

    public Ending(int score) {
        this.FINAL_SCORE = score;

        // Set up the layout and panel properties
        this.setLayout(new BorderLayout());

        // Set the background to the image (using BgScore.png)
        ImageIcon backgroundIcon = new ImageIcon(BG_SCORE_PATH);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        this.add(backgroundLabel, BorderLayout.CENTER);

        // Initialize score label
        scoreLabel = new JLabel("Final Score: " + FINAL_SCORE, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 50));
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
                showBackgroundWithScore(); // Show background and score
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Display the background image and score
    private void showBackgroundWithScore() {
        // Display the score in the center of the screen
        this.add(scoreLabel, BorderLayout.CENTER);

        // Refresh the panel to update the display
        revalidate();
        repaint();
    }
}
