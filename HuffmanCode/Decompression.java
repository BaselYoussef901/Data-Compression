public class Decompression {
    private String Result="";
    Decompression(String data,Node accessDictionary){
        String temp="";
        for(int i=0; i<data.length(); i++){
            temp += data.charAt(i);
            if(accessDictionary.d2.containsKey(temp)){
                Result += accessDictionary.d2.get(temp);
                temp="";
            }
        }
    }
    String getResult(){
        return Result;
    }
}
