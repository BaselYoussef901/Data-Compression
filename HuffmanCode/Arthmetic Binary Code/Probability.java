import java.io.*;
import java.util.HashMap;

public class Probability {
    HashMap<Character, Integer> Frequency = new HashMap<>();
    float probability,lower,upper;
    char symbol;
    Probability[] probAsFloat;
    Probability(){}
    Probability(float probability, char symbol) {
        this.probability = probability;
        this.symbol = symbol;
    }
    void setValue(float lower,float upper){
        this.lower = lower;
        this.upper = upper;
    }
    Probability(String Text) {
        for (int i = 0; i < Text.length(); i++) {
            Integer freq = Frequency.get(Text.charAt(i));
            if (freq == null) {
                freq = 0;
                //System.out.print("X ");
            }
            Frequency.put(Text.charAt(i), freq + 1);
        }

        int idx = 0;
        float range=0;
        probAsFloat = new Probability[Frequency.size()];
        for (Character ch : Frequency.keySet()) {
            probAsFloat[idx] = new Probability((float) Frequency.get(ch) / Text.length(), ch);
            probAsFloat[idx].setValue(range, range+probAsFloat[idx].probability);
            range += probAsFloat[idx++].probability;
        }
        try {
            BufferedWriter ProbabilityOut = new BufferedWriter(new FileWriter("Probability.txt"));
            for(int i=0; i<probAsFloat.length; i++){
                ProbabilityOut.write("P("+probAsFloat[i].symbol+"): "+(int)(probAsFloat[i].probability*Text.length())+"/"+Text.length()
                        +"\tRange["+probAsFloat[i].lower+"\t:\t"+probAsFloat[i].upper+"]"
                        +"\t\t"+probAsFloat[i].probability+'\n');
            }
            ProbabilityOut.close();
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }
}
