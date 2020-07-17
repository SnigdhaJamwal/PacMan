//package pac_man;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Rectangle{

    public boolean right=false,left=false,up=false,down=false;
    BufferedImage image;
    int speed=4;
    public static int score=0;

    public Player(int x,int y)
    {
        setBounds(x,y,32,32);
    try{
        image= ImageIO.read(Player.class.getResourceAsStream("res/image.png"));
        //image= ImageIO.read(new File("C:/Users/Snigdha/Desktop/Snigdha/4th sem/java/Pac Man Project/PacMan/src/res/image.png"));
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }

    public void tick(){
        if(right && nomove(x+speed,y)){
            x+=speed;
        if(x>=640)
        {x=0;
        y=224;}}
        if(left && nomove(x-speed,y)) {
            x-=speed;
            if(x<=0)
            {x=640;
                y=192;}
        }
        if(up  && nomove(x,y-speed)) {
            y-=speed;
        }
        if(down && nomove(x,speed+y))
            y+=speed;
        Screen screen=Maze.screen;
        for(int i=0;i<screen.points.size();i++)
            if(this.intersects(screen.points.get(i))){
            screen.points.remove(i);
            score=score+10;
            break;}
        //score=126-(screen.points.size());
        if(screen.points.size()==0) {
            System.out.println("Won");
            Maze.player=new Player(0,0);
            Maze.screen=new Screen("res/map.png");
            Maze.state=Maze.won_screen;
        }
        for(int i=0;i<screen.enemies.size();i++)
            if(this.intersects(screen.enemies.get(i))){

                Maze.player=new Player(0,0);
                Maze.screen=new Screen("res/map.png");
                Maze.state=Maze.gameover_screen;

                }
    }
    private boolean nomove(int nextx,int nexty){
        Rectangle bounds=new Rectangle(nextx,nexty,width,height);
        Screen screen=Maze.screen;
        for(int xx=0;xx<screen.tiles.length;xx++) {
            for (int yy = 0; yy < screen.tiles[0].length; yy++) {
                if (screen.tiles[xx][yy] != null)
                    if (bounds.intersects(screen.tiles[xx][yy]))
                        return false;

            }
        }

        return true;
    }
    public void render(Graphics g){
      g.drawImage(image,x,y,32,32,null);
    }
}
