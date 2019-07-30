package PRELIMPROJECT.sample;

import java.util.ArrayList;

public class ScoreSheet {
    private ArrayList<Roll> upper, lower;
    private int uppertotal=0, uppersubtotal=0,
            lowertotal=0, total=0;
    private Roll dummyRoll = new Roll(true);

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
            if (!r.isDummy())
                uppersubtotal += r.getVal();
        }
        uppertotal = uppersubtotal >= 63 ? uppersubtotal + 35 : uppersubtotal;

        lowertotal = 0;
        for (Roll r : lower){
            if (!r.isDummy())
                lowertotal += r.getVal();
        }

        total = uppertotal + lowertotal;
    }

    // SCORE SHEET FUNCTIONS
    public void insertRoll(int index, Roll roll){
        if (index < 6){
            insertRoll_Upper(index, roll);
        }
        else{
            insertRoll_Lower(index, roll);
        }
        updateTotal();
    }
    private void insertRoll_Upper(int index, Roll roll){
        // index should be 0 through 5
        roll.setMode(index);
        upper.set(index, roll);
    }
    private void insertRoll_Lower(int index, Roll roll){
        // index should be 6 through 12
        roll.setMode(index);
        lower.set(index-6, roll);
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

    // GETTERS
    public int getUppersubtotal() { return uppersubtotal;}
    private int getUppertotal(){ return uppertotal; }
    private int getLowertotal(){ return lowertotal; }
    public int getTotal(){ return total;}
    public ArrayList<Roll> getUpperList(){ return upper; }
    public ArrayList<Roll> getLowerList(){ return lower; }
}
