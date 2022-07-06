//package pac_man;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.File;
public class Enemy extends Rectangle {

    private final int random = 0, smart = 1, find_path = 2;
    private int state = random;
    private final int right = 0, left = 1, up = 2, down = 3;
    private int dir = -1;
    public Random randomGen;
    BufferedImage image;
    private int time = 0;
    private final int targetTime = 60 * 4;
    private final int speed = 1;
    private int lastdir = -1;

    public Enemy(final int x, final int y) {
        randomGen = new Random();
        setBounds(x, y, 32, 32);
        try {
            image = ImageIO.read(getClass().getResource("res/enemy1.png"));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        dir = randomGen.nextInt(4);
    }

    public void tick() {
        if (state == random) {
            if (dir == right)
                if (nomove(x + speed, y)) {
                    x += speed;
                    if (x >= 640) {
                        x = 0;
                        y = 224;
                    }
                } else
                    dir = randomGen.nextInt(4);

            else if (dir == left)
                if (nomove(x - speed, y)) {
                    x -= speed;
                    if (x <= 0) {
                        x = 640;
                        y = 192;
                    }
                } else
                    dir = randomGen.nextInt(4);
            else if (dir == up)
                if (nomove(x, y - speed)) {
                    y -= speed;
                } else
                    dir = randomGen.nextInt(4);
            else if (dir == down)
                if (nomove(x, speed + y))
                    y += speed;
                else
                    dir = randomGen.nextInt(4);
            time++;
            if (time == targetTime) {
                state = smart;
                time = 0;
            }
        } else if (state == smart) {
            boolean move = false;
            if (x < Maze.player.x && nomove(x + speed, y)) {
                x += speed;
                if (x >= 640) {
                    x = 0;
                    y = 224;
                }
                move = true;
                lastdir = right;
            }
            if (x > Maze.player.x && nomove(x - speed, y)) {
                x -= speed;

                if (x <= 0) {
                    x = 640;
                    y = 192;
                }
                move = true;
                lastdir = left;
            }
            if (y < Maze.player.y && nomove(x, speed + y)) {
                y += speed;
                move = true;
                lastdir = down;
            }
            if (y > Maze.player.y && nomove(x, y - speed)) {
                y -= speed;
                move = true;
                lastdir = up;
            }
            if (x == Maze.player.x && y == Maze.player.y)
                move = true;

            if (!move) {
                state = find_path;
            }
            time++;
            if (time == targetTime) {
                state = random;
                time = 0;
            }
        } else if (state == find_path) {
            if (lastdir == right) {
                if (y < Maze.player.y) {
                    if (nomove(x, y + speed)) {
                        y += speed;
                        state = smart;
                    }
                } else {
                    if (nomove(x, y - speed)) {
                        y -= speed;
                        state = smart;
                    }
                }
                if (nomove(x + speed, y)) {
                    x += speed;
                    state = smart;
                }
            } else if (lastdir == left) {
                if (y < Maze.player.y) {
                    if (nomove(x, y + speed)) {
                        y += speed;
                        state = smart;
                    }
                } else {
                    if (nomove(x, y - speed)) {
                        y -= speed;
                        state = smart;
                    }
                }
                if (nomove(x - speed, y)) {
                    x -= speed;
                    state = smart;
                }
            }

            else if (lastdir == up) {
                if (x < Maze.player.x) {
                    if (nomove(x + speed, y)) {
                        x += speed;
                        state = smart;
                    }
                } else {
                    if (nomove(x - speed, y)) {
                        x -= speed;
                        state = smart;
                    }
                }
                if (nomove(x, y - speed)) {
                    y -= speed;
                    state = smart;
                }
            } else if (lastdir == down) {
                if (x < Maze.player.x) {
                    if (nomove(x + speed, y)) {
                        x += speed;
                        state = smart;
                    }
                } else {
                    if (nomove(x - speed, y)) {
                        x -= speed;
                        state = smart;
                    }
                }
                if (nomove(x, speed + y)) {
                    y += speed;
                    state = smart;
                }
            }
        }
    }

    private boolean nomove(final int nextx, final int nexty) {
        final Rectangle bounds = new Rectangle(nextx, nexty, width, height);
        final Screen screen = Maze.screen;
        for (int xx = 0; xx < screen.tiles.length; xx++) {
            for (int yy = 0; yy < screen.tiles[0].length; yy++) {
                if (screen.tiles[xx][yy] != null)
                    if (bounds.intersects(screen.tiles[xx][yy]))
                        return false;
            }
        }
        return true;
    }

    public void render(final Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
}
