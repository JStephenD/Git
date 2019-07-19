package PRELIMPROJECT;

import java.util.Random;
public class Die {
    static Random rand = new Random();
    private int val;

    public Die(int val){
        this.val = val;
    }
    public Die(){
        this(random());
    }

    private static int random(){
        return rand.nextInt(6) + 1;
    }

    public int getVal(){
        return val;
    }
}
