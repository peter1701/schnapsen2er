package com.example.peter.cardanimationtest.gamelogic;

import android.widget.LinearLayout;

import com.example.peter.cardanimationtest.gamelogic.Cards;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

/**
 * Created by Peter on 13.12.2017.
 */

public class Player {
    int points=0;
    List<Cards> hand;
    List<Cards> stock;
    LinearLayout linearLayout;
    Cards playedCard;
    int specialPoints=0;

    boolean npc;
    public Player() {
        hand=new ArrayList<>();
        stock=new ArrayList<>();
    }

    public void setSpecialPoints(int specialPoints) {
        this.specialPoints = specialPoints;
    }

    public int getSpecialPoints() {
        return specialPoints;
    }

    public int getPoints() {
        int ret=0;
        for(Cards c:stock){
            ret+=c.getPoints();
        }
        if(ret==0) {
            return ret;
        }else{
            return ret+specialPoints;
        }
    }

    public Cards getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(Cards playedCard) {
        this.playedCard = playedCard;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Cards> getHand() {
        return hand;
    }

    public void setHand(List<Cards> hand) {
        this.hand = hand;
    }

    public List<Cards> getStock() {
        return stock;
    }

    public void setStock(List<Cards> stock) {
        this.stock = stock;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public boolean isNpc() {
        return npc;
    }

    public void setNpc(boolean npc) {
        this.npc = npc;
    }
}
