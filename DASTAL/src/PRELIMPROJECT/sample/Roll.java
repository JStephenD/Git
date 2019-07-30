package PRELIMPROJECT.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Roll implements Rollable {
    private Die[] dices = new Die[5];
    private ArrayList<Integer> arrvals = new ArrayList<>();
    private int mode = 0;
    private boolean dummy = false;

    public Roll(int d1, int d2, int d3, int d4, int d5){
        dices[0] = new Die(d1);
        dices[1] = new Die(d2);
        dices[2] = new Die(d3);
        dices[3] = new Die(d4);
        dices[4] = new Die(d5);
        updateVals();
    }
    public Roll(){
        for (int i = 0; i < 5; i++){
            dices[i] = new Die();
        }
        updateVals();
    }
    public Roll(boolean dummy){
        this.dummy = dummy;
    }

    public void roll(){
        for (Die die : dices){
            if (!die.isLocked()){
                die.roll();
            }
        }
        updateVals();
    }
    private void updateVals(){
        arrvals.clear();
        for (Die die : dices){
            arrvals.add(die.getVal());
        }
        Collections.sort(arrvals);
    }

    // ESSENTIALS
    public void setLock(int diedex, boolean locked){ dices[diedex].setLock(locked);}
    public void setMode(int mode){ this.mode = mode; }
    public int getVal(){
        switch (mode){
            case 0: return getSumOf(1);
            case 1: return getSumOf(2);
            case 2: return getSumOf(3);
            case 3: return getSumOf(4);
            case 4: return getSumOf(5);
            case 5: return getSumOf(6);
            case 6: return getThreeOfAKind();
            case 7: return getFourOfAKind();
            case 8: return getFullHouse();
            case 9: return getShortStraight();
            case 10: return getLongStraight();
            case 11: return getYahtzee();
            case 12: return getChance();
        }
        return 0;
    }

    // METHODS
    private int getFrequency(int val){ return Collections.frequency(arrvals, val); }
    private int getSum(){
        int sum = 0;
        for (int i : arrvals){
            sum += i;
        }
        return sum;
    }
    public boolean isDummy(){ return dummy; }
    public boolean isYahtzee(){ return getFrequency(arrvals.get(0)) == 5;}

    // SCORE SHEET METHODS
    public int getSumOf(int val){ return getFrequency(val) * val; }
    public int getChance(){ return getSum(); }
    public int getFullHouse(){
        if (getFrequency(arrvals.get(1)) == 3 && getFrequency(arrvals.get(3)) == 2){
            return 40;
        }
        else if ((getFrequency(arrvals.get(1)) == 2 && getFrequency(arrvals.get(3)) == 3)){
            return 40;
        }
        return 0;
    }
    public int getLongStraight(){
        int prev = arrvals.get(0);
        for (int i = 1; i < arrvals.size(); i++){
            if (!((prev + 1) == arrvals.get(i))){
                return 0;
            }
            prev = arrvals.get(i);
        }
        return 35;
    }
    public int getShortStraight(){
        HashSet<Integer> set = new HashSet<>(arrvals);
        boolean left = false, right = false;
        int curr=0, prev=0, offset;

        if (set.size() == 5){
            offset = 0;
            for (int n : set){
                if (offset > 0){  // offset to right 1 read 2nd dex >> [0 1 2 3 4 ]
                    if (prev == 0){
                        prev = n;
                    }
                    else{
                        curr = n;
                        if (!(prev + 1 == curr)){
                            right = false;
                            break;
                        }
                        prev = n;
                    }
                }
                else{
                    offset++;
                }
                right = true;
            }
        }
        prev = 0;
        offset = 4;
        if (set.size() >= 4){
            for (int k : set){
                if (offset != 0) offset--;
                else break;
                if (prev == 0){
                    prev = k;
                }
                else{
                    curr = k;
                    if (!(prev + 1 == curr)){
                        left = false;
                        break;
                    }
                    prev = k;
                }
                left = true;
            }
        }
        return  (left || right) ? 25 : 0;
    }
    public int getYahtzee(){ return (getFrequency(arrvals.get(0)) == 5) ? 50 : 0; }
    public int getFourOfAKind(){ return (getFrequency(arrvals.get(2)) >= 4) ? getSum() : 0; }
    public int getThreeOfAKind(){ return (getFrequency(arrvals.get(2)) >= 3) ? getSum() : 0; }

    // GETTERS
    public Die[] getDices(){ return dices;}
    public ArrayList<Integer> getArrvals(){ return arrvals; }
}
