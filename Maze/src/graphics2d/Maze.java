package graphics2d;
import java.awt.*;
import java.util.*;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import java.util.Random;

public class Maze {

    static Scene2d scene= new Scene2d();
    static int screenWidth=960;
    static int screenHeight=520;
    static Screen newScreen= new Screen(screenWidth,screenHeight);
    static double rectangleWidth;
    static double rectangleHeight;
    static SceneObject2d[][] maze;
    static int Width;
    static int Height;
    static double coloursStart[]={0,0,0,0};
    static double coloursFinish[]={0,0,0,0};
    static boolean displayMazeBuild;
    static double colours[]={0,0,0,0};
    static boolean mazegrid[][];
    static Random rd= new Random();
    static double changeC[]={0,0,0,0};

    public static void drawNewRec(int x,int y){
        //int difx=x+(-(xprev-x)/2);
        //int dify=y+(-(yprev-y)/2);
        //rectangle newRect=new rectangle(rectangleWidth,rectangleHeight,x*(int)rectangleWidth,y*((int)rectangleHeight));
        //rectangle newRect2=new rectangle(rectangleWidth,rectangleHeight,difx*(int)rectangleWidth,dify*((int)rectangleHeight));

        rectangle newRect=new rectangle(rectangleWidth,rectangleHeight,(int)(rectangleWidth/2.0)+x*((int)rectangleWidth),(int)(rectangleHeight/2.0)+y*((int)rectangleHeight));


        double estimateSteps=((Width*Height)/2.0);


        for(int i=1;i<=3;i++){
            double val=(coloursFinish[i]-coloursStart[i])/estimateSteps;
            if(255>changeC[i]+val && changeC[i]+val>=0){
                changeC[i]+=val;
            }
        }

        colours=new double[]{0,changeC[1],changeC[2],changeC[3]};



        //System.out.println(255/((Width*Height)/4.0));
        newRect.setColor(colours);
        //rectangle newRect2=new rectangle(rectangleWidth,rectangleHeight,(int)(rectangleWidth/2.0)+difx*(int)rectangleWidth,(int)(rectangleHeight/2.0)+dify*((int)rectangleHeight));
        scene.addObject(newRect);
        newScreen.drawObject2d(newRect);
        maze[x][y]=newRect;
        //scene.addObject(newRect2);
        //maze[x][y]=newRect;



    }




    public static void step(int x,int y) throws InterruptedException {


        //Thread.sleep(2000);
        colours= new double[]{0, 255, 255, 255};
        drawNewRec(x,y);
        mazegrid[x][y]=true;
        Random rand = new Random();


        int[] order={1,2,3,4};
        for(int i=0;i< rand.nextInt(10);i++){
            int pos1=rand.nextInt(4);
            int pos2=rand.nextInt(4);
            int temp=order[pos1];
            order[pos1]=order[pos2];
            order[pos2]=temp;
        }
        int down=y-2;
        int up=y+2;
        int left=x-2;
        int right=x+2;
        /*
        for(int i=0;i<visited.length;i++){
            for(int j=0;j< visited[0].length;j++){
                if(visited[i][j]){
                    System.out.print("[1]");
                }else{
                    System.out.print("[ ]");
                }

            }
            System.out.println();
        }

         */

        for(int i=0;i<4;i++){
            //newScreen.drawScene2d(scene);

            if(order[i]==1 && down>=2 && mazegrid[x][down]==false && mazegrid[x][down+1]==false){
                if(displayMazeBuild) {
                    newScreen.gridResize();
                    newScreen.windowUpdate();
                }

                mazegrid[x][down+1]=true;
                drawNewRec(x,down+1);
                step(x,down);

            }
            else if(order[i]==2 && up<mazegrid[0].length && mazegrid[x][up]==false && mazegrid[x][up-1]==false){
                if(displayMazeBuild) {
                    newScreen.gridResize();
                    newScreen.windowUpdate();
                }

                mazegrid[x][up-1]=true;
                drawNewRec(x,up-1);
                step(x,up);

            }
            else if(order[i]==3 && left>=2 && mazegrid[left][y]==false && mazegrid[left+1][y]==false){
                if(displayMazeBuild) {
                    newScreen.gridResize();
                    newScreen.windowUpdate();
                }
                mazegrid[left + 1][y] = true;
                drawNewRec(left + 1, y);
                step(left, y);

            }
            else if(order[i]==4 && right<mazegrid.length && mazegrid[right][y]==false && mazegrid[right-1][y]==false){
                if(displayMazeBuild) {
                    newScreen.gridResize();
                    newScreen.windowUpdate();
                }
                mazegrid[right-1][y]=true;
                drawNewRec(right-1,y);
                step(right,y);



            }

        }

    }

    public static void wilsons(){

    }

    public static void primNeighbors(int cellCheck[],DLinkList<int[]> toCheck){
        for(int i=0;i<4;i++){
            int[] cell={cellCheck[0]+2*(i+1)%2*(int)Math.pow((-1),i/2),cellCheck[1]+2*i%2*(int)Math.pow((-1),i/2)};
            System.out.println("cell("+cell[0]+","+cell[1]+")");
            if(
                    cell[0] >= 0
                            && cell[0]< mazegrid.length
                            && cell[1] >= 0
                            && cell[1]< mazegrid[0].length
                            && mazegrid[cell[0]][cell[1]] == false
            ){
                toCheck.addFirst(new int[]{cell[0],cell[1]});
            }

        }

    }
    public static void prims(int x,int y){
        int[] start={x,y};
        mazegrid[start[0]][start[1]]=true;
        DLinkList<int[]> tocheck=new DLinkList<>();
        primNeighbors(start,tocheck);
        while(tocheck.size>0){
            int next=rd.nextInt(tocheck.size-1);
            int nextcell[]=tocheck.getNodeAt(next).element;
            int count=0;
            int pathcell[]={0,0};
            int newcell[]={0,0};
            for(int i=0;i<4;i++){
                int[] cell={nextcell[0]+2*(i+1)%2*(int)Math.pow((-1),i/2),nextcell[1]+2*i%2*(int)Math.pow((-1),i/2)};
                System.out.println("cell("+cell[0]+","+cell[1]+")");
                if(
                    cell[0] >= 0
                    && cell[0]< mazegrid.length
                    && cell[1] >= 0
                    && cell[1]< mazegrid[0].length
                    && mazegrid[cell[0]][cell[1]] == true
                ){
                    pathcell[0]=nextcell[0]+(i+1)%2*(int)Math.pow((-1),i/2);
                    pathcell[1]=nextcell[1]+i%2*(int)Math.pow((-1),i/2);
                    newcell[0]=cell[0];
                    newcell[1]=cell[1];
                    count++;
                }

            }
            if(count==1){
                mazegrid[pathcell[0]][pathcell[1]]=true;
                mazegrid[newcell[0]][newcell[1]]=true;


            }

        }

        /*
        while(wall.size>0){

        }

         */

    }

    public static void solve(int start[],int finish[]) throws InterruptedException {
        int check[][]={{0,1},{0,-1},{1,0},{-1,0}};
        boolean considered[][]=new boolean[mazegrid.length][mazegrid[0].length];
        boolean solved=false;
        DLinkList<int[]> considering=new DLinkList<>();
        considering.addLast(start);
        //changeC=0;
        while(!solved){
            DLinkList<int[]> newconsidering=new DLinkList<>();
            /*
            for(int[] item:considering){
                System.out.print(Arrays.toString(item)+",");

            }

             */

            for(int[] item:considering){

                //System.out.println(Arrays.toString(item));
                if(item[0]==finish[0] && item[1]==finish[1]){
                    solved=true;
                }else if(!solved){
                    for (int i = 0; i < 4; i++) {

                        if ((item[0] + check[i][0]) >= 0
                                && (item[0] + check[i][0]) < mazegrid.length
                                && (item[1] + check[i][1]) >= 0
                                && (item[1] + check[i][1]) < mazegrid[0].length
                                && mazegrid[item[0] + check[i][0]][item[1] + check[i][1]] == true
                                && considered[item[0] + check[i][0]][item[1] + check[i][1]]==false
                        ) {
                            int newCell[] = {item[0] + check[i][0], item[1] + check[i][1]};
                            newconsidering.addLast(newCell);


                            //drawNewRec(item[0] + check[i][0],item[1] + check[i][1]);


                            colours = new double[]{0, 255, 0, 0};
                            ((rectangle)maze[item[0] + check[i][0]][item[1] + check[i][1]]).setColor(colours);
                            newScreen.drawObject2d(maze[item[0] + check[i][0]][item[1] + check[i][1]]);
                            Thread.sleep(8);
                            newScreen.windowUpdate();

                        }

                    }

                    ((rectangle)maze[item[0]][item[1]]).setColor(colours);
                    //newScreen.drawObject2d(maze[item[0]][item[1]]);
                    drawNewRec(item[0],item[1]);

                    considered[item[0]][item[1]]=true;
                    considering=newconsidering;


                }

            }

            //newScreen.drawScene2d(scene);
            //newScreen.gridResize();
            //newScreen.windowUpdate();
        }



    }

    public static void maze2d() throws InterruptedException {

        Scanner sc= new Scanner(System.in);

        System.out.println("Display Building of maze(true=1/false=0):");
        int s=sc.nextInt();
        if(s==1){
            displayMazeBuild=true;
        }else if(s==0){
            displayMazeBuild=false;
        }
        else{
            System.out.println("Invalid Input. Not displaying maze build");
            displayMazeBuild=false;
        }


        System.out.println("Enter size of maze(1-15):");
        int a=sc.nextInt();
        int height=9*a;
        int width=16*a;

        int cs[] = {0,0,255,255};
        int cf[] = {0,0,255,255};

        System.out.println("Would you like to change the colour of the maze solver?(yes=1,no=0):");
        int b=sc.nextInt();
        if(b==1){
            System.out.println("The path of the maze solver will be a gradient between two colours that are RGB values");
            System.out.println("Start Red value(0-255):");
            cs[1]=sc.nextInt();
            System.out.println("Start Green value(0-255):");
            cs[2]=sc.nextInt();
            System.out.println("Start Blue value(0-255):");
            cs[3]=sc.nextInt();

            System.out.println("finish Red value(0-255):");
            cf[1]=sc.nextInt();
            System.out.println("finish Green value(0-255):");
            cf[2]=sc.nextInt();
            System.out.println("finish Blue value(0-255):");
            cf[3]=sc.nextInt();
        }

        System.out.println("The maze will now try to find a path to the blue square which has been randomly placed in the maze");






        rectangleWidth=(screenWidth/width);
        rectangleHeight=(screenHeight/height);
         Width=width;
         Height=height;
        maze =new rectangle[width][height];

        /*
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if(rd.nextBoolean()){
                    rectangle newRect=new rectangle(rectangleWidth,rectangleHeight,(int)(rectangleWidth/2.0)+i*(int)rectangleWidth,(int)(rectangleHeight/2.0)+j*((int)rectangleHeight));
                    scene.addObject(newRect);
                    maze[i][j]=newRect;
                }
            }
        }*/

        mazegrid=new boolean[width][height];

        coloursStart[1]=255;
        coloursStart[2]=255;
        coloursStart[3]=255;

        coloursFinish[1]=255;
        coloursFinish[2]=255;
        coloursFinish[3]=255;

        changeC[1]=coloursStart[1];
        changeC[2]=coloursStart[2];
        changeC[3]=coloursStart[3];

        step(2,2);
        newScreen.drawScene2d(scene);
        newScreen.gridResize();
        newScreen.windowUpdate();

        int start[]={2,2};
        int finish[]={0,0};
        while(finish[0]==0 && finish[1]==0){
            int randx=rd.nextInt(width);
            int randy=rd.nextInt(height);
            if(mazegrid[randx][randy]==true){
                finish[0]=randx;
                finish[1]=randy;
            }
        }
        //finish[0]=width-2;
        //finish[1]=height-2;
        colours= new double[]{0, 255, 0, 0};
        ((rectangle)maze[start[0]][start[1]]).setColor(colours);
        newScreen.drawObject2d(maze[start[0]][start[1]]);

        colours= new double[]{0, 0, 0, 255};
        ((rectangle)maze[finish[0]][finish[1]]).setColor(colours);
        newScreen.drawObject2d(maze[finish[0]][finish[1]]);

        newScreen.windowUpdate();
        /*
        newScreen.drawScene2d(scene);
        newScreen.gridResize();
        newScreen.windowUpdate();

         */

        coloursStart[1]=cs[1];
        coloursStart[2]=cs[2];
        coloursStart[3]=cs[3];

        coloursFinish[1]=cf[1];
        coloursFinish[2]=cf[2];
        coloursFinish[3]=cf[3];

        changeC[1]=coloursStart[1];
        changeC[2]=coloursStart[2];
        changeC[3]=coloursStart[3];

        solve(start,finish);



    }
    /*
    public static void main(String[] args){
        //prims(0,0);
    }

     */



}
