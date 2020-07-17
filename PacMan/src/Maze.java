
//package pac_man;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;

public class Maze extends Canvas implements Runnable, KeyListener {

    private boolean isRunning = false;
    public static Maze game;

    public static final String title = "Pac - Man";
    public static final String version = "1";
    public static final int width = 640;
    public static final int height = 480;
    private Thread thread;
    public static JFrame frame;
    public static Player player;
    public static Screen screen;
    public static final int pause_screen = 0, Game = 1, won_screen = 2, gameover_screen = 3;
    public static int state = -1;

    public Maze() {
        frame = new JFrame();
        frame.setTitle(title + " " + version);
        frame.setSize(Maze.width, Maze.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Dimension dimension = new Dimension(width, height);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        frame.addKeyListener(this);
        state = pause_screen;
        player = new Player(width / 2, height / 2);
        screen = new Screen("res/map.png");
        // C:/Users/Snigdha/Desktop/Snigdha/4th sem/java/Pac Man Project/PacMan/src/
    }

    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }

    }

    public synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        if (state == Game) {
            player.tick();
            screen.tick();
        }
    }

    private void render() {

        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null)
            frame.createBufferStrategy(3);

        else {
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, game.width, game.height);
            if (state == Game) {
                player.render(g);
                screen.render(g);
            } else if (state == pause_screen) {
                int boxwidth = 500, boxheight = 300;
                int xx = (Maze.width - boxwidth) / 2;
                int yy = (Maze.height - boxheight) / 2;
                g.setColor(Color.BLUE);
                g.fillRect(xx, yy, boxwidth, boxheight);
                g.setColor(Color.white);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                g.drawString("PRESS ENTER TO START THE GAME", xx + 60, yy + 90);

            } else if (state == won_screen) {
                int boxwidth = 500, boxheight = 300;
                int xx = (Maze.width - boxwidth) / 2;
                int yy = (Maze.height - boxheight) / 2;
                g.setColor(Color.GREEN);
                g.fillRect(xx, yy, boxwidth, boxheight);
                g.setColor(Color.white);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                g.drawString("YOU WON", xx + 60, yy + 80);
                g.drawString("Score:1260", xx + 60, yy + 120);
                g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 23));
                g.drawString("PRESS ENTER TO START THE GAME", xx + 60, yy + 150);

            } else if (state == gameover_screen) {
                int boxwidth = 500, boxheight = 300;
                int xx = (Maze.width - boxwidth) / 2;
                int yy = (Maze.height - boxheight) / 2;
                g.setColor(Color.RED);
                g.fillRect(xx, yy, boxwidth, boxheight);
                g.setColor(Color.white);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                g.drawString("GAME OVER", xx + 60, yy + 80);
                g.drawString("Score " + player.score, xx + 60, yy + 120);
                g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 23));
                g.drawString("PRESS ENTER TO START THE GAME", xx + 60, yy + 150);

            }
            g.dispose();
            bs.show();
        }

    }

    @Override
    public void run() {
        requestFocus();
        int fps = 0;
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double targetTick = 60.0;
        double delta = 0;
        double ns = 1000000000 / targetTick;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }
            if ((System.currentTimeMillis() - timer) >= 1000) {
                System.out.println(fps);
                fps = 0;
                timer += 1000;
            }
        }
        stop();
    }

    public static void main(String args[]) {

        // write your code here
        game = new Maze();

        game.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state == Game) {

            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                player.right = true;
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                player.left = true;
            if (e.getKeyCode() == KeyEvent.VK_UP)
                player.up = true;
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
                player.down = true;
        } else if (state == pause_screen) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                state = Game;
        } else if (state == gameover_screen) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                state = Game;
        } else if (state == won_screen) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                state = Game;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            player.right = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            player.left = false;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            player.up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            player.down = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
