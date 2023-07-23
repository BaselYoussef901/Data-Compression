import java.util.*;
public class Main {
    public static void main(String []args){
        while(true){
            System.out.print("Would you like to try? (1.Encoding \t 2.Decoding): ");
            Scanner input = new Scanner(System.in);
            int FileType = input.nextInt();
            if(FileType==1){
                LZW Encoding = new LZW();
                Encoding.LZW_File(FileType);
            }else if(FileType==2){
                LZW Decoding = new LZW();
                Decoding.LZW_File(FileType);
            }else{
                System.exit(0);
            }
            System.out.println("Please Check Output files..\n");
        }
    }
}
