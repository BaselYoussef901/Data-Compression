import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class VectorQuantizing {
    Vector<Vector<Integer>> Vectors = new Vector<>();
    Vector<Vector<Integer>> Quantizer = new Vector<>();
    Vector<Vector<Integer>> Final = new Vector<>();
    Vector<Integer>Average = new Vector<>();
    Vector<Integer> Error = new Vector<>();
    Vector<Integer> Position = new Vector<>();
    Double MSE=0.0;
    int CodeBook;

    /**     GUI Shit Variables          **/
    public static int vWidth , vHeight , codeBook;
    int iWidth , iHeight;

    VectorQuantizing(){}
    void setData(Vector<Vector<Integer>>Vectors , int CodeBook){
        this.Vectors = Vectors;
        this.CodeBook = CodeBook;
        QuantizationCompress();
    }
    void Average(Vector<Vector<Integer>> semiVector){
        int []SumIndex = new int[semiVector.get(0).size()];
        for(Vector<Integer> on:semiVector){
            for(int i=0; i<on.size(); i++)
                SumIndex[i] += on.get(i);
        }
        Average = new Vector<>();
        for(int i=0; i<SumIndex.length; i++)
            Average.add(SumIndex[i]/semiVector.size());
    }
    int Distance(Vector<Integer>semiVector , Vector<Integer>Operation , int Plus){
        int Distance = 0;
        for(int i=0; i<semiVector.size(); i++)
            Distance += Math.abs((semiVector.get(i) - Operation.get(i) + Plus));
        return Distance;
    }
    void Recursion(int CodeBook , Vector<Vector<Integer>>BinaryVectors){
        if(CodeBook==1 || BinaryVectors.size()==0){
            if(BinaryVectors.size()>0) {
                Average(BinaryVectors);
                Quantizer.add(Average);
            }
            return;
        }
        Average(BinaryVectors);
        Vector<Vector<Integer>> leftVectors  = new Vector<>();
        Vector<Vector<Integer>> rightVectors = new Vector<>();
        for(Vector<Integer> x:BinaryVectors){
            int Dis1 = Distance(x,Average,1);
            int Dis2 = Distance(x,Average,0);
            if(Dis1<=Dis2)
                leftVectors.add(x);
            else
                rightVectors.add(x);
        }
        Recursion(CodeBook/2 , leftVectors);
        Recursion(CodeBook/2 , rightVectors);
    }
    void QuantizationCompress(){
        Recursion(CodeBook,Vectors);
        FinalOptimizingData();
    }
    void FinalOptimizingData(){
        //Vector<Integer> Position = new Vector<>();
        for(Vector<Integer>x : Vectors){
            int minDistance = Distance(x,Quantizer.get(0),0);
            int index = 0;
            for(int i=1; i<Quantizer.size(); i++){
                int checkDistance = Distance(x,Quantizer.get(i),0);
                if(checkDistance < minDistance) {
                    minDistance = checkDistance;
                    index = i;
                }
            }
            Position.add(index);
        }

        for (int i = 0; i < Vectors.size(); i ++) {
            Final.add(Quantizer.get(Position.get(i)));
        }

        Error();
    }
    void Error(){
        for(int i=0; i<Vectors.size(); i++){
            for(int j=0; j<Vectors.get(0).size(); j++){
                int value = Math.abs(Vectors.get(i).get(j) - Final.get(i).get(j));
                Error.add(value*value);
                MSE+=value*value;
            }
        }
    }

}
