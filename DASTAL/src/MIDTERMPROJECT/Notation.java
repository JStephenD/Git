package MIDTERMPROJECT;

import java.util.ArrayList;
import java.util.Arrays;

public class Notation {
    private ArrayList<String> input;
    private Stack stack;
    private ArrayList<String> result, postfixResult;

    public Notation(String input){
        this.input = new ArrayList<>(Arrays.asList(input.split(" ")));
        result = new ArrayList<>();
        stack = new Stack();
    }

    // NOTATION EQUATIONS
    public boolean infixToPostfix(){
        String x; // temp string
        stack.push("(");
        input.add(")"); // enclose equation with open and close parentheses

        System.out.println("input: " + input);

//        for (int i = 0; i < input.size(); i++){
//            item = input.get(i);
        for (String item : input){
            if(item.equals("(")) {
                stack.push(item);
            }
            else if (isNumeric(item)){
                result.add(item);
            }
            else if (isOperator(item)){
                x = stack.pop();
                while (isOperator(x) && precedence(x) >= precedence(item)){
                    result.add(x);
                    x = stack.pop();
                }
                stack.push(x);
                stack.push(item);
            }
            else if (item.equals(")")){
                x = stack.pop();
                while (!x.equals("(")){
                    result.add(x);
                    x = stack.pop();
                }
            }
            else{
                // error input string
                return false;
            }
        }
        postfixResult = result;

        System.out.println("result: "+result);
        System.out.println("stack: " + stack.getStack());

        return true; // valid infix to postfix
    }

    // GETTERS
    public ArrayList getPostfixResult(){
        return (postfixResult != null) ? postfixResult : new ArrayList();
    }
    public ArrayList getResult(){
        return (result != null) ? result : new ArrayList();
    }

    // FUNCTIONS
    private boolean isOperator(String x){ return "^*/+-".contains(x); }
    private int precedence(String x){
        if (x.equals("^")) return 3;
        else if (x.equals("*")||x.equals("/")) return 2;
        else if (x.equals("+")||x.equals("-")) return 1;
        else return 0;
    }
    private boolean isNumeric(String x){
        try{
            Double.parseDouble(x);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
