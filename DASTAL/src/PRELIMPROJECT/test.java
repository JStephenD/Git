package PRELIMPROJECT;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ScoreSheet ss = new ScoreSheet();
        Roll curr = new Roll(0,0,0,0,0),
            take = new Roll(0,0,0,0,0);
        int index;
        String ul;

        for (int rolls = 1; rolls <= 13; rolls++){
            System.out.println("r to roll");
            if (in.nextLine().equals("r")){
                curr = new Roll();
                System.out.println(curr.getArrvals());
            }
            System.out.println("u for upper l for lower");
            ul = in.nextLine();
            if (ul.equals("u")){
                for (Roll r: ss.getUpperList()){
                    System.out.println(r.getArrvals());
                }
                System.out.println("enter index [1-6]");
                index = Integer.parseInt(in.nextLine()) - 1;
                ss.insertRoll_Upper(index, curr);
            }
            else if (ul.equals("l")){
                for (Roll r: ss.getLowerList()){
                    System.out.println(r.getArrvals());
                }
                System.out.println("enter index [1-7]");
                index = Integer.parseInt(in.nextLine()) - 1;
                ss.insertRoll_Lower(index, curr);
            }
        }

        for (Roll r: ss.getUpperList()){
            System.out.println(r.getArrvals()+":"+r.getVal());
        }
        System.out.println(ss.getUppertotal());
        for (Roll r: ss.getLowerList()){
            System.out.println(r.getArrvals()+":"+r.getVal());
        }
        System.out.println(ss.getLowertotal());
    }
}
