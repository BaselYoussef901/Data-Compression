import java.util.HashMap;
public class Dictionary {
    HashMap<String,Integer> Endictionary = new HashMap<>();
    HashMap<Integer,String> Dedictionary = new HashMap<>();
    int DictSize;
    Dictionary(){
        DictSize = 128;
        Endictionary.clear();
        Dedictionary.clear();
        for(int i=0; i<DictSize; i++){
            Endictionary.put(String.valueOf((char)i) , i);
            Dedictionary.put(i , String.valueOf((char)i));
        }
        //System.out.println(Arrays.asList(Endictionary));
    }


}
