//package pac_man;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Points extends Rectangle{
    public Points(int x,int y){
        setBounds(x+10,y+10,5,5);
    }
    public void render(Graphics g)
    {
        g.setColor(Color.green);
        g.fillRect(x,y,width,height);
    }
}