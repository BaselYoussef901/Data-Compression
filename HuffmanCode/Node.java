import java.util.HashMap;
import java.util.PriorityQueue;
public class Node {
    public PriorityQueue<Node> HuffmanTree = new PriorityQueue<>(256, new CompareFreq());
    public HashMap<Character,String> d1    = new HashMap<Character,String>();        //char and his code
    public HashMap<String,Character> d2    = new HashMap<String,Character>();     //code and his char
    protected int freq;
    protected char info;
    public Node left,right;

    Node(){}
    public Node getParent(){
        Node root = null;
        while(HuffmanTree.size() > 1){
            Node left = HuffmanTree.peek();
            HuffmanTree.poll();
            Node right = HuffmanTree.peek();
            HuffmanTree.poll();


            Node parent = new Node();
            parent.freq = left.freq + right.freq;
            parent.info= '-';
            parent.left = left;
            parent.right = right;
            root = parent;
            HuffmanTree.add(parent);
        }
        return root;
    }

    public void AssignNodes(Node root , String code){
        if(root.left==null && root.right==null ){
            d1.put(root.info , code);       //root.info = Symbol
            d2.put(code , root.info);
            return;
        }
        AssignNodes(root.left , code+"0");
        AssignNodes(root.right, code+"1");
    }
    public void printNodes(Node root, String code){
        while(root.left!=null || root.right!=null){
            if(root.left!=null){

            }
            if(root.right!=null){

            }
        }
    }
}
