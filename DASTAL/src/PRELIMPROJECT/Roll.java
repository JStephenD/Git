package PRELIMPROJECT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Roll {
    private Die die1,die2,die3,die4,die5;
    private ArrayList<Integer> arrvals = new ArrayList<>();
    private int mode = 0;

    public Roll(Die die1, Die die2, Die die3, Die die4, Die die5){
        this.die1 = die1;
        this.die2 = die2;
        this.die3 = die3;
        this.die4 = die4;
        this.die5 = die5;
        setUp();
    }
    public Roll(int val1, int val2, int val3, int val4, int val5){
        this(new Die(val1), new Die(val2), new Die(val3), new Die(val4), new Die(val5));
    }
    public Roll(){
        this(new Die(), new Die(), new Die(), new Die(), new Die());
    }

    private void setUp(){
        arrvals.add(this.die1.getVal());
        arrvals.add(this.die2.getVal());
        arrvals.add(this.die3.getVal());
        arrvals.add(this.die4.getVal());
        arrvals.add(this.die5.getVal());
        Collections.sort(arrvals);
    }

    public void setMode(int mode){
        this.mode = mode;
    }
    public int getVal(){
//        1 - > sum1
//        2 - > sum2
//        3 - > sum3
//        4 - > sum5
//        5 - > sum5
//        6 - > sum6
//        7 - > 3k
//        8 - > 4k
//        9 - > FH
//        10 - > SS
//        11 - > LS
//        12 - > YZ
//        13 - > CH
        switch (mode){
            case 0:
                return getSumOf(1);
            case 1:
                return getSumOf(2);
            case 2:
                return getSumOf(3);
            case 3:
                return getSumOf(4);
            case 4:
                return getSumOf(5);
            case 5:
                return getSumOf(6);
            case 6:
                return getThreeOfAKind();
            case 7:
                return getFourOfAKind();
            case 8:
                return getFullHouse();
            case 9:
                return getShortStraight();
            case 10:
                return getLongStraight();
            case 11:
                return getYahtzee();
            case 12:
                return getChance();
        }
        return 0;
    }

    private int getFrequency(int val){ return Collections.frequency(arrvals, val); }
    private int getSum(){
        int sum = 0;
        for (int i : arrvals){
            sum += i;
        }
        return sum;
    }

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
                left = true;
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

    public ArrayList<Integer> getArrvals(){ return arrvals; }
}
