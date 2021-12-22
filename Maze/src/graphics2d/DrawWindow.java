package graphics2d;
import java.awt.*;
import javax.swing.JFrame;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.AWTException;
import java.util.Arrays;
import java.util.Random;


public class DrawWindow extends JFrame {
    //public static Test tester=new Test();
    //public static Snake newSnake=new Snake();
    public static int[][][] pxlGrid=Maze.newScreen.outputPixelGrid;
    //public static int[][][] pxlGrid=tester.newScreen.outputPixelGrid;
    public static DrawWindow drawWindow = new DrawWindow("art",pxlGrid.length,pxlGrid[0].length);
    public static int forward=0;
    public static int right=0;
    public static int up=0;
    public static int mouseX;
    public static boolean mouseLock;
    public static int mouseY;
    private Image dbImage;
    private Graphics dbg;

    public DrawWindow(String title,int h, int l) {
        super(title);

        setSize(h,l);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        dbImage=createImage(getWidth(),getHeight());
        dbg= dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage,0,0,this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponents(g);


        for(int i=0;i<pxlGrid.length;i++){
            for(int j=0;j<pxlGrid[0].length;j++) {
                    drawPixel(g, i,pxlGrid[0].length-j , pxlGrid[i][j]);

            }
        }
    }

    public void paintImmediately(int x, int y,int w,int h){



    }




    public void drawPixel(Graphics g,int x, int y,int[] rgb){

        g.setColor(new Color(rgb[0],rgb[1],rgb[2]));
        g.drawRect(x, y, 1, 1);
    }

    public static void main(String[] args) throws InterruptedException {


        Maze.maze2d();




    }


}
