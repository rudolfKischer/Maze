package graphics2d;

import java.util.Arrays;

public class SceneObject2d extends SceneObject{
    //public String name;
    //first slot in vertices stores an array with the info about the vertice list:{size of list,~,~}
    //public double[][] vertices;
    //first slot in polygon stores an array with the info about the polygon list:{size of list,~,~}
    //public double[][] polygons;
    //public double[][] scale;
    //public double[][] position;
    //public double[][] rotation;


    //initialization
    SceneObject2d(){
        name="Untitled";
        vertices=new double[3][3];
        verticeColouring=new double[3][4];
        polygons=new int[1][3];
        polygonColouring=new double[1][4];
        position=new double[3][3];
        rotation=new double[3][3];
        scale=new double[3][3];
        rotationAngles=new double[1];
        setPosition(0,0);
        setRotation(0);


    }

    SceneObject2d(String name){
        this.name=name;
        vertices=new double[3][3];
        polygons=new int[1][3];
    }
    //to string
    public String toString(){
        return this.name;
    }
    //adding Vertices
    public void addNewVertice(double x,double y){
        if(vertices[0][0]+1==vertices.length){
            vertices[0][0]+=1;
            double[][] temp=new double[(int)((vertices.length*1.5)+1)][3];
            for(int i=0;i<vertices.length;i++){
                for(int j=0;j<vertices[0].length;j++){
                    temp[i][j]=vertices[i][j];
                }
            }
            verticeColouring[0][0]+=1;
            double[][] tempColor=new double[(int)((verticeColouring.length*1.5)+1)][4];
            for(int i=0;i<verticeColouring.length;i++){
                for(int j=0;j<verticeColouring[0].length;j++){
                    tempColor[i][j]=verticeColouring[i][j];
                }
            }

            verticeColouring=tempColor;

            vertices=temp;
            vertices[(int)vertices[0][0]][0]=x;
            vertices[(int)vertices[0][0]][1]=y;
            vertices[(int)vertices[0][0]][2]=1;
        }else{
            verticeColouring[0][0]+=1;
            vertices[0][0]+=1;
            vertices[(int)vertices[0][0]][0]=x;
            vertices[(int)vertices[0][0]][1]=y;
            vertices[(int)vertices[0][0]][2]=1;
        }
    }

    public void addNewVertice(double x,double y,double[] color){
        if(vertices[0][0]+1==vertices.length){
            vertices[0][0]+=1;
            double[][] temp=new double[(int)((vertices.length*1.5)+1)][3];
            for(int i=0;i<vertices.length;i++){
                for(int j=0;j<vertices[0].length;j++){
                    temp[i][j]=vertices[i][j];
                }
            }
            verticeColouring[0][0]+=1;
            double[][] tempColor=new double[(int)((verticeColouring.length*1.5)+1)][4];
            for(int i=0;i<verticeColouring.length;i++){
                for(int j=0;j<verticeColouring[0].length;j++){

                    tempColor[i][j]=verticeColouring[i][j];
                }
            }

            verticeColouring=tempColor;
            verticeColouring[(int)verticeColouring[0][0]]=color;

            vertices=temp;
            vertices[(int)vertices[0][0]][0]=x;
            vertices[(int)vertices[0][0]][1]=y;
            vertices[(int)vertices[0][0]][2]=1;
        }else{
            verticeColouring[0][0]+=1;
            verticeColouring[(int)verticeColouring[0][0]]=color;
            vertices[0][0]+=1;
            vertices[(int)vertices[0][0]][0]=x;
            vertices[(int)vertices[0][0]][1]=y;
            vertices[(int)vertices[0][0]][2]=1;
        }
    }
    //adding polygons
    public void addNewPolygon(int A,int B,int C){
        if(polygons[0][0]+1==polygons.length){
            polygons[0][0]+=1;
            int[][] temp=new int[(int)((polygons.length*1.5)+1)][3];
            double[][] tempColouring=new double[(int)((polygons.length*1.5)+1)][4];
            for(int i=0;i<polygons.length;i++){
                for(int j=0;j<polygons[0].length;j++){
                    temp[i][j]=polygons[i][j];
                }
                for(int j=0;j< polygonColouring[0].length;j++){
                    tempColouring[i][j]= polygonColouring[i][j];
                }
            }
            polygons=temp;
            polygonColouring=tempColouring;
            polygons[polygons[0][0]][0]=A+1;
            polygons[polygons[0][0]][1]=B+1;
            polygons[polygons[0][0]][2]=C+1;
        }else{
            polygons[0][0]+=1;
            polygons[polygons[0][0]][0]=A+1;
            polygons[polygons[0][0]][1]=B+1;
            polygons[polygons[0][0]][2]=C+1;
        }
    }

    public void addNewPolygon(int A,int B,int C,double[] color){
        if(polygons[0][0]+1==polygons.length){
            polygons[0][0]+=1;
            int[][] temp=new int[(int)((polygons.length*1.5)+1)][3];
            double[][] tempColouring=new double[(int)((polygons.length*1.5)+1)][4];
            for(int i=0;i<polygons.length;i++){
                for(int j=0;j<polygons[0].length;j++){
                    temp[i][j]=polygons[i][j];
                }
                for(int j=0;j< polygonColouring[0].length;j++){
                    tempColouring[i][j]= polygonColouring[i][j];
                }
            }
            polygons=temp;
            polygonColouring=tempColouring;
            polygons[polygons[0][0]][0]=A+1;
            polygons[polygons[0][0]][1]=B+1;
            polygons[polygons[0][0]][2]=C+1;
            polygonColouring[polygons[0][0]]=color;
        }else{
            polygons[0][0]+=1;
            polygons[polygons[0][0]][0]=A+1;
            polygons[polygons[0][0]][1]=B+1;
            polygons[polygons[0][0]][2]=C+1;
            polygonColouring[polygons[0][0]]=color;
        }
    }
    //list vertices
    public void listVertices(){
        for(int i=1;i<vertices.length;i++){
            System.out.println("Point "+i+": [ ");
            for(int j=0;j<vertices[0].length;j++){
                System.out.print(vertices[i][j]+", ");
            }
            System.out.println("]");
        }
    }
    //list polygons
    public void listPolygons(){
        for(int i=1;i<polygons.length;i++){
            System.out.println("Polygon "+i+": [ ");
            for(int j=0;j<polygons[0].length;j++){
                System.out.print(polygons[i][j]+", ");
            }
            System.out.println("]");
        }
    }

    public void setPosition(double x,double y){
        for(int i=0;i<this.position.length;i++){
            this.position[i][i]=1;
        }
        this.position[2][0]=x;
        this.position[2][1]=y;
    }

    public double[] getPosition(){
        double[] pos = {position[2][0],position[2][1]};
        return pos;
    }

    public void translate(double x,double y){
        this.position[2][0]+=x;
        this.position[2][1]+=y;

    }
    //rotation
    public void setRotation(double angle){
        this.rotationAngles[0]=angle;
        double angleRad=(angle/180)*Math.PI;
        this.rotation[0][0]=Math.cos(angleRad);
        this.rotation[0][1]=-Math.sin(angleRad);
        this.rotation[1][0]=Math.sin(angleRad);
        this.rotation[1][1]=Math.cos(angleRad);
        this.rotation[2][2]=1;
    }
    public void rotate(double angle){
        this.rotationAngles[0]+=angle;
        setRotation(rotationAngles[0]);
    }


    //scaling
    //world Space Transformation
    //view space Transformation

    public void transform(){
        verticesTransformed=new double[vertices.length][vertices[0].length];
        double[][] transformationMat=Matrix.dotProduct(position,rotation);
        //System.out.println(Arrays.deepToString(transformationMat));
        //System.out.println(Arrays.deepToString(position));
        //System.out.println(Arrays.deepToString(transformationMat));
        for(int i=1;i<verticesTransformed.length;i++){
            verticesTransformed[i]=Matrix.vectorMatDotProduct(vertices[i],transformationMat);
            //System.out.println(Arrays.toString(verticesTransformed[i]));
        }
    }





}




