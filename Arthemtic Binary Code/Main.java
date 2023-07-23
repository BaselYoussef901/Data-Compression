import javax.swing.*;                   //GUI
import java.awt.*;                      //Fonts
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;                       //Input-Output Files
class GUI extends JFrame{}
public class Main {
    public static void main(String []args){
        GUI GUI_obj = new GUI();
        JButton encoding_button = new JButton("Encoding");
        JLabel encoding_Data = new JLabel();
        encoding_Data.setFont(new Font("SansSerif",Font.BOLD,15));
        encoding_button.setFont(new Font("SansSerif",Font.BOLD,15));
        encoding_button.setPreferredSize(new Dimension(720,50));
        ActionListener ActionListenEncoding = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    BufferedReader ReadFrom = new BufferedReader(new FileReader("EncodingInput.txt"));
                    String EncodingText = ReadFrom.readLine();
                    ReadFrom.close();
                    String Text="<html>";
                    Probability ProbObj = new Probability(EncodingText);
                    Compression CompObj = new Compression(ProbObj,EncodingText);
                    //System.out.println(CompObj.Encoding);
                    Text+="Text: "+EncodingText+"<br/>";
                    Text+="Encoding:         "+CompObj.Encoding+"<br/>";
                    Text+="Original Size:    "+CompObj.OriginalSize+"<br/>";
                    Text+="Original In Bits: "+Integer.toBinaryString(CompObj.OriginalSize).length()+"<br/>";
                    Text+="Compressed Size:  "+CompObj.CompressedSize+"<br/>";
                    Text+="Compressed In Bits: "+Integer.toBinaryString(CompObj.CompressedSize).length()+"<br/>";
                    Text+="BinaryFloatCode:  "+CompObj.floatCode+"<br/>";
                    Text+="<hr/>";
                    encoding_Data.setText(Text);
                    BufferedWriter WriteInto = new BufferedWriter(new FileWriter("EncodingOutput.txt"));
                    WriteInto.write("Encoding:         "+CompObj.Encoding+'\n');
                    WriteInto.write("Original Size:    "+CompObj.OriginalSize+'\n'
                            +"Original In Bits: "+Integer.toBinaryString(CompObj.OriginalSize).length()+'\n');
                    WriteInto.write("Compressed Size:  "+CompObj.CompressedSize+'\n'
                            +"Compressed In Bits: "+Integer.toBinaryString(CompObj.CompressedSize).length()+'\n');
                    WriteInto.write("BinaryFloatCode:      "+CompObj.floatCode+'\n');
                    WriteInto.close();
                }catch(IOException IOE){
                    IOE.printStackTrace();
                }
            }
        };
        GUI_obj.add(encoding_button);
        encoding_button.addActionListener(ActionListenEncoding);
        GUI_obj.add(encoding_Data);




        JButton decoding_button = new JButton("Decoding");
        JLabel decoding_Data = new JLabel();
        decoding_Data.setFont(new Font("SansSerif",Font.BOLD,15));
        decoding_button.setFont(new Font("SansSerif",Font.BOLD,15));
        decoding_button.setPreferredSize(new Dimension(720,50));
        ActionListener ActionListenDecoding = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    BufferedReader ReadFrom = new BufferedReader(new FileReader("DecodingInput.txt"));

                    String DecodingText = ReadFrom.readLine();      //110001100000
                    String L = ReadFrom.readLine();
                    StringTokenizer Token;
                    int DecodingLines = Integer.parseInt(L);        //3
                    float range=0;
                    Probability ProbObj = new Probability();
                    ProbObj.probAsFloat = new Probability[DecodingLines];
                    for(int i=0; i<DecodingLines; i++){
                        L = ReadFrom.readLine();
                        Token = new StringTokenizer(L);
                        char symbol = Character.valueOf(Token.nextToken().charAt(0));
                        float probability = Float.parseFloat(Token.nextToken());
                        ProbObj.probAsFloat[i] = new Probability(probability,symbol);
                        ProbObj.probAsFloat[i].setValue(range,range+ProbObj.probAsFloat[i].probability);
                        range+=ProbObj.probAsFloat[i].probability;
                    }
                    try {
                        BufferedWriter ProbabilityOut = new BufferedWriter(new FileWriter("Probability.txt"));
                        for(int i=0; i<ProbObj.probAsFloat.length; i++){
                            ProbabilityOut.write("P("+ProbObj.probAsFloat[i].symbol+"): "+ProbObj.probAsFloat[i].probability
                                    +"\tRange["+ProbObj.probAsFloat[i].lower+"\t:\t"+ProbObj.probAsFloat[i].upper+"]\n"
                                );
                        }
                        ProbabilityOut.close();
                    } catch (IOException IOE) {
                        IOE.printStackTrace();
                    }
                    ReadFrom.close();
                    Decompression DecompObj = new Decompression(ProbObj,DecodingText,DecodingLines);
                    String Text="<html>";
                    Text+="Text:             "+DecodingText+"<br/>";
                    Text+="Decoding:         "+DecompObj.Decoding+"<br/>";
                    Text+="Original Size:    "+DecompObj.OriginalSize+"<br/>";
                    Text+="Original In Bits: "+Integer.toBinaryString(DecompObj.OriginalSize).length()+"<br/>";
                    Text+="Compressed Size:  "+DecompObj.CompressedSize+"<br/>";
                    Text+="Compressed In Bits: "+Integer.toBinaryString(DecompObj.CompressedSize).length()+"<br/>";
                    Text+="BinaryFloatCode:  "+DecompObj.floatCode+"<br/>";
                    Text+="<hr/>";
                    encoding_Data.setText(Text);

                    BufferedWriter WriteInto = new BufferedWriter(new FileWriter("DecodingOutput.txt"));
                    WriteInto.write("Decoding:         "+DecompObj.Decoding+'\n');
                    WriteInto.write("Original Size:    "+DecompObj.OriginalSize+'\n'
                            +"Original In Bits: "+Integer.toBinaryString(DecompObj.OriginalSize).length()+'\n');
                    WriteInto.write("Compressed Size:  "+DecompObj.CompressedSize+'\n'
                            +"Original In Bits: "+Integer.toBinaryString(DecompObj.CompressedSize).length()+'\n');
                    WriteInto.write("BinaryFloatCode:  "+DecompObj.floatCode+'\n');
                    WriteInto.close();
                }catch(IOException IOE){
                    IOE.printStackTrace();
                }
            }
        };
        GUI_obj.add(decoding_button);
        decoding_button.addActionListener(ActionListenDecoding);
        GUI_obj.add(decoding_Data);
        GUI_obj.setLayout(new FlowLayout());
        GUI_obj.setVisible(true);
        GUI_obj.setSize(850,1080);
        GUI_obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
