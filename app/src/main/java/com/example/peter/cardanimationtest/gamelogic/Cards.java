package com.example.peter.cardanimationtest.gamelogic;

import com.example.peter.cardanimationtest.R;

/**
 * Created by Peter on 12.12.2017.
 */

public enum Cards {


    HERZ_ASS(R.mipmap.herz_ass,"Herz","Ass"),
    HERZ_JACK(R.mipmap.herz_jack,"Herz","Jack"),
    HERZ_KING(R.mipmap.herz_king,"Herz","King"),
    HERZ_QUEEN(R.mipmap.herz_queen,"Herz","Queen"),
    HERZ_TEN(R.mipmap.herz_ten,"Herz","Ten"),

    PIG_ASS(R.mipmap.pig_ass,"Pig","Ass"),
    PIG_JACK(R.mipmap.pig_jack,"Pig","Jack"),
    PIG_KING(R.mipmap.pig_king,"Pig","King"),
    PIG_QUEEN(R.mipmap.pig_queen,"Pig","Queen"),
    PIG_TEN(R.mipmap.pig_ten,"Pig","Ten"),

    KREUZ_ASS(R.mipmap.kreuz_ass,"Kreuz","Ass"),
    KREUZ_JACK(R.mipmap.kreuz_jack,"Kreuz","Jack"),
    KREUZ_KING(R.mipmap.kreuz_king,"Kreuz","King"),
    KREUZ_QUEEN(R.mipmap.kreuz_queen,"Kreuz","Queen"),
    KREUZ_TEN(R.mipmap.kreuz_ten,"Kreuz","Ten"),

    SHELL_ASS(R.mipmap.shell_ass,"Shell","Ass"),
    SHELL_JACK(R.mipmap.shell_jack,"Shell","Jack"),
    SHELL_KING(R.mipmap.shell_king,"Shell","King"),
    SHELL_QUEEN(R.mipmap.shell_queen,"Shell","Queen"),
    SHELL_TEN(R.mipmap.shell_ten,"Shell","Ten");



    private final int mipmap;
    private final String farbe;
    private final String card;


    Cards(int mipmap, String farbe, String card) {
        this.mipmap=mipmap;
        this.farbe=farbe;
        this.card=card;
    }

    public int getMipmap(){
        return mipmap;
    }

    public String getFarbe() {
        return farbe;
    }

    public String getCard() {
        return card;
    }

    public int getPoints(){

        switch(this.getCard()){
            case "Ass":
                return 11;
            case "Ten":
                return 10;
            case "King":
                return 4;
            case "Queen":
                return 3;
            case "Jack":
                return 2;
            default:
                return 0;
        }
    }
    @Override
    public String toString(){
        return this.card+" "+this.getFarbe();
    }
}
