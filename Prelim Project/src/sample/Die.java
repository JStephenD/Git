package sample;

import java.util.Random;

public class Die implements Rollable{
    static Random rand = new Random();
    private int val;
    private boolean locked = false;

    public Die(int val){
        this.val=val;
    }
    public Die(){
        this.val = rand.nextInt(6) + 1;
    }

    public void roll(){
        this.val = rand.nextInt(6) + 1;
    }

    public void setLock(boolean locked){ this.locked = locked; }
    public boolean isLocked(){ return locked; }
    public int getVal(){ return val; }
}
