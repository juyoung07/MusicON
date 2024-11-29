package src.musicOn;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Chicago extends JPanel {
    private Image backgroundImage;

    public Chicago() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("../img/bg/chicagoInGame.png"));
            if (backgroundImage == null) {
                System.err.println("Error loading Chicago background image.");
            }
        } catch (IOException e) {
            System.err.println("Error loading Chicago background image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) { // Check if image loaded successfully
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.RED);
            g.drawString("Error loading image", 50, 50);
        }
    }
}