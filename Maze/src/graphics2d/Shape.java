package graphics2d;
import java.lang.Math;
import java.util.Arrays;
import java.util.Random;


public abstract class Shape extends SceneObject2d{
    public double[] color=new double[4];



}

class regularPolygon extends Shape{
    double radius;
    double[] position;
    int verticiesNum;
    double[] color=new double[4];

    regularPolygon(double r,int n,double x,double y){
        //initialize variables
        this.radius=r;
        int verticiesNum=n;

        //add center point and then add to polygons list of vertices
        this.addNewVertice(x,y);
        //loop through all the points given
        for(int i=0;i<verticiesNum;i++){
            //get the angle of the point starting with east most point winding counter clockwise
            double angle=(2.0*(double)Math.PI)*(((double)i)/((double)verticiesNum));//+((float)Math.PI/4.0f);
            this.addNewVertice(Math.cos(angle)*r+x,Math.sin(angle)*r+y);
        }
        //turn threw points  polygons starting with the east most point ,the middle point
        for(int i =1;i<(n+1);i++){
            if(i==(n+1)-1){
                this.addNewPolygon(0, i, 1);
            }else{
                this.addNewPolygon(0, i, i+1);
            }
            this.polygonColouring[i]=this.color;
        }
    }

    public void setColor(double[] color){
        for(int i=1;i<this.polygons.length;i++){
            this.polygonColouring[i]=color;
        }
    }

}

class rectangle extends Shape {
    double width;
    double length;


    rectangle(double width ,double length,int x, int y){
        this.width=width;
        this.length=length;
        Random rand=new Random();
        double[] pointColor={0,rand.nextInt(254),rand.nextInt(254),rand.nextInt(254)};
        this.addNewVertice(-width/2.0,length/2.0,pointColor);
        double[] pointColor1={0,rand.nextInt(254),rand.nextInt(254),rand.nextInt(254)};
        this.addNewVertice(width/2.0,length/2.0,pointColor1);
        double[] pointColor2={0,rand.nextInt(254),rand.nextInt(254),rand.nextInt(254)};
        this.addNewVertice(width/2.0,-length/2.0,pointColor2);
        double[] pointColor3={0,rand.nextInt(254),rand.nextInt(254),rand.nextInt(254)};
        this.addNewVertice(-width/2.0,-length/2.0,pointColor3);
        this.setPosition(x,y);



        this.addNewPolygon(0,1,2,this.color);
        this.addNewPolygon(0,2,3,this.color);

    }

    public void setColor(double[] color){
        for(int i=1;i<this.polygons.length;i++){
            this.polygonColouring[i]=color;
        }
    }







}