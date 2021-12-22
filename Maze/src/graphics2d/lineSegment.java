package graphics2d;

public class lineSegment extends SceneObject2d {
    public double[] A;
    public double[] B;
    public double[] color=new double[4];
    public int width;

    lineSegment(double[] A,double [] B){
        this.A=A;
        this.B=B;
        this.width=10;

    }

    lineSegment(double[] A,double[] B, int width){
        this.A=A;
        this.B=B;
        this.width=width;

    }

    lineSegment(double[] A,double[] B, double[] color){
        this.A=A;
        this.B=B;
        this.color=color;

    }

}
