import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
public class Decompression {
    public Dictionary Dict_obj;
    public String Result="";
    public int OriginalSize , DecompressedSize;
    Decompression(int []Tags){
        Dict_obj = new Dictionary();
        String Search = String.valueOf((char)Tags[0]);
        boolean ok=false;
        for(int Code : Tags){
            String TextCode = (Dict_obj.Dedictionary.containsKey(Code)?
                                    Dict_obj.Dedictionary.get(Code) : Search + Search.charAt(0));
            Result+=TextCode;
            if(ok){
                //To Avoid First A | 128 = A we already have A=65
                Dict_obj.Dedictionary.put(Dict_obj.DictSize++, Search + TextCode.charAt(0));
            }
            ok=true;
            Search = TextCode;
        }
        OriginalSize = Result.length()*8;
        DecompressedSize = Tags.length*8;

    }
    public void outDecoding(){
        /**                         Output in File.txt                       **/
        try {
            BufferedWriter WriteInto = new BufferedWriter(new FileWriter("outDecode.txt",true));
            WriteInto.write("Text Code after Decompressing:\n-\t"+Result);
            WriteInto.write("\n-\tOriginal Size: "+OriginalSize+
                    "\t| in bits: "+(Integer.toBinaryString(OriginalSize)).length()+
                    "\t| in Binary: "+Integer.toBinaryString(OriginalSize));
            WriteInto.write("\n-\tDecompressed Size: "+
                    DecompressedSize+"\t| in bits: "+(Integer.toBinaryString(DecompressedSize)).length()+
                    "\t| in Binary: "+Integer.toBinaryString(DecompressedSize));
            WriteInto.write("\n-\tRatio: "+(double)DecompressedSize/(double)OriginalSize);
            WriteInto.write("\n___________________________________________________________________\n\n");
            WriteInto.close();
        } catch(IOException IOE){
            IOE.printStackTrace();
        }
    }
}
