package PRELIMPROJECT;

import java.util.ArrayList;

public class ScoreSheet {
    private ArrayList<Roll> upper, lower;
    private int uppertotal=0, uppersubtotal=0,
            lowertotal=0, total=0;
    private Roll dummyRoll = new Roll(0,0,0,0,0);

    public ScoreSheet(){
        this.upper = new ArrayList<>();
        this.lower = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            if (i < 6)
                upper.add(dummyRoll);
            lower.add(dummyRoll);
        }
    }

    private void updateTotal(){
        uppersubtotal = 0;
        uppertotal = 0;
        for (Roll r : upper){
            uppersubtotal += r.getVal();
        }
        uppertotal = uppersubtotal >= 60 ? uppersubtotal + 25 : uppersubtotal;

        lowertotal = 0;
        for (Roll r : lower){
            lowertotal += r.getVal();
        }

        total = uppertotal + lowertotal;
    }

    public ArrayList<Roll> getUpperList(){ return upper; }
    public ArrayList<Roll> getLowerList(){ return lower; }

    public void insertRoll_Upper(int index, Roll roll){
        roll.setMode(index);
        upper.set(index, roll);
        updateTotal();
    }
    public void insertRoll_Lower(int index, Roll roll){
        roll.setMode(index + 6);
        lower.set(index, roll);
        updateTotal();
    }
    public Roll takeFrom_Upper(int index){
        Roll roll = upper.get(index);
        upper.set(index, dummyRoll);
        updateTotal();
        return roll;
    }
    public Roll takeFrom_Lower(int index){
        Roll roll = lower.get(index);
        lower.set(index, dummyRoll);
        updateTotal();
        return roll;
    }
    public int getUppertotal(){ return this.uppertotal; }
    public int getLowertotal(){ return lowertotal; }
}
