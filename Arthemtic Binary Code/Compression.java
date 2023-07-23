import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Compression {
    int k,OriginalSize,CompressedSize;
    String Encoding="",temp;
    float lowerCheck,upperCheck,floatCode;
    Compression []Ratios;


    Compression(float lower,float upper){
        lowerCheck=lower;
        upperCheck=upper;
    }
    void Recursion(int index,boolean way){
    //way: [False-> 0.5  |  True->..]
       try{
            BufferedWriter Steps = new BufferedWriter(new FileWriter("MySteps.txt",true));
            if(Ratios[index].lowerCheck<0.5 && Ratios[index].upperCheck >=0.5)
                return;

            if(!way){
                Ratios[index] = new Compression( (Ratios[index].lowerCheck-(float)0.5)*2 , (Ratios[index].upperCheck-(float)0.5)*2);
                Encoding+="1";
            }else{
                Ratios[index] = new Compression( (Ratios[index].lowerCheck)*2 , (Ratios[index].upperCheck)*2);
                Encoding+="0";
            }

            if(Ratios[index].lowerCheck >=0.5) {
                Steps.write("Lower("+temp.charAt(index)+") = "+Ratios[index].lowerCheck+'\n');
                Steps.write("Upper(" + temp.charAt(index) + ") = " + Ratios[index].upperCheck + '\n');
                Recursion(index, false);
            }
            else if(Ratios[index].lowerCheck<0.5) {
                Steps.write("Lower("+temp.charAt(index)+") = "+Ratios[index].lowerCheck+'\n');
                Steps.write("Upper(" + temp.charAt(index) + ") = " + Ratios[index].upperCheck + '\n');
                Recursion(index, true);
            }
            else
                Steps.write("________________________\n\n");
            Steps.close();
        }catch(IOException IOE){
            IOE.printStackTrace();
        }
    }
    Compression(Probability ProbObj,String Text){
        try{
            BufferedWriter Steps = new BufferedWriter(new FileWriter("MySteps.txt",true));
            Steps.flush();
            getK(ProbObj);
            temp=Text;

            /////////////////////////
            ProbObj.probAsFloat[0].probability = (float)0.6;
            ProbObj.probAsFloat[0].lower = (float)0;
            ProbObj.probAsFloat[0].upper = (float)0.6;

            ProbObj.probAsFloat[1].probability = (float)0.2;
            ProbObj.probAsFloat[1].lower = (float)0.6;
            ProbObj.probAsFloat[1].upper = (float)0.8;

            ProbObj.probAsFloat[2].probability = (float)0.2;
            ProbObj.probAsFloat[2].lower = (float)0.8;
            ProbObj.probAsFloat[2].upper = (float)1;

            k=3;
            /////////////////////////



            Ratios = new Compression[Text.length()];
            Ratios[0] = new Compression(ProbObj.probAsFloat[0].lower , ProbObj.probAsFloat[0].upper);
            Steps.write("\n\nSymbol #1 is "+Text.charAt(0)+'\n');
            Steps.write("Lower("+Text.charAt(0)+") = "+Ratios[0].lowerCheck+'\n');
            Steps.write("Upper("+Text.charAt(0)+") = "+Ratios[0].upperCheck+'\n'+'\n');

            if(Ratios[0].lowerCheck >=0.5)
                Recursion(0,false);
            else if(Ratios[0].lowerCheck<0.5)
                Recursion(0,true);


            for(int i=1; i<Text.length(); i++) {
                Ratios[i] = new Compression(
                        (Ratios[i - 1].lowerCheck
                                + ((Ratios[i - 1].upperCheck - Ratios[i - 1].lowerCheck)
                                * ProbObj.probAsFloat[Text.charAt(i) - 65].lower))
                        ,
                        (Ratios[i - 1].lowerCheck
                                + ((Ratios[i - 1].upperCheck - Ratios[i - 1].lowerCheck)
                                * ProbObj.probAsFloat[Text.charAt(i) - 65].upper))
                    );
                //System.out.println("Symbol: "+Text.charAt(i)+"\tIndex: "+(Text.charAt(i)-65));
                //System.out.println(ProbObj.probAsFloat[Text.charAt(i) - 65].lower);
                //System.out.println(ProbObj.probAsFloat[Text.charAt(i) - 65].upper);

                Steps.write("Symbol #"+(i+1)+" is "+Text.charAt(i)+'\n');
                Steps.write("Lower("+Text.charAt(i)+") = "+Ratios[i].lowerCheck+'\n');
                Steps.write("Upper("+Text.charAt(i)+") = "+Ratios[i].upperCheck+'\n'+'\n');
                if(Ratios[i].lowerCheck >=0.5)
                    Recursion(i,false);
                else if(Ratios[i].lowerCheck<0.5)
                    Recursion(i,true);
            }

            Encoding+="1";
            int power=0;
            int Numerator=0;
            for(int i=Encoding.length()-1; i>=0; --i){
                if(Encoding.charAt(i)=='1')
                    Numerator+=Math.pow(2,power++);
                else
                    power++;
            }
            int Denumerator = (int)Math.pow(2,Encoding.length());
            floatCode = (float)Numerator/(float)Denumerator;
            for(int i=0; i<k-1; i++)
                Encoding+="0";
            OriginalSize = Text.length()*8;
            CompressedSize = Encoding.length();
            Steps.close();


            //for(int i=0; i<Ratios.length; i++)
            //    System.out.println(Ratios[i].lowerCheck+" "+Ratios[i].upperCheck);
            //System.out.println("\n\n");
            //for(int i=0; i<ProbObj.probAsFloat.length; i++)
            //    System.out.println(ProbObj.probAsFloat[i].lower+" "+ProbObj.probAsFloat[i].upper);

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

}
