package MIDTERM;

public class midlab01 {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++){
            System.out.println(i+" "+pentagonal(i));
//            System.out.println(fibonnaci(i));
        }

    }

    public static int fibonnaci(int n){
        return (n==0||n==1) ? 1 : (fibonnaci(n-1) + fibonnaci(n-2));
    }
    public static int pentagonal(int n){
        return (n==1) ? 1 : (n+(2 * (n-1)) + pentagonal(n-1));
    }
}
