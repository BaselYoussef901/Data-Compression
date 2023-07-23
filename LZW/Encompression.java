import javax.sound.midi.Sequence;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
public class Encompression {
    public Dictionary Dict_obj;
    List<Integer>Answer = new ArrayList<>();
    public int OriginalSize , EncompressedSize;
    Encompression(String Sequence){
        //ABAABABB
        Dict_obj = new Dictionary();
        String PrevCode="";     //A
        for(char ch:Sequence.toCharArray()){
            String newCode = PrevCode + ch;     //AA
            if(Dict_obj.Endictionary.containsKey(newCode))
                PrevCode = newCode;
            else{
                Answer.add(Dict_obj.Endictionary.get(PrevCode));            //65    66  65  130 131
                Dict_obj.Endictionary.put(newCode , Dict_obj.DictSize++);   //AB 128    BA 129  AA 130
                PrevCode = String.valueOf(ch);
            }
        }
        if(!PrevCode.isEmpty())
            Answer.add(Dict_obj.Endictionary.get(PrevCode));

        OriginalSize = Sequence.length()*8;
        EncompressedSize = Answer.size()*8;
    }
    public void outEncoding(){
        /**                         Output in File.txt                       **/
        try {
            BufferedWriter WriteInto = new BufferedWriter(new FileWriter("outEncode.txt",true));
            WriteInto.write("Tags are: \n-\t");
            for(int tag:Answer){
                WriteInto.write("["+tag+"] ");
            }
            WriteInto.write("\n-\tOriginal Size: "+OriginalSize+
                    "\t| in bits: "+(Integer.toBinaryString(OriginalSize)).length()+
                    "\t| in Binary: "+Integer.toBinaryString(OriginalSize));
            WriteInto.write("\n-\tEncompressed Size: "+
                    EncompressedSize+"\t| in bits: "+(Integer.toBinaryString(EncompressedSize)).length()+
                    "\t| in Binary: "+Integer.toBinaryString(EncompressedSize));
            WriteInto.write("\n-\tRatio: "+(double)EncompressedSize/(double)OriginalSize);
            WriteInto.write("\n___________________________________________________________________\n\n");
            WriteInto.close();
        } catch(IOException IOE){
            IOE.printStackTrace();
        }
    }
}
