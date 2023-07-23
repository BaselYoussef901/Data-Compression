import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class DataUserInput {
    public static void main(String[] args){
        //Text File Has (VectorWidth - VectorHeight - CodeBook)
        //And An Image To Compress
        /**Stages:                                                          */
        //1) You Design your image pixels as a 2D-matrix
        //2) Scale The end of each position to fit to the vectors
        //3) Reconstruct Your Main Image into Compressed Vectors
        //4) Show Errors and Compression

        /**             Super-Beso  20200111                Seif-Salman 20200241                **/
        String EnPath  = "_CompressedOutData\\";
        String DePath  = "_DeCompressedOutData\\";
        String TextOut = "_TextFilesOut\\";
        String Textin  = "_InputsData\\";

        System.out.println("\t\tWUBBA LUBBA DUB DUB!");
        try {
            Scanner inFile = new Scanner(new File(Textin+"_InputDataToCompress.txt"));
            int iWidth , iHeight , vWidth , vHeight , CodeBook;
            int [][]ImagePixels = ReadWriteImage.readImage(Textin+"CheckOn.jfif");
            iWidth  = ImagePixels.length;
            iHeight = ImagePixels[0].length;
            vWidth  = inFile.nextInt();
            vHeight = inFile.nextInt();
            CodeBook= inFile.nextInt();
            System.out.println("\t Image]: \t\tW: "+iWidth+"\t\tH: "+iHeight+"\t\tSize: "+iWidth*iHeight);
            BufferedWriter ImageData = new BufferedWriter(new FileWriter(TextOut+"ImageData.txt"));
            for(int i=0; i<iHeight; i++){
                for(int j=0; j<iWidth; j++){
                    ImageData.write(ImagePixels[i][j]+"\t");
                }
                ImageData.write("\n");
            }



            int sWidth  = (iWidth % vWidth==0? iWidth  : ((iWidth / vWidth)+1)*vWidth);
            int sHeight = (iHeight%vHeight==0? iHeight : ((iHeight/vHeight)+1)*vHeight);
            int [][]ScalePixels = new int[sHeight][sWidth];
            for(int i=0; i<sHeight; i++){
                int xx = (i>=iHeight? iHeight-1 : i);
                for(int j=0; j<sWidth; j++){
                    int yy = (j>=iWidth? iWidth-1 : j);
                    ScalePixels[i][j] = ImagePixels[xx][yy];
                }
            }
            BufferedWriter ScaleData = new BufferedWriter(new FileWriter(TextOut+"ScaleData.txt"));
            for(int i=0; i<sHeight; i++){
                for(int j=0; j<sWidth; j++){
                    ScaleData.write(ScalePixels[i][j]+"\t");
                }
                ScaleData.write("\n");
            }
            System.out.println("\t Scale]: \t\tW: "+sWidth+"\t\tH: "+sHeight+"\t\tSize: "+sWidth*sHeight);



            Vector<Vector<Integer>> myVectors = new Vector<>();
            for(int i=0; i<sHeight; i+=vHeight){
                for(int j=0; j<sWidth; j+=vWidth){
                    myVectors.add(new Vector<>());
                    for (int x=i; x<i+vHeight; x++) {
                        for (int y=j; y<j+vWidth; y++) {
                            myVectors.lastElement().add(ScalePixels[x][y]);
                        }
                    }
                }
            }
            BufferedWriter VectorData = new BufferedWriter(new FileWriter(TextOut+"VectorData.txt"));
            for(int i=0; i<myVectors.size(); i++){
                for(int j=0; j<myVectors.get(0).size(); j++){
                    VectorData.write(myVectors.get(i).get(j) + "\t");
                }
                VectorData.write("\n");
            }
            System.out.println("\tVectors]: \t\tW: "+myVectors.get(0).size()+"\t\tH: "+myVectors.size()+"\t\tSize: "+myVectors.get(0).size()*myVectors.size());
            //Vectors]: 	W: 4		H: 2500		Size: 10000         TestCase on: (vW=2,vH=2 & iW=512,iH=512)
            //Because we have 4 Elements in each row (100/4) 25 Vector per row -> 25*100 Column = 2500




            VectorQuantizing mySaviour = new VectorQuantizing();
            mySaviour.setData(myVectors , CodeBook);
            BufferedWriter FinalData = new BufferedWriter(new FileWriter(TextOut+"ReconstructedData.txt"));
            FinalData.write("CodeBook Quantization: ");
            for(int i=0; i<mySaviour.Quantizer.size(); i++){
                FinalData.write("{");
                for(int j=0; j<mySaviour.Quantizer.get(0).size(); j++){
                    FinalData.write(mySaviour.Quantizer.get(i).get(j) + ",");
                }
                FinalData.write("}  -  ");
            }
            FinalData.write("\n");
            for(int i=0; i<mySaviour.Final.size(); i++){
                for(int j=0; j<mySaviour.Final.get(0).size(); j++){
                    FinalData.write(mySaviour.Final.get(i).get(j) + "\t");
                }
                FinalData.write("\n");
            }
            System.out.println("Reconstructed]: \tW: "+mySaviour.Final.get(0).size()+"\t\tH: "+mySaviour.Final.size()+"\t\tSize: "+mySaviour.Final.get(0).size()*mySaviour.Final.size());



            int idx=0;
            int[][] EndPixels = new int[sHeight][sWidth];
            for(int i=0; i<sHeight; i+=vHeight){
                for(int j=0; j<sWidth; j+=vWidth){
                    Vector<Integer> Final = mySaviour.Quantizer.get(mySaviour.Position.get(idx++));
                    for(int k=0; k<vHeight; k++){
                        for(int p=0; p<vWidth; p++){
                            EndPixels[i+k][j+p] = Final.get(p+(k*vWidth));
                        }
                    }
                }
            }
            BufferedWriter End = new BufferedWriter(new FileWriter(EnPath+"EndPixelsImage.txt"));
            for(int i=0; i<sHeight; i++){
                for(int j=0; j<sWidth; j++){
                    End.write(EndPixels[i][j]+"\t");
                }
                End.write("\n");
            }
            ReadWriteImage.writeImage(EndPixels , EnPath+"CompressedImage.jpg");
        }
        catch(IOException e){
            e.printStackTrace();
        }




        /**                             ***                 ***                 ***                 **/

    }
}
