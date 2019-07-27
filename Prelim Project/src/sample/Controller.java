package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;

public class Controller {
    @FXML
    private Button rollButton,
        die1, die2, die3, die4, die5,
        btn1, btn2, btn3, btn4, btn5,
        btn6, btn7, btn8, btn9, btn10,
        btn11, btn12, btn13;
    @FXML
    private Label score, roundlbl, subTotal, success_score;
    @FXML
    private ImageView lock1, lock2, lock3, lock4, lock5,
            rollleft1, rollleft2, rollleft3;
    @FXML
    private Pane gamePane, importPane, success;
    @FXML
    private TextField filename;
    @FXML
    private ProgressBar prog1, prog2, prog3, prog4, prog5,
        prog6, prog7, prog8, prog9, prog10,
        prog11, prog12, prog13;


    ScoreSheet scoreSheet = new ScoreSheet();
    private boolean rollOngoing = false, importMode = false;
    private Roll roll = new Roll();
    private int rollTimes = 0, totalscore,
            yahtextra,
            round=1,
            linenum = 0; // import mode
    private boolean[] available = new boolean[13];
    private String[] lines = new String[13];

    // EVENTS
    public void rollButtonClick(){
        if (importMode){
            if (!rollOngoing && linenum < 13) {
                rollOngoing = true;
                String line = lines[linenum];

                String strnum[];
                int nums[] = new int[5];

                strnum = line.split(" ");
                for (int i = 0; i < 5; i++) {
                    nums[i] = Integer.parseInt(strnum[i]);
                }

                roll = new Roll(nums[0], nums[1], nums[2], nums[3], nums[4]);

                linenum++;
                update();
            }
        }
        else {
            rollTimes++;
            if (rollTimes <= 3) {
                if (!rollOngoing) {
                    roll = new Roll();
                    rollOngoing = true;
                    rollleft1.setVisible(false);
                } else {
                    roll.roll();
                    if (rollTimes == 2) rollleft2.setVisible(false);
                    else rollleft3.setVisible(false);
                }
                update();
            }
        }
        System.out.println(roll.getArrvals());
    }
    public void importBtn(){
        importPane.setVisible(true);
    }
    public void backBtn(){
        importPane.setVisible(false);
    }
    public void okBtn(){
        importPane.setVisible(false);
        importText();
    }
    public void newGame(){
        importMode = false;
        scoreSheet = new ScoreSheet();
        totalscore = 0;
        round = 1;
        available = new boolean[13];
        rollOngoing = false;
        rollButton.getStyleClass().removeAll("rollpic_click", "rollpic_disable");
        rollButton.getStyleClass().add("rollpic");

        score.setText("0");
        subTotal.setText("0");
        roundlbl.setText("1");

        success.setVisible(false);
        gamePane.setVisible(true);

        lines = new String[13];
        yahtextra = 0;
        linenum = 0;

        for (int i = 0; i <= 12; i++){
            getButton(i).setText("");
            getButton(i).setFont(Font.font("Gill Sans Ultra Bold", 12));
            getButton(i).setTextFill(Paint.valueOf("Red"));
        }
    }

    public void die1Click(){ lock_unlock(0);}
    public void die2Click(){ lock_unlock(1);}
    public void die3Click(){ lock_unlock(2);}
    public void die4Click(){ lock_unlock(3);}
    public void die5Click(){ lock_unlock(4);}
    public void score1Click() { scoreClick(0);}
    public void score2Click() { scoreClick(1);}
    public void score3Click() { scoreClick(2);}
    public void score4Click() { scoreClick(3);}
    public void score5Click() { scoreClick(4);}
    public void score6Click() { scoreClick(5);}
    public void score7Click() { scoreClick(6);}
    public void score8Click() { scoreClick(7);}
    public void score9Click() { scoreClick(8);}
    public void score10Click() { scoreClick(9);}
    public void score11Click() { scoreClick(10);}
    public void score12Click() { scoreClick(11);}
    public void score13Click() { scoreClick(12);}

    // METHODS
        // UPDATES
    private void update(){
        updateRollButton();
        updateDices();
        scoreHints();
    }
    private void updateRollButton(){
        if (importMode){
            rollButton.getStyleClass().removeAll("rollpic", "rollpic_click", "rollpic_disable");
            rollButton.getStyleClass().add("rollpic_disable");
            rollleft1.setVisible(false);
            rollleft2.setVisible(false);
            rollleft3.setVisible(false);
        }
        else {
            if (rollTimes == 3) {
                rollButton.getStyleClass().removeAll("rollpic", "rollpic_click", "rollpic_disable");
                rollButton.getStyleClass().add("rollpic_disable");
            } else {
                rollButton.getStyleClass().removeAll("rollpic", "rollpic_click", "rollpic_disable");
                rollButton.getStyleClass().add("rollpic_click");
            }
        }
    }
    private void updateDices(){
        int dienum = 0;
        for (Die die : roll.getDices()){
            dienum++;
            switch (dienum){
                case 1:
                    die1.getStyleClass().removeAll("d1", "d2", "d3", "d4", "d5", "d6");
                    die1.getStyleClass().add("d"+die.getVal());
                    break;
                case 2:
                    die2.getStyleClass().removeAll("d1", "d2", "d3", "d4", "d5", "d6");
                    die2.getStyleClass().add("d"+die.getVal());
                    break;
                case 3:
                    die3.getStyleClass().removeAll("d1", "d2", "d3", "d4", "d5", "d6");
                    die3.getStyleClass().add("d"+die.getVal());
                    break;
                case 4:
                    die4.getStyleClass().removeAll("d1", "d2", "d3", "d4", "d5", "d6");
                    die4.getStyleClass().add("d"+die.getVal());
                    break;
                case 5:
                    die5.getStyleClass().removeAll("d1", "d2", "d3", "d4", "d5", "d6");
                    die5.getStyleClass().add("d"+die.getVal());
                    break;
            }
        }
    }
    private void updateRound(){
        rollButton.setDisable(false);
        rollTimes = 0;
        totalscore = scoreSheet.getTotal() + yahtextra;
        clearScoreHints();

        subTotal.setText(scoreSheet.getUppersubtotal()+"");
        score.setText(totalscore+"");

        if(round != 13) {
            round++;
            roundlbl.setText(round+"");
        }
        else if (round+1 == 14){
            finish();
        }

        lock1.setVisible(false);
        lock2.setVisible(false);
        lock3.setVisible(false);
        lock4.setVisible(false);
        lock5.setVisible(false);

        rollButton.getStyleClass().removeAll("rollpic_click", "rollpic_disable");
        rollButton.getStyleClass().add("rollpic");
        rollleft1.setVisible(true);
        rollleft2.setVisible(true);
        rollleft3.setVisible(true);
    }


        // EVENT METHODS
    private void scoreClick(int scoredex){
        if (!available[scoredex]) {
            if (rollOngoing) {
                scoreSheet.insertRoll(scoredex, roll);
                rollOngoing = false;
                getButton(scoredex).setFont(Font.font("Gill Sans Ultra Bold", 16));
                getButton(scoredex).setTextFill(Paint.valueOf("Black"));
                updateRound();
            }
        }
        else if (scoredex == 11){
            if (roll.isYahtzee()){
                yahtextra+=50;
                getButton(11).setText((50+yahtextra)+"");
                getButton(11).setFont(Font.font("Gill Sans Ultra Bold", 13));
                rollOngoing = false;
                updateRound();
            }
        }
        available[scoredex] = true;
    }

    private void scoreHints(){
        int btnnum = 0;
        for (Roll roll : scoreSheet.getUpperList()){
            if(roll.isDummy()){
                this.roll.setMode(btnnum);
                getButton(btnnum).setText(this.roll.getVal()+"");
            }
            btnnum++;
        }
        for (Roll roll : scoreSheet.getLowerList()){
            if(roll.isDummy()){
                this.roll.setMode(btnnum);
                getButton(btnnum).setText(this.roll.getVal()+"");
            }
            if(btnnum == 11 && this.roll.isYahtzee()){
                getButton(11).setText((50+yahtextra)+"");
                getButton(11).setFont(Font.font("Gill Sans Ultra Bold", 13));
                getButton(11).setTextFill(Paint.valueOf("Red"));
            }
            btnnum++;
        }
    }
    private void clearScoreHints(){
        int btnnum = 0;
        for (Roll roll : scoreSheet.getUpperList()){
            if(roll.isDummy()){
                getButton(btnnum).setText("");
            }
            btnnum++;
        }
        for (Roll roll : scoreSheet.getLowerList()){
            if(roll.isDummy()){
                getButton(btnnum).setText("");
            }
            if(btnnum == 11){
                if (yahtextra > 0) {
                    getButton(11).setText((50 + yahtextra) + "");
                    getButton(11).setFont(Font.font("Gill Sans Ultra Bold", 13));
                    getButton(11).setTextFill(Paint.valueOf("Black"));
                }
            }
            btnnum++;
        }
    }
    private void lock_unlock(int diedex){
        if (!importMode){
            Die die = roll.getDices()[diedex];
            die.setLock(!die.isLocked());
            if (rollOngoing) {
                ImageView lock = null;
                switch (diedex) {
                    case 0: lock = lock1; break;
                    case 1: lock = lock2; break;
                    case 2: lock = lock3; break;
                    case 3: lock = lock4; break;
                    case 4: lock = lock5; break;
                }
                lock.setVisible(!lock.isVisible());
            }
        }
    }
    private void importText(){
        importMode = true;
        scoreSheet = new ScoreSheet();
        totalscore = 0;
        round = 1;
        available = new boolean[13];
        rollOngoing = false;
        rollButton.getStyleClass().removeAll("rollpic_click", "rollpic_disable");
        rollButton.getStyleClass().add("rollpic");

        score.setText("0");
        subTotal.setText("0");
        roundlbl.setText("1");

        for (int i = 0; i <= 12; i++){
            getButton(i).setText("");
            getButton(i).setFont(Font.font("Gill Sans Ultra Bold", 12));
            getButton(i).setTextFill(Paint.valueOf("Red"));
        }

        String filename = this.filename.getText();
        String desktop = "C:\\Users\\John Stephen Degillo\\Desktop\\";

        try {
            FileReader r = new FileReader(desktop+filename);
            BufferedReader bufferedReader = new BufferedReader(r);
            String line;

            int linecnt = 0;
            while((line = bufferedReader.readLine()) != null) {
                lines[linecnt] = line;
                linecnt++;
            }

            bufferedReader.close();
        }
        catch (Exception e){ }
    }
    private Button getButton(int btnnum){
        switch (btnnum){
            case 0: return btn1;
            case 1: return btn2;
            case 2: return btn3;
            case 3: return btn4;
            case 4: return btn5;
            case 5: return btn6;
            case 6: return btn7;
            case 7: return btn8;
            case 8: return btn9;
            case 9: return btn10;
            case 10: return btn11;
            case 11: return btn12;
            case 12: return btn13;
        }
        return null;
    }

    private void finish(){
        gamePane.setVisible(false);
        success.setVisible(true);
        success_score.setText(totalscore+"");
        int prognum = 1;
        for (Roll roll : scoreSheet.getUpperList()){
            getProgressBar(prognum).setProgress(roll.getVal() / getMaxofMode(prognum));
            prognum++;
        }
        for (Roll roll : scoreSheet.getLowerList()){
            getProgressBar(prognum).setProgress(roll.getVal() / getMaxofMode(prognum));
            prognum++;
        }
    }
    private ProgressBar getProgressBar(int prognum){
        switch (prognum){
            case 1: return prog1;
            case 2: return prog2;
            case 3: return prog3;
            case 4: return prog4;
            case 5: return prog5;
            case 6: return prog6;
            case 7: return prog7;
            case 8: return prog8;
            case 9: return prog9;
            case 10: return prog10;
            case 11: return prog11;
            case 12: return prog12;
            case 13: return prog13;
        }
        return null;
    }
    private double getMaxofMode(int num){
        switch (num){
            case 1: return 5;   // 1s
            case 2: return 10;  // 2s
            case 3: return 15;  // 3s
            case 4: return 20;  // 4s
            case 5: return 25;  // 5s
            case 6: return 30;  // 6s
            case 7: return 30;  // 3k
            case 8: return 30;  // 4k
            case 9: return 40;  // fh
            case 10: return 25; // ss
            case 11: return 35; // ls
            case 12: return 50; // y
            case 13: return 30; // c
        }
        return 0;
    }
}
