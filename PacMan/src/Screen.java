
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
public class Screen {
    public int width,height;
    public Tile[][] tiles;
    public List<Points> points;
    public List<Enemy> enemies;

    public Screen (final String path) {
        points = new ArrayList<>();
        enemies = new ArrayList<>();
        try {
            final BufferedImage map = ImageIO.read(getClass().getResource(path));
            //final BufferedImage map = ImageIO.read(new File(path));
            this.width = map.getWidth();
            this.height = map.getHeight();
            final int[] pixels = new int[width * height];
            tiles = new Tile[width][height];
            map.getRGB(0, 0, width, height, pixels, 0, width);
            for (int xx = 0; xx < width; xx++)
                for (int yy = 0; yy < height; yy++) {
                    final int val = pixels[xx + (yy * width)];
                    if (val == 0xFF000000)
                        // tile
                        tiles[xx][yy] = new Tile(xx * 32, yy * 32);
                    else if (val == 0xFF0000FF) { // player
                        Maze.player.x = xx * 32;
                        Maze.player.y = yy * 32;
                    } else if (val == 0xFFFF0000) {
                        // enemy
                        enemies.add(new Enemy(xx * 50, yy * 32));
                    } else {
                        // points
                        points.add(new Points(xx * 32, yy * 32));
                    }

                }

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public void tick() {
        for (int i = 0; i < enemies.size(); i++)
            enemies.get(i).tick();
    }

    public void render(final Graphics g)
{
    for(int x=0;x<width;x++) {
        for (int y = 0; y < height; y++) {
            if (tiles[x][y] != null)
                tiles[x][y].render(g);
        }
    }
    for(int i=0;i<points.size();i++)
        points.get(i).render(g);
    for(int i=0;i<enemies.size();i++)
        enemies.get(i).render(g);

}
}




