package MIDTERMPROJECT;

import java.util.ArrayList;

public class Stack {
    ArrayList<String> stack;

    public Stack(){
        stack = new ArrayList<>();
    }

    public void push(String x){
        stack.add(x);
    }
    public String pop(){
        int lastIndex = stack.size() - 1;
        String item = stack.get(lastIndex);
        stack.remove(lastIndex);
        return item;
    }

    public ArrayList<String> getStack(){ return stack;}
}