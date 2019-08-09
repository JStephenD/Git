package MIDTERM;

public class Factorial {
    public static void main(String[] args) {
        System.out.println(factorinator(33));
    }
    static long factorinator(long num){
        if (num == 1){
            return num;
        }
        else{
            return num*factorinator(--num);
        }
    }
}
