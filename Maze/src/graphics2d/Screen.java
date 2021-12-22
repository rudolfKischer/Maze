package graphics2d;
import java.util.Arrays;
import java.util.Random;

public class Screen {
    int[][][] pixelGrid;
    double[][] zBuffer;
    int[][][] outputPixelGrid=new int[960][540][3];
    //DrawWindow drawWindow;

    int size;
     String type;//black white,rgb,cym,char,value

    public Screen(int width,int height){
        this.size=size;
        pixelGrid= new int[width][height][3];
        zBuffer= new double[width][height];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                zBuffer[i][j]=Double.MAX_VALUE;
            }
        }
    }


    //2d
    public void makeEdgeScanline(double[] A,double[] B,int[][] scanline) {
        double[] upper = A;
        double[] lower = B;
        double[] left = A;
        double[] right = B;
        if (B[1] > A[1]) {
            upper = B;
            lower = A;
        }
        if (B[0] < A[0]) {
            left = B;
            right = A;
        }
        //min p0 max p1
        int scanlinemax;
        int scanlinemin;
        if((int)(upper[1])>this.pixelGrid[0].length){
             scanlinemax=this.pixelGrid[0].length;
        }else if((int)(upper[1])<0){
            scanlinemax=0;
        }else{
            scanlinemax=(int)(upper[1]);
        }

        if((int)(lower[1])>this.pixelGrid[0].length){
            scanlinemin=this.pixelGrid[0].length;
        }else if((int)(lower[1])<0){
            scanlinemin=0;
        }else{
            scanlinemin=(int)(lower[1]);
        }
        for (int i = scanlinemin; i < scanlinemax; i++) {

            int x = (int) (lower[0] + (((right[0] - left[0]) / (right[1] - left[1])) * (i - (int)(lower[1]))));


                //System.out.println(x);

                if (x < scanline[i][0] ) {
                    if(x<0){
                        scanline[i][0]=0;
                    }else{
                        scanline[i][0] = x;
                    }
                }
                if (x > scanline[i][1]  ) {
                    if(x>this.pixelGrid.length){
                        scanline[i][1]=this.pixelGrid.length;
                    }else{
                        scanline[i][1] = x;
                    }
                }




        }
    }

    public void clipPolygon(double[] A,double[] B,double[] C){

    }

    public void drawPolygon(double[] A,double[] B,double[] C,double[] color){
            // first is for where it is y coord,second 0=max min 1=color
            int[][] scanline=new int[this.pixelGrid[0].length][2];
            for(int k=0;k<scanline.length;k++) {
                scanline[k][0] = pixelGrid.length;
            }

        makeEdgeScanline(A,B, scanline);
        //System.out.println(Arrays.deepToString(scanline));

        makeEdgeScanline(B,C, scanline);

        makeEdgeScanline(C,A, scanline);

        //System.out.println(""+A.vertices[2]+A.vertices[0]);
        for(int i=0;i<scanline.length;i++){
            int k=1;


            for(int j=scanline[i][0];j<scanline[i][1];j++){
                    //this is for interpolating if need be
                    double length=scanline[i][1]-scanline[i][0]+scanline.length;
                    double scale=((k+i+1)/length);
                    k++;
                    //
                    pixelGrid[j][i][0]=(int)color[1];
                    pixelGrid[j][i][1]=(int)color[2];
                    pixelGrid[j][i][2]=(int)color[3];
            }
        }
    }

    public void drawPolygonColorInterpolated(int polygonNumber,SceneObject object){



        double[] A=object.verticesTransformed[object.polygons[polygonNumber][0]];
        double[] B=object.verticesTransformed[object.polygons[polygonNumber][1]];
        double[] C=object.verticesTransformed[object.polygons[polygonNumber][2]];

        double[] distAB={A[0]-B[0],A[1]-B[1]};
        double[] distBC={B[0]-C[0],B[1]-C[1]};
        double[] distCA={C[0]-A[0],C[1]-A[1]};
        // first is for where it is y coord,second 0=max min 1=color
        int[][] scanline=new int[this.pixelGrid[0].length][2];
        for(int k=0;k<scanline.length;k++) {
            scanline[k][0] = pixelGrid.length;
        }

        makeEdgeScanline(A,B, scanline);
        //System.out.println(Arrays.deepToString(scanline));

        makeEdgeScanline(B,C, scanline);

        makeEdgeScanline(C,A, scanline);

        //System.out.println(""+A.vertices[2]+A.vertices[0]);
        for(int i=0;i<scanline.length;i++){



            for(int j=scanline[i][0];j<scanline[i][1];j++){
                //this is for interpolating if need be
                double length=scanline[i][1]-scanline[i][0]+scanline.length;
                double denominator=(B[1]-C[1])*(A[0]-C[0])+(C[0]-B[0])*(A[1]-C[1]);
                double w1=((B[1]-C[1])*(j-C[0])+(C[0]-B[0])*(i-C[1]))/denominator;
                double w2=((C[1]-A[1])*(j-C[0])+(A[0]-C[0])*(i-C[1]))/denominator;
                double w3=1-w1-w2;
                double[] color={
                        0,
                        (int)(object.verticeColouring[object.polygons[polygonNumber][0]][1]*w1)+(int)(object.verticeColouring[object.polygons[polygonNumber][1]][1]*w2)+(int)(object.verticeColouring[object.polygons[polygonNumber][2]][1]*w3),
                        (int)(object.verticeColouring[object.polygons[polygonNumber][0]][2]*w1)+(int)(object.verticeColouring[object.polygons[polygonNumber][1]][2]*w2)+(int)(object.verticeColouring[object.polygons[polygonNumber][2]][2]*w3),
                        (int)(object.verticeColouring[object.polygons[polygonNumber][0]][3]*w1)+(int)(object.verticeColouring[object.polygons[polygonNumber][1]][3]*w2)+(int)(object.verticeColouring[object.polygons[polygonNumber][2]][3]*w3),
                };


                double depth=A[2]*w1+B[2]*w2+C[2]*w3;




                //if(w1+w2+w3>1.0){
                //    System.out.println("w1:"+w1);
                //    System.out.println("w2:"+w2);
                //    System.out.println("w3:"+w3);
                //}


                //
                //System.out.println(Arrays.toString(color));
                //System.out.println(Arrays.toString(color));
                for(int p=1;p<4;p++){
                    if(color[p]>255){
                        color[p]=255;
                    }
                    if(color[p]<0){
                        color[p]=0;
                    }
                }


                //if(w1>=0.0 && w2>=0.0 && w3>=0.0){



                if(depth<zBuffer[j][i]){
                    pixelGrid[j][i][0]=(int)color[1];
                    pixelGrid[j][i][1]=(int)color[2];
                    pixelGrid[j][i][2]=(int)color[3];
                    zBuffer[j][i]=depth;
                }





            }
        }
    }


    public void drawPolygonShadedColorInterpolated(int polygonNumber,SceneObject object){



        double[] A=object.verticesTransformed[object.polygons[polygonNumber][0]];
        double[] B=object.verticesTransformed[object.polygons[polygonNumber][1]];
        double[] C=object.verticesTransformed[object.polygons[polygonNumber][2]];
        double polygonShading=object.polygonShading[polygonNumber];


        // first is for where it is y coord,second 0=max min 1=color
        int[][] scanline=new int[this.pixelGrid[0].length][2];
        for(int k=0;k<scanline.length;k++) {
            scanline[k][0] = pixelGrid.length;
        }

        makeEdgeScanline(A,B, scanline);
        //System.out.println(Arrays.deepToString(scanline));

        makeEdgeScanline(B,C, scanline);

        makeEdgeScanline(C,A, scanline);

        //System.out.println(""+A.vertices[2]+A.vertices[0]);
        for(int i=0;i<scanline.length;i++){



            for(int j=scanline[i][0];j<scanline[i][1];j++){
                //this is for interpolating if need be
                double length=scanline[i][1]-scanline[i][0]+scanline.length;
                double denominator=(B[1]-C[1])*(A[0]-C[0])+(C[0]-B[0])*(A[1]-C[1]);
                double w1=((B[1]-C[1])*(j-C[0])+(C[0]-B[0])*(i-C[1]))/denominator;
                double w2=((C[1]-A[1])*(j-C[0])+(A[0]-C[0])*(i-C[1]))/denominator;
                double w3=1-w1-w2;
                double[] color={
                        0,
                        (int)(object.verticeColouring[object.polygons[polygonNumber][0]][1]*w1)+(int)(object.verticeColouring[object.polygons[polygonNumber][1]][1]*w2)+(int)(object.verticeColouring[object.polygons[polygonNumber][2]][1]*w3),
                        (int)(object.verticeColouring[object.polygons[polygonNumber][0]][2]*w1)+(int)(object.verticeColouring[object.polygons[polygonNumber][1]][2]*w2)+(int)(object.verticeColouring[object.polygons[polygonNumber][2]][2]*w3),
                        (int)(object.verticeColouring[object.polygons[polygonNumber][0]][3]*w1)+(int)(object.verticeColouring[object.polygons[polygonNumber][1]][3]*w2)+(int)(object.verticeColouring[object.polygons[polygonNumber][2]][3]*w3),
                };

                for(int s=0;s<4;s++){

                    color[s]*=polygonShading;
                }



                for(int p=1;p<4;p++){
                    if(color[p]>255){
                        color[p]=255;
                    }
                    if(color[p]<0){
                        color[p]=0;
                    }
                }


                //if(w1>=0.0 && w2>=0.0 && w3>=0.0){
                double depth=A[2]*w1+B[2]*w2+C[2]*w3;



                if(depth<zBuffer[j][i]){
                pixelGrid[j][i][0]=(int)color[1];
                pixelGrid[j][i][1]=(int)color[2];
                pixelGrid[j][i][2]=(int)color[3];
                zBuffer[j][i]=depth;
                }





            }
        }
    }





    public void drawPolygonWire(double[] A,double[] B,double[] C,double[] color){
        drawLineSegment(new lineSegment(A,B,color ));
        drawLineSegment(new lineSegment(B,C,color ));
        drawLineSegment(new lineSegment(C,A,color ));
    }

    public void drawLineSegment(lineSegment line){
        double[] upper = line.A;
        double[] lower = line.B;
        double[] left = line.A;
        double[] right = line.B;
        if (line.B[1] > line.A[1]) {
            upper = line.B;
            lower = line.A;
        }
        if (line.B[0] < line.A[0]) {
            left = line.B;
            right = line.A;
        }
            double slope=((right[1] - left[1])/(right[0] - left[0]));
            int upperBound;

            if(Math.abs(slope)<1){
                upperBound=(int)(right[0]-left[0]);
            }else{
                upperBound=(int)(upper[1]-lower[1]);

            }

            for(int i=0;i<upperBound;i++){
                int XPoint;
                int YPoint;
                if(Math.abs(slope)<1){
                    YPoint=(int)((slope*i)+left[1]);
                    XPoint=i+(int)left[0];
                }else{
                    YPoint=i+(int)lower[1];
                    XPoint=(int)((((double)1/slope)*i)+lower[0]);
                }
                if(XPoint<this.pixelGrid.length && XPoint>0 && YPoint<this.pixelGrid[0].length && YPoint>0){
                    //System.out.println("x,y"+XPoint+","+YPoint);
                    for(int c=1;c<4;c++){
                        pixelGrid[XPoint][YPoint][c-1] = (int)line.color[c];
                    }
                }
            }
    }

    public void clear(){
        this.pixelGrid=new int[this.pixelGrid.length][this.pixelGrid[0].length][4];
        this.zBuffer=new double[this.pixelGrid.length][this.pixelGrid[0].length];
        for(int i=0;i<this.pixelGrid.length;i++){
            for(int j=0;j<this.pixelGrid[0].length;j++){
                zBuffer[i][j]=Double.MAX_VALUE;
            }
        }
    }

    public void gridResize(){

        double horizontalScale=(double)pixelGrid.length/ (double)outputPixelGrid.length;
        double verticalScale=(double)pixelGrid[0].length/ (double)outputPixelGrid[0].length;
        int[][][] intermediateGrid=new int[outputPixelGrid.length][pixelGrid[0].length][3];

        if(outputPixelGrid.length!=pixelGrid.length || outputPixelGrid[0].length!=pixelGrid[0].length){

            if(horizontalScale>1){
                for(int i=0;i< pixelGrid[0].length;i++){


                    for(int j=0;j< outputPixelGrid.length;j+=1){

                        int l=j*(int)horizontalScale;
                        int[] colorSum=new int[3];
                        for(int k=l;k<(int)horizontalScale+l;k++){

                            if(k< pixelGrid.length){
                                for(int rgb=0;rgb< colorSum.length;rgb++){
                                    colorSum[rgb]+=pixelGrid[k][i][rgb];
                                }
                            }
                        }
                        for(int rgb=0;rgb< colorSum.length;rgb++){

                            colorSum[rgb]/=(int)horizontalScale;
                        }

                        intermediateGrid[j][i]=colorSum;

                    }
                }
            }else{
                for(int i=0;i<pixelGrid[0].length;i++){

                    for(int j=0;j< outputPixelGrid.length;j+=(1/horizontalScale)){


                        int l=(int)(j/(1/horizontalScale));
                        for(int k=j;k<(1/horizontalScale)+j;k++){

                            if(k< intermediateGrid.length) {


                                intermediateGrid[k][i] = pixelGrid[l][i];
                            }
                        }
                    }
                }

            }
            //System.out.println(Arrays.deepToString(intermediateGrid));

            //also not sure if horizontal is correct
            //have to resize vor vertical now

            if(verticalScale>1){
                for(int i=0;i< intermediateGrid.length;i++){

                    for(int j=0;j< outputPixelGrid[0].length;j++){
                        int l=j*(int)verticalScale;
                        int[] colorSum=new int[3];
                        for(int k=l;k<(int)verticalScale+l;k++){
                            if(k< intermediateGrid[0].length){
                                for(int rgb=0;rgb< colorSum.length;rgb++){
                                    colorSum[rgb]+=intermediateGrid[i][k][rgb];
                                }
                            }
                        }
                        for(int rgb=0;rgb< colorSum.length;rgb++){
                            colorSum[rgb]/=(int)verticalScale;
                        }

                        outputPixelGrid[i][j]=colorSum;

                    }
                }

            }else{
                for(int i=0;i<intermediateGrid.length;i++){

                    for(int j=0;j< outputPixelGrid[0].length;j+=(1/verticalScale)){
                        int l=(int)(j/(1/verticalScale));
                        for(int k=j;k<((1/verticalScale)+j);k++){
                            if(k< outputPixelGrid[0].length){
                                outputPixelGrid[i][k]=intermediateGrid[i][l];
                            }


                        }
                    }
                }

            }
        }else{
            for(int i=0;i<outputPixelGrid.length;i++){
                for(int j=0;j<outputPixelGrid[0].length;j++){
                    outputPixelGrid[i][j]=pixelGrid[i][j];
                }
            }
        }



    }

    public void drawObject2d(SceneObject2d o){
        o.transform();
        for(int p=1;p<o.polygons.length;p++){
            drawPolygon(o.verticesTransformed[o.polygons[p][0]],o.verticesTransformed[o.polygons[p][1]],o.verticesTransformed[o.polygons[p][2]],o.polygonColouring[p]);
        }
    };

    public void drawScene2d(Scene2d scene){
        for(SceneObject2d o: scene.allWorldObjects){
            //System.out.println("position"+ Arrays.deepToString(o.position));
            //System.out.println("transformvert"+ Arrays.deepToString(o.verticesTransformed));
            //System.out.println("vertices"+ Arrays.deepToString(o.vertices));
            drawObject2d(o);
        }
    }

    public void drawScene2dColorInterpolated(Scene2d scene){
        for(SceneObject2d o: scene.allWorldObjects){
            o.transform();

            for(int p=1;p<o.polygons.length;p++){
                drawPolygonColorInterpolated(p,o);
            }
        }
    }



    public void drawScene2dWire(Scene2d scene){
        for(SceneObject2d o: scene.allWorldObjects){

            o.transform();
            for(int p=1;p<o.polygons.length;p++){
                drawPolygonWire(o.verticesTransformed[o.polygons[p][0]],o.verticesTransformed[o.polygons[p][1]],o.verticesTransformed[o.polygons[p][2]],o.polygonColouring[p]);
            }
        }
    }


    public void windowUpdate(){

        DrawWindow.pxlGrid=this.outputPixelGrid;
        DrawWindow.drawWindow.repaint();
    }

    //console text

    public float[][] whiteNoiseMap(int n){
        float[][] noiseMap= new float[n][n];
        Random random = new Random();
        for (int row=0;row<n;row++) {
            for (int column = 0; column < n; column++) {
                noiseMap[row][column]=random.nextFloat();
            }}
        return noiseMap;
    }

    //public void populateRandomTriangles

    public static char doubleGreyScaleToChar(float scale){
        String bitmap=" .:-=+*#%@";
        float input_start=0;
        float input_end=255;
        int output_start=0;
        int output_end=9;
        int output = (int)(output_start + ((output_end - output_start) / (input_end - input_start)) * (scale - input_start));
        return bitmap.charAt(output);


    }

    public void printScrn(){
        for (int row=this.pixelGrid.length-1;row>=0;row--) {
            for (int column = 0; column < this.pixelGrid.length; column++) {
                System.out.print(doubleGreyScaleToChar(this.pixelGrid[row][column][0])+" ");
            }
            System.out.print("\n");

        }
    }










}
