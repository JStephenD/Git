package PRELIM;

import java.util.*;

public class PA01 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<String> decks = new ArrayList<>();

        System.out.print("Input: ");
        StringTokenizer st = new StringTokenizer(input.nextLine(), " ");
        while (st.hasMoreTokens()){
            decks.add(st.nextToken());
        }
        pokerHand(decks);

        System.out.print("Do you want to try again? [y/n]: ");
        if (input.nextLine().toLowerCase().equals("y"))
            main(null);
        else
            System.out.println("Bye");
    }

    static void pokerHand(ArrayList<String> decks){
        ArrayList<String> lh = new ArrayList<>(), rh = new ArrayList<>();
        String lhv, rhv;

        int cardCount = 0;
        for (String card : decks){
            if (cardCount < 5)
                lh.add(card);
            else
                rh.add(card);
            cardCount++;
        }

        lhv = evalHand(lh);
        rhv = evalHand(rh);
        System.out.println("Black: " + lhv);
        System.out.println("White: " + rhv);
        System.out.println(compareHand(lhv, rhv));
    }

    static String evalHand(ArrayList<String> d){
        ArrayList<Integer> v = new ArrayList<>();
        ArrayList<String> s = new ArrayList<>();
        ArrayList<Integer> p = new ArrayList<>();
        int max_freq = 0, freq,
                pairs = 0;
        boolean has_pair = false,
                has_trio = false,
                has_twopair = false;

        for (String card : d){
            // get values
            v.add(actual_val(card.charAt(0) + ""));
            // get suits
            s.add(card.charAt(1) + "");
        }
        Collections.sort(v);

        // ---STRAIGHT, STRAIGHT FLUSH, FLUSH---
        if (v.get(0).equals(v.get(1)-1) && v.get(1).equals(v.get(2)-1) && v.get(2).equals(v.get(3)-1) && v.get(3).equals(v.get(4)-1)) {
            if (s.get(0).equals(s.get(1)) && s.get(1).equals(s.get(2)) && s.get(2).equals(s.get(3)) && s.get(3).equals(s.get(4))){
                //      deck              high card
                return "StraightFlush-" + v.get(4);
            }
            //      deck         high card
            return "Straight-" + v.get(4);
        }
        else if (s.get(0).equals(s.get(1)) && s.get(1).equals(s.get(2)) && s.get(2).equals(s.get(3)) && s.get(3).equals(s.get(4))){
            //      deck      highestestest    highestest       highest          higher           high card
            return "Flush-" + v.get(4) + "-" + v.get(3) + "-" + v.get(2) + "-" + v.get(1) + "-" + v.get(0);
        }

        // others
        for (Integer i : v){
            freq = Collections.frequency(v, i);
            if (freq > max_freq)
                max_freq = freq;
            if (freq == 2) {
                has_pair = true;
                p.add(i);
                pairs++;
                if (pairs >= 3)
                    has_twopair = true;
            }
            else if (freq == 1)
                p.add(i+13);
            else if (freq == 3)
                has_trio = true;
            else if (freq == 4)
                //      deck     value of quadro
                return "Quad-" + v.get(2); // ---QUADRO---
        }
        Collections.sort(p);

        if (has_pair){ // ---PAIR AND 2 PAIR---
            if (has_trio)
                //      deck         value of trio
                return "FullHouse-" + v.get(2); // ---FULL HOUSE---
            if (has_twopair){
                //      deck        pair 1 high      pair 2 low value  high card
                return "TwoPair-" + p.get(2) + "-" + p.get(0) + "-" + (p.get(4)-13);
            }
            else
                //      deck     pair value       highest               higher                high card
                return "Pair-" + p.get(0) + "-" + (p.get(4)-13) + "-" + (p.get(3)-13) + "-" + (p.get(2)-13);
        }
        else if (has_trio)
            //      deck     value of trio
            return "Trio-" + v.get(2); // ---TRIO---

        // ---HIGH CARD---
        //      deck     highestesttest   highestest       highest          higher         high card
        return "High-" + v.get(4) + "-" + v.get(3) + "-" + v.get(2) + "-" + v.get(1) + "-" + v.get(0);
    }
    static String compareHand(String l, String r){
        StringTokenizer s1 = new StringTokenizer(l, "-");
        StringTokenizer s2 = new StringTokenizer(r, "-");

        String v1, v2;
        int h1, h2, max = 6;

        for (int x = 0; x <= max; x++){
            if (!s1.hasMoreTokens() || !s2.hasMoreTokens()) break;
            v1 = s1.nextToken();
            v2 = s2.nextToken();

            // deck tier
            h1 = deck_tier(v1);
            h2 = deck_tier(v2);
            if (x == 0) { // 0 -> deck tier zone
                if (h1 > h2) return "Black Wins";
                else if (h1 < h2) return "White Wins"; //
            }
            else{ // 1 -> 6 value of cards zone
                h1 = Integer.parseInt(v1);
                h2 = Integer.parseInt(v2);
                if (h1 > h2) return "Black Wins";
                else if (h1 < h2) return "White Wins"; //
            }
        }
        return "Tie";
    }
    static int deck_tier(String s){
        if (s.equals("StraightFlush")) return 9;
        else if (s.equals("Quad")) return 8;
        else if (s.equals("FullHouse")) return 7;
        else if (s.equals("Flush")) return 6;
        else if (s.equals("Straight")) return 5;
        else if (s.equals("Trio")) return 4;
        else if (s.equals("TwoPair")) return 3;
        else if (s.equals("Pair")) return 2;
        else if (s.equals("High")) return 1;

        return 0;
    }
    static int actual_val(String s){
        try{
            return Integer.parseInt(s) - 1;
        }
        catch (Exception e){}
        if (s.equals("T")) return 9;
        else if (s.equals("J")) return 10;
        else if (s.equals("Q")) return 11;
        else if (s.equals("K")) return 12;
        else if (s.equals("A")) return 13;
        return 0;
    }
}
