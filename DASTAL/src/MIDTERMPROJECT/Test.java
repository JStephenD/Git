package MIDTERMPROJECT;

public class Test {
    public static void main(String[] args) {
        Notation nota = new Notation("12 + ( 23 * 3 )");
        if(nota.infixToPostfix()){
            System.out.println(nota.getPostfixResult());
        }
        else{
            System.out.println("string input error");
        }
    }
}
