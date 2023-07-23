import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Decompression {
    String Decoding="";
    int OriginalSize,CompressedSize,k,ShiftingBits=0;
    float lowerCheck,upperCheck,floatCode,BinaryFloat;
    Decompression []Ratios;

    Decompression(float lower,float upper){
        lowerCheck=lower;
        upperCheck=upper;
    }

    void Recursion(int index,boolean way){
        if(Ratios[index].lowerCheck<0.5 && Ratios[index].upperCheck >=0.5)
            return;
        if(!way){
            Ratios[index] = new Decompression( (Ratios[index].lowerCheck-(float)0.5)*2 , (Ratios[index].upperCheck-(float)0.5)*2);
            ShiftingBits++;
        }else{
            Ratios[index] = new Decompression( (Ratios[index].lowerCheck)*2 , (Ratios[index].upperCheck)*2);
            ShiftingBits++;
        }


        if(Ratios[index].lowerCheck >=0.5) {
            //Steps.write("Lower("+temp.charAt(index)+") = "+Ratios[index].lowerCheck+'\n');
            //Steps.write("Upper(" + temp.charAt(index) + ") = " + Ratios[index].upperCheck + '\n');
            Recursion(index, false);
        }
        else if(Ratios[index].lowerCheck<0.5) {
            //Steps.write("Lower("+temp.charAt(index)+") = "+Ratios[index].lowerCheck+'\n');
            //Steps.write("Upper(" + temp.charAt(index) + ") = " + Ratios[index].upperCheck + '\n');
            Recursion(index, true);
        }
    }
    Decompression(Probability ProbObj,String DecodingText,int Lines){
        try{
            BufferedWriter Steps = new BufferedWriter(new FileWriter("MySteps.txt",true));
            Steps.flush();

            getK(ProbObj);
            String tempCheck;
            tempCheck = DecodingText.substring(0,k);

            Ratios = new Decompression[200];
            BinaryFloat = BinaryCalc(tempCheck);
            floatCode = BinaryFloat;
            char ch = getChar(ProbObj,Lines);
            Decoding += ch;
            Ratios[0] = new Decompression(ProbObj.probAsFloat[ch-65].lower , ProbObj.probAsFloat[ch-65].upper);
            if(BinaryFloat==0.5){
                return;
            }
            if(Ratios[0].lowerCheck >=0.5)
                Recursion(0,false);
            else if(Ratios[0].lowerCheck<0.5)
                Recursion(0,true);

            System.out.println(DecodingText);
            System.out.println("BinaryCode: "+BinaryFloat);
            System.out.println("Float: "+floatCode);
            System.out.println("Decode: "+Decoding);
            System.out.println("Shifts: "+ShiftingBits);
            System.out.println("tempCheck: "+tempCheck);
            System.out.println("Lower: "+Ratios[0].lowerCheck);
            System.out.println("Upper: "+Ratios[0].upperCheck+"\n\n");

            Steps.write("BinaryCode: "+BinaryFloat+'\n');
            Steps.write("Float: "+floatCode+'\n');
            Steps.write("Decode: "+Decoding+'\n');
            Steps.write("Shifts: "+ShiftingBits+'\n');
            Steps.write("tempCheck: "+tempCheck+'\n');
            Steps.write("Index: "+(ch-65)+"   Char: "+ch+'\n');
            Steps.write("Lower: "+Ratios[0].lowerCheck+'\n');
            Steps.write("Upper: "+Ratios[0].upperCheck+"\n\n\n");

            int idx=1;
            while(BinaryFloat!=0.5){
                tempCheck = DecodingText.substring(ShiftingBits,k+ShiftingBits);
                System.out.println("\t\t\tTempCheck: "+tempCheck);
                BinaryFloat = BinaryCalc(tempCheck);
                floatCode   = (BinaryFloat-Ratios[idx-1].lowerCheck)/(Ratios[idx-1].upperCheck-Ratios[idx-1].lowerCheck);
                ch = getChar(ProbObj,Lines);
                Decoding += ch;
                Ratios[idx] = new Decompression(
                        (Ratios[idx - 1].lowerCheck
                                + ((Ratios[idx - 1].upperCheck - Ratios[idx - 1].lowerCheck)
                                * ProbObj.probAsFloat[ch - 65].lower))
                        ,
                        (Ratios[idx - 1].lowerCheck
                                + ((Ratios[idx - 1].upperCheck - Ratios[idx - 1].lowerCheck)
                                * ProbObj.probAsFloat[ch - 65].upper))
                );
                //ShiftingBits=0;
                if(Ratios[idx].lowerCheck >=0.5)
                    Recursion(idx,false);
                else if(Ratios[idx].lowerCheck<0.5)
                    Recursion(idx,true);

                System.out.println("BinaryCode: "+BinaryFloat);
                System.out.println("Float: "+floatCode);
                System.out.println("Decode: "+Decoding);
                System.out.println("Shifts: "+ShiftingBits);
                System.out.println("tempCheck: "+tempCheck);
                System.out.println("Index: "+(ch-65)+"   Char: "+ch);
                System.out.println("Lower: "+Ratios[idx].lowerCheck);
                System.out.println("Upper: "+Ratios[idx].upperCheck+"\n\n");

                Steps.write("BinaryCode: "+BinaryFloat+'\n');
                Steps.write("Float: "+floatCode+'\n');
                Steps.write("Decode: "+Decoding+'\n');
                Steps.write("Shifts: "+ShiftingBits+'\n');
                Steps.write("tempCheck: "+tempCheck+'\n');
                Steps.write("Index: "+(ch-65)+"   Char: "+ch+'\n');
                Steps.write("Lower: "+Ratios[idx].lowerCheck+'\n');
                Steps.write("Upper: "+Ratios[idx].upperCheck+"\n\n\n");

                idx++;
            }

            System.out.println("Decoding Text is:  "+Decoding);
            OriginalSize = Decoding.length()*8;
            CompressedSize = DecodingText.length();

            Steps.close();
        }catch(IOException IOE){
            IOE.printStackTrace();
        }
    }




    void getK(Probability ProbObj){
        float smallestValue = 1;
        for(int i=0; i<ProbObj.probAsFloat.length; i++){
            if(ProbObj.probAsFloat[i].probability < smallestValue)
                smallestValue = ProbObj.probAsFloat[i].probability;
        }
        k = -log2(smallestValue) +1;
        //System.out.print(smallestValue+"  "+k);
    }
    public static int log2(float N) {
        float result = (float)(Math.log(N) / Math.log(2));
        return (int)result;
    }
    char getChar(Probability ProbObj,int len){
        char symbol='-';
        for(int i=0; i<len; i++){
            if(floatCode<=ProbObj.probAsFloat[i].upper && floatCode>= ProbObj.probAsFloat[i].lower) {
                symbol = ProbObj.probAsFloat[i].symbol;
                break;
            }
        }
        return symbol;
    }
    float BinaryCalc(String tempCheck){
        int power=0;
        int Numerator=0;
        for(int i=tempCheck.length()-1; i>=0; --i){
            if(tempCheck.charAt(i)=='1')
                Numerator+=Math.pow(2,power++);
            else
                power++;
        }
        int Denumerator = (int)Math.pow(2,k);
        return ((float)Numerator/(float)Denumerator);
    }
}
