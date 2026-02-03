import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VolleyballGame extends JPanel implements ActionListener, KeyListener {

    Timer timer = new Timer(15, this);

    int ballX = 400, ballY = 100;
    int ballDX = 4, ballDY = 2;

    int p1X = 150, p1Y = 300;
    int p2X = 550, p2Y = 300;

    int p1Score = 0, p2Score = 0;

    boolean w, a, d, up, left, right;

    final int GROUND = 350;
    final int NET_X = 390;

    public VolleyballGame() {
        JFrame frame = new JFrame("🏐 Java Volleyball Game");
        frame.setSize(800, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 800, 450);

        // Ground
        g.setColor(Color.GREEN);
        g.fillRect(0, GROUND, 800, 100);

        // Net
        g.setColor(Color.WHITE);
        g.fillRect(NET_X, 250, 20, 100);

        // Ball
        g.setColor(Color.ORANGE);
        g.fillOval(ballX, ballY, 20, 20);

        // Players
        g.setColor(Color.BLUE);
        g.fillRect(p1X, p1Y, 40, 50);

        g.setColor(Color.RED);
        g.fillRect(p2X, p2Y, 40, 50);

        // Scores
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1: " + p1Score, 100, 30);
        g.drawString("Player 2: " + p2Score, 550, 30);

        if (p1Score == 5 || p2Score == 5) {
            g.drawString("GAME OVER", 340, 200);
            g.drawString("Press R to Restart", 310, 230);
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent e) {

        // Ball movement
        ballX += ballDX;
        ballY += ballDY;
        ballDY += 1; // gravity

        // Wall collision
        if (ballX <= 0 || ballX >= 780) ballDX *= -1;

        // Net collision
        if (ballX >= NET_X - 20 && ballX <= NET_X + 20 && ballY >= 250) {
            ballDX *= -1;
        }

        // Player collision
        Rectangle ball = new Rectangle(ballX, ballY, 20, 20);
        Rectangle p1 = new Rectangle(p1X, p1Y, 40, 50);
        Rectangle p2 = new Rectangle(p2X, p2Y, 40, 50);

        if (ball.intersects(p1)) {
            ballDY = -15;
            ballDX = 4;
        }

        if (ball.intersects(p2)) {
            ballDY = -15;
            ballDX = -4;
        }

        // Ground hit
        if (ballY >= GROUND) {
            if (ballX < 400) p2Score++;
            else p1Score++;
            resetBall();
        }

        // Player movement
        if (a && p1X > 0) p1X -= 5;
        if (d && p1X < 350) p1X += 5;
        if (left && p2X > 410) p2X -= 5;
        if (right && p2X < 760) p2X += 5;

        repaint();
    }

    void resetBall() {
        ballX = 400;
        ballY = 100;
        ballDY = 2;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> a = true;
            case KeyEvent.VK_D -> d = true;
            case KeyEvent.VK_W -> p1Y = 250;
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_UP -> p2Y = 250;
            case KeyEvent.VK_R -> {
                p1Score = p2Score = 0;
                timer.start();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> a = false;
            case KeyEvent.VK_D -> d = false;
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
        }
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new VolleyballGame();
    }
}
