package MIDTERM;

public class TriangularNumber {
    public static void main(String[] args) {
        System.out.println(triangulinator(2));
    }
    static int triangulinator(int num){
        if (num == 1){
            return num;
        }
        else{
            return num+triangulinator(--num);
        }
    }
}
