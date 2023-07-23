import jdk.jfr.Frequency;

import javax.swing.*;                   //GUI
import java.awt.*;                      //Fonts
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;                       //Input-Output Files
class GUI extends JFrame{}

public class Main{

    public static void main(String []args) throws IOException {
        HashMap<Character,Integer> Frequency =new HashMap<Character,Integer>();
        GUI GUI_obj = new GUI();
        File ReadFile = new File ("Input.txt");

        JButton buttonRead = new JButton("Press Me!");
        JLabel Data = new JLabel();
        Data.setFont(new Font("SansSerif",Font.BOLD,15));
        buttonRead.setFont(new Font("SansSerif",Font.BOLD,15));
        buttonRead.setPreferredSize(new Dimension(720,50));

        Node myNodeObject = new Node();
        ActionListener ActionListen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    BufferedReader ReadFrom = new BufferedReader(new FileReader("Input.txt"));
                    String Sequence = ReadFrom.readLine();
                    ReadFrom.close();


                    /**                 Probabilities                 **/
                    for(int i=0; i<Sequence.length(); i++){
                        Integer freq = Frequency.get(Sequence.charAt(i));
                        if( freq==null ) {
                            freq = 0;
                            //System.out.print("X ");
                        }
                        Frequency.put(Sequence.charAt(i) , freq+1);
                    }

                    String OuterFace="<html>";
                    OuterFace+="<br/>"+"<br/>";
                    for(Character ch: Frequency.keySet()){
                        OuterFace += ("P("+ch+"): "+Frequency.get(ch)+'/'+Sequence.length()+"<br/>");
                    }
                    OuterFace+="<br/>";





                    /**                 Saving Each Char's Code                 **/
                    File Dictionary = new File("DictionaryCode.txt");
                    OutputStream OutDictionary = new BufferedOutputStream(new FileOutputStream(Dictionary));
                    byte[] DictionaryByte = new byte[1024];
                    for(Character ch: Frequency.keySet()){
                        Node node = new Node();
                        node.freq = Frequency.get(ch);
                        node.info = ch;
                        node.left = null;
                        node.right = null;
                        myNodeObject.HuffmanTree.add(node);
                    }
                    Node root = myNodeObject.getParent();
                    myNodeObject.AssignNodes(root,"");

                    for(Character ch: myNodeObject.d1.keySet()){
                        String temp= ch + ":" + myNodeObject.d1.get(ch) + '\n';
                        DictionaryByte = temp.getBytes(StandardCharsets.UTF_8);
                        OutDictionary.write(DictionaryByte);
                    }
                    OutDictionary.close();





                    /**                 Encoding                 **/
                    File BinaryCode = new File("BinaryCode.txt");
                    OutputStream OutBinaryCode = new BufferedOutputStream(new FileOutputStream(BinaryCode));
                    byte[] BinaryByte = new byte[1024];
                    String temp = "";
                    String DeCodingBinary="";
                    for(int i=0; i<Sequence.length(); i++){
                        DeCodingBinary += myNodeObject.d1.get(Sequence.charAt(i));

                        if(Sequence.charAt(i)==' '){
                            temp+='\n';
                            BinaryByte = temp.getBytes(StandardCharsets.UTF_8);
                            OutBinaryCode.write(BinaryByte);
                            continue;
                        }
                        temp += myNodeObject.d1.get(Sequence.charAt(i));
                    }
                    OutBinaryCode.close();
                    OuterFace+="The Encode is: "+"<br/>";
                    OuterFace+=temp.substring(0,temp.length()/2)+"<br/>";
                    OuterFace+=temp.substring(temp.length()/2,temp.length())+"<br/>";
                    //System.out.println(temp);




                    /**                 Decoding                 **/
                    OuterFace+="<hr/>";
                    Decompression DeCoding = new Decompression(DeCodingBinary , myNodeObject);
                    OuterFace+="Decomression:\t\t"+DeCoding.getResult()+"<br/>";
                    OuterFace+="<hr/>";
                    OuterFace+="Original Size:\t"+Sequence.length()*8+" bits"+"<br/>";
                    OuterFace+="Compressed Size:"+DeCodingBinary.length()+" bits"+"<br/>";
                    OuterFace+="<hr/>";
                    OuterFace+="</html>";
                    Data.setText(OuterFace);


                }catch(IOException IOE){
                    IOE.printStackTrace();
                }
            }
        };

        //InterFace Program
        GUI_obj.add(buttonRead);
        buttonRead.addActionListener(ActionListen);
        GUI_obj.add(Data);
        GUI_obj.setLayout(new FlowLayout());
        GUI_obj.setVisible(true);
        GUI_obj.setSize(850,1080);
        GUI_obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





    }
}
