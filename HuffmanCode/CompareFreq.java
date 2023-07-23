import java.util.Comparator;
class CompareFreq implements Comparator<Node> {
    public int compare(Node x,Node y){
        return x.freq - y.freq;
    }
}