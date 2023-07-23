import java.io.*;
import java.util.StringTokenizer;
public class LZW {
    public int []Tags;
    public int noOfTags;
    public void LZW_File(int condition){
        /**=====================================================================**/
        /**                           Encompression                             **/
        /**=====================================================================**/
        if(condition==1){
            Encompression Encoding_Obj , Encoding2_obj , Encoding3_obj;
            try {
                BufferedReader ReadFrom = new BufferedReader(new FileReader("inEncoding.txt"));
                //Sample: 1
                String SequenceString = ReadFrom.readLine();
                Encoding_Obj = new Encompression(SequenceString);
                Encoding_Obj.outEncoding();

                //Sample: 2
                SequenceString = ReadFrom.readLine();
                Encoding2_obj = new Encompression(SequenceString);
                Encoding2_obj.outEncoding();
                //Sample: 3
                SequenceString = ReadFrom.readLine();
                Encoding3_obj = new Encompression(SequenceString);
                Encoding3_obj.outEncoding();

                ReadFrom.close();
            } catch(IOException IOE){
                IOE.printStackTrace();
            }

        }

        /**=====================================================================**/
        /**                           Decompression                             **/
        /**=====================================================================**/
        else if(condition==2){
            Decompression Decoding_obj , Decoding2_obj;
            try {
                BufferedReader ReadFrom = new BufferedReader(new FileReader("inDecoding.txt"));
                //Sample: 1
                String str = ReadFrom.readLine();
                noOfTags = Integer.parseInt(str);
                Tags = new int[noOfTags];
                str = ReadFrom.readLine();
                StringTokenizer Tokenizer = new StringTokenizer(str);
                for (int i = 0; i < noOfTags; i++){
                    int codeNumber = Integer.parseInt(Tokenizer.nextToken());
                    Tags[i] = codeNumber;
                }
                Decoding_obj = new Decompression(Tags);
                Decoding_obj.outDecoding();


                //Sample: 2
                str = ReadFrom.readLine();
                noOfTags = Integer.parseInt(str);
                Tags = new int[noOfTags];
                str = ReadFrom.readLine();
                StringTokenizer Tokenizer2 = new StringTokenizer(str);
                for (int i = 0; i < noOfTags; i++){
                    int codeNumber = Integer.parseInt(Tokenizer2.nextToken());
                    Tags[i] = codeNumber;
                }
                Decoding2_obj = new Decompression(Tags);
                Decoding2_obj.outDecoding();

                ReadFrom.close();
            } catch(IOException IOE){
                IOE.printStackTrace();
            }

        }


    }
}
