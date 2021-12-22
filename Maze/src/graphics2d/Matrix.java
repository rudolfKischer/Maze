package graphics2d;

import java.util.Arrays;

public class Matrix{

    //double[][] xRotation={{1,0,0},{0,Math.cos(x),-Math.sin(x)},{0,Math.sin(x),Math.cos(x)}};
    //double[][] xRotation={{1,0,0},{0,-Math.sin(x),Math.sin(x)},{0,-Math.sin(x),Math.cos(x)}};
    //y
    //double[][] yRotation={{Math.cos(y),0,Math.sin(y)},{0,1,0},{-Math.sin(y),0,Math.cos(y)}};
    //z
    //double[][] zRotation={{Math.cos(z),-Math.sin(z),0},{Math.sin(z),Math.cos(z),0},{0,0,1}};


    //matrix operations should be done with with one long array and then given the dimensions mxn and nxp

    public static double[][] addition(double[][] A,double[][] B){
        if(A.length!=B.length || A[0].length != B[0].length){
            System.out.println("The addition of these matrices are undefined");
            return null;
        }
        double[][] C=new double[A.length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;i<A[0].length;j++){
                C[i][j]=A[i][j]+B[i][j];
            }
        }
        return C;
    }

    public static int[][] addition(int[][] A,int[][] B){
        if(A.length!=B.length || A[0].length != B[0].length){
            System.out.println("The addition of these matrices are undefined");

        }
        int[][] C=new int[A.length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;i<A[0].length;j++){
                C[i][j]=A[i][j]+B[i][j];
            }
        }
        return C;
    }

    public static double[] getVector(double[] A,double[] B){
        if(A.length != B.length){
            System.out.println("incompatible vertices");
        }
        double[] AB=new double[A.length];
        for(int i=0;i<A.length;i++){
            AB[i]=B[i]-A[i];
        }
        return AB;

    }

    public static double[][] scalarMultiplication(double[][] A,double lambda){
        double[][] lambdaA=new double[A.length][A[0].length];
        for(int i=0;i<A.length;i++){
            for(int j=0;i<A[0].length;j++){
                lambdaA[i][j]=A[i][j]*lambda;
            }
        }

        return lambdaA;
    }

    public static int[][] scalarMultiplication(int[][] A,int lambda){
        int[][] lambdaA=new int[A.length][A[0].length];
        for(int i=0;i<A.length;i++){
            for(int j=0;i<A[0].length;j++){
                lambdaA[i][j]=A[i][j]*lambda;
            }
        }

        return lambdaA;
    }

    public static double[][] dotProduct(double[][] A,double[][] B){
        if(A.length != B[0].length){
            System.out.println("multiplication not defined for these matrices");
            return null;
        }
        double[][] AB= new double[B.length][A[0].length];
        for(int i=0;i<B.length;i++){
            for(int j=0;j<A[0].length;j++){
                for(int k=0;k<A.length;k++){
                    AB[j][i]+=A[k][i]*B[j][k];
                }
            }
        }
        return AB;
    }
    public static double[] vectorMatDotProduct(double[] V,double[][] M){
        if(V.length != M[0].length){
            System.out.println("multiplication not defined for these matrices");
            return null;
        }
        double[] VM= new double[V.length];
            for(int j=0;j<V.length;j++){
                for(int k=0;k<M[0].length;k++){
                    VM[j]+=V[k]*M[k][j];
                }
            }
        return VM;
    }

    public static double vectorVectorDotProduct(double[] x1,double[] x2){
        if(x1.length != x2.length){
            System.out.println("multiplication not defined for these matrices");
        }
        double x12=0;
        for(int j=0;j<x1.length;j++){
            x12+=x1[j]*x2[j];
        }
        return x12;
    }

    public static int[][] dotProduct(int[][] A,int[][] B){
        if(A.length != B[0].length){
            System.out.println("multiplication not defined for these matrices");
            return null;
        }
        int[][] AB= new int[B.length][A[0].length];
        for(int i=0;i<B.length;i++){
            for(int j=0;j<A[0].length;j++){
                for(int k=0;k<A.length;k++){
                    AB[i][j]+=A[k][i]*B[j][k];
                }
            }
        }
        return AB;
    }

    public static double[] crossProductVector(double[] A,double[] B){
        double[][] CrossMatA={
                {0,A[2],-A[1]},
                {-A[2],0,A[0]},
                {A[1],-A[0],0}};

        return vectorMatDotProduct(B,CrossMatA);

    };

    public static double[] normalizeVector(double[] A){
        double mag=vectorMagnitude(A);
        double[] normalized=new double[A.length];
        for(int i=0;i<A.length;i++){
            normalized[i]=A[i]/mag;
        }

        return normalized;
    }

    public static double vectorMagnitude(double[] vector){
        double mag = Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
        return mag;
    }

    public static double getDeterminant(double[][] mat){
        if(mat==null || mat.length != mat[0].length){
            System.out.println("invalid Matrix");
            return 0;
        }
        if(mat.length == 2 && mat[0].length ==2){
            return mat[0][0]*mat[1][1] - mat[0][1]*mat[1][0];
        }else{
            double determinant=0.0;

            for(int i=0;i<mat.length;i++){
                double[][] cofactor= new double[mat.length-1][mat[0].length-1];
                int p=0;
                for(int ci=0;ci<mat.length;ci++){
                    if(ci != i){


                        for(int cj=0;cj<cofactor[0].length;cj++){
                            cofactor[p][cj]=mat[ci][cj+1];
                        }
                        p++;
                    }

                }
                determinant+=Math.pow(-1,i)*mat[i][0]*getDeterminant(cofactor);
            }

            return determinant;
        }

    }

    public static double[] getPlaneNormal(double[] A,double[] B,double[] C){
        double[] AB=getVector(A,B);
        double[] AC=getVector(A,C);

        double[] surfaceNormal=normalizeVector(crossProductVector(AB,AC));
        return surfaceNormal;


    }

    public static double getAngleBetweenVectors(double[] A,double[] B){
        double cosAngle= vectorVectorDotProduct(A,B)/(vectorMagnitude(A)*vectorMagnitude(B));
        return Math.acos(cosAngle);
    }

    public static void print(double[][] A){
        System.out.println("m:"+A.length+" n:"+A[0].length);
        for(int i=0;i<A.length;i++){
            System.out.print("[ ");
            for(int j=0;j<A[0].length;j++){
                System.out.print(A[j][i]+" ");
            }
            System.out.println("]");
        }


    }

    /*
    public static void main(String[] args){

    }
    */




}
