package com.example.peter.cardanimationtest.service;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.peter.cardanimationtest.gamelogic.Cards;
import com.example.peter.cardanimationtest.gamelogic.Player;
import com.example.peter.cardanimationtest.gamelogic.Schnapsen2er;
import com.example.peter.cardanimationtest.gui.Schnapsen2erFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Peter on 06.02.2018.
 */

public class Schnapsen2erService implements GameService {

    private static final Schnapsen2erService instance = new Schnapsen2erService();

    private Schnapsen2er schnapsen2er;
    private Schnapsen2erFragment schnapsen2erFragment;

    private Schnapsen2erService() {

    }

    public Schnapsen2er getSchnapsen2er() {
        return schnapsen2er;
    }

    public void setSchnapsen2er(Schnapsen2er schnapsen2er) {
        this.schnapsen2er = schnapsen2er;
    }

    public Schnapsen2erFragment getSchnapsen2erFragment() {
        return schnapsen2erFragment;
    }

    public void setSchnapsen2erFragment(Schnapsen2erFragment schnapsen2erFragment) {
        this.schnapsen2erFragment = schnapsen2erFragment;
    }

    public static Schnapsen2erService getInstance() {
        return instance;
    }


    public void setUp() {
        schnapsen2er=new Schnapsen2er();
        schnapsen2er.resetDeck();
        schnapsen2erFragment.clearAllViews();
        Cards cards;

        prepareHand(schnapsen2er.getPlayer());
        prepareHand(schnapsen2er.getCpu());
        System.out.println(schnapsen2er.getPlayer().getHand().toString());
        schnapsen2er.setTrump();
        schnapsen2erFragment.setTrump(schnapsen2er.getTrump(), getTrumpListener());
        ArrayList<String> zwanziger= has20er(schnapsen2er.getPlayer());

        schnapsen2erFragment.add20erButton(zwanziger);




    }
    private void prepareHand(Player p){
        for (int idx = 0; idx < 5; idx++) {
            addSortedCard(p);
        }


    }

    private void sortHand(Player player) {
        Collections.sort(player.getHand(), new Comparator<Cards>() {
            @Override
            public int compare(Cards o1, Cards o2) {
                if(o1.getFarbe()==o2.getFarbe()){
                    return o2.getPoints()-o1.getPoints();
                }
                return o1.getFarbe().compareTo(o2.getFarbe());


            }
        });

    }


    public View.OnClickListener getPlayerCardListener(Cards card) {
        schnapsen2erFragment.enableAllCards();
        return (view -> {
            ArrayList<String> zwanziger= has20er(schnapsen2er.getPlayer());

            schnapsen2erFragment.add20erButton(zwanziger);
            if (schnapsen2er.isPlayersTurn()) {
                if (schnapsen2er.getPlayer().getHand().size() > 0) {
                    if (schnapsen2er.isTrumpRemoved()) {
                        schnapsen2er.setFarbegeben(true);
                    }

                    playerPlayCard(card);
                    cpuPlayCard(card);


                    Player winningPlayer = playerWinning();
                    endRound(winningPlayer);
                    System.out.println(playerWinning().toString());


                }
            } else {
                if (schnapsen2er.getPlayer().getHand().size() > 0) {
                    if (schnapsen2er.isFarbegeben()) {
                        boolean hasCardThisColor=false;
                        for(Cards c:schnapsen2er.getPlayer().getHand()){
                            if(c.getFarbe()==schnapsen2er.getCpu().getPlayedCard().getFarbe()){
                                hasCardThisColor=true;
                            }
                        }if(hasCardThisColor){

                            if (schnapsen2er.getCpu().getPlayedCard().getFarbe().equals(card.getFarbe())) {
                                playerPlayCard(card);

                            } else {
                                return;
                            }
                        }else{
                            playerPlayCard(card);
                        }
                    } else {
                        playerPlayCard(card);

                    }

                    Player winningPlayer = playerWinning();

                    endRound(winningPlayer);
                    System.out.println(playerWinning().toString());
                }


            }
        });
    }

    private Cards getCPUCardToPlay(Cards playedCard) {

        Cards c = null;
        for (int idx = 0; idx < schnapsen2er.getCpu().getHand().size(); idx++) {
            Cards ca = schnapsen2er.getCpu().getHand().get(idx);
            if (ca != null) {

            } else {
                schnapsen2er.getCpu().getHand().remove(ca);
            }
        }
        if (playedCard != null) {
            if (schnapsen2er.isFarbegeben()) {
                for (Cards cards : schnapsen2er.getCpu().getHand()) {
                    System.out.println("Cards in hand:" + schnapsen2er.getCpu().getHand().size() + " " + schnapsen2er.getCpu().getHand().toString());
                    if (cards.getFarbe().equals(playedCard.getFarbe())) {
                        c = cards;
                    }
                }
            } else {
                c = schnapsen2er.getCpu().getHand().get(schnapsen2er.getCpu().getHand().size() - 1);
                System.out.println(c.toString());
            }
        } else {
            c = schnapsen2er.getCpu().getHand().get(schnapsen2er.getCpu().getHand().size() - 1);
            System.out.println(c.toString());
        }
        if (c == null) {
            c = schnapsen2er.getCpu().getHand().get(schnapsen2er.getCpu().getHand().size() - 1);
        }

        return c;

    }

    private Player playerWinning() {
        Player p;
        Player c;
        if (schnapsen2er.isPlayersTurn()) {
            c = schnapsen2er.getCpu();
            p = schnapsen2er.getPlayer();
        } else {
            p = schnapsen2er.getCpu();
            c = schnapsen2er.getPlayer();
        }
        Player ret = null;

        if (Objects.equals(p.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe()) && Objects.equals(c.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe())) {
            if (p.getPlayedCard().getPoints() > c.getPlayedCard().getPoints()) {
                return p;
            } else {
                return c;
            }
        }
        if (p.getPlayedCard().getFarbe().equals(schnapsen2er.getTrump().getFarbe()) && !c.getPlayedCard().getFarbe().equals(schnapsen2er.getTrump().getFarbe())) {
            return p;
        }
        if (!Objects.equals(p.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe()) && Objects.equals(c.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe())) {
            return c;
        }
        if (!Objects.equals(p.getPlayedCard().getFarbe(), c.getPlayedCard().getFarbe())) {
            return p;
        }
        if (p.getPlayedCard().getPoints() > c.getPlayedCard().getPoints()) {

            return p;
        }
        if (p.getPlayedCard().getPoints() < c.getPlayedCard().getPoints()) {
            return c;
        } else {
            return null;
        }

/*

        if (Objects.equals(p.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe()) && Objects.equals(c.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe())) {
            if (p.getPlayedCard().getPoints() > c.getPlayedCard().getPoints()) {
                ret = p;
            } else {
                ret = c;
            }
        } else if (p.getPlayedCard().getFarbe().equals(schnapsen2er.getTrump().getFarbe()) && !c.getPlayedCard().getFarbe().equals(schnapsen2er.getTrump().getFarbe())) {
            ret = p;
        } else if (!Objects.equals(p.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe()) && Objects.equals(c.getPlayedCard().getFarbe(), schnapsen2er.getTrump().getFarbe())) {
            ret = c;
        } else if (!Objects.equals(p.getPlayedCard().getFarbe(), c.getPlayedCard().getFarbe())) {
            ret = p;
        } else if (p.getPlayedCard().getPoints() > c.getPlayedCard().getPoints()) {

            ret = p;
        } else if (p.getPlayedCard().getPoints() < c.getPlayedCard().getPoints()) {
            ret = c;
        }*/


    }

    public View.OnClickListener getTrumpListener() {
        return (view -> {
            Cards card = null;
            for (Cards c : schnapsen2er.getPlayer().getHand()) {
                if (c.getFarbe() == schnapsen2er.getTrump().getFarbe() && c.getCard() == "Jack") {
                    card = c;
                }
            }
            if (card != null) {
                schnapsen2erFragment.changeTrump(card, schnapsen2er.getTrump(), getPlayerCardListener(schnapsen2er.getTrump()));

                schnapsen2er.changeTrump(card);
            }
        });
    }

    public void playersDraw() {

        for(Player p:schnapsen2er.getPlayers()){

            addSortedCard(p);

            System.out.println(p.getHand().toString());
        }

        if (schnapsen2er.isTrumpRemoved()) {
            schnapsen2erFragment.removeTrump();

        }
        ArrayList<String> zwanziger= has20er(schnapsen2er.getPlayer());

        schnapsen2erFragment.add20erButton(zwanziger);

    }
    private void addSortedCard(Player p){
        addSortedCard(p,null);
    }
    private void addSortedCard(Player p,Cards cards){
        Cards card=null;
        if(cards == null){
            card=schnapsen2er.playerDrawCard(p);
        }
        if (card == null) {
            return;
        }
        sortHand(p);

        schnapsen2erFragment.clearPlayerCardLayout(p);

        for(Cards c:p.getHand()){

            schnapsen2erFragment.addCardToHand(c,p,getPlayerCardListener(c));

        }
    }
    private ArrayList<String> has20er(Player p){
        ArrayList<String> ret=new ArrayList<>();
        if(p.getHand().contains(Cards.HERZ_KING)&&p.getHand().contains(Cards.HERZ_QUEEN)){
            ret.add("Herz");
            System.out.println(p.getHand().toString());
        }
        if(p.getHand().contains(Cards.KREUZ_KING)&&p.getHand().contains(Cards.KREUZ_QUEEN)){
            ret.add("Kreuz");
            System.out.println(p.getHand().toString());
        }
        if(p.getHand().contains(Cards.PIG_KING)&&p.getHand().contains(Cards.PIG_QUEEN)){
            ret.add("Pig");
            System.out.println(p.getHand().toString());
        }
        if(p.getHand().contains(Cards.SHELL_KING)&&p.getHand().contains(Cards.SHELL_QUEEN)){
            ret.add("Shell");
            System.out.println(p.getHand().toString());
        }

        return ret;
    }


    private void playerPlayCard(Cards card) {


        schnapsen2erFragment.playCard(card, schnapsen2er.getPlayer());
        schnapsen2er.playCard(card, schnapsen2er.getPlayer());


    }

    private void endRound(Player winningPlayer) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (schnapsen2er.getCpu().getHand().size() > 0) {
                    schnapsen2erFragment.clearMiddleLayout();
                    schnapsen2er.addCardsToStock(winningPlayer);
                    playersDraw();
                    schnapsen2erFragment.updatePoints(schnapsen2er);
                }


            }
        }, Schnapsen2er.MILLIS_UNTIL_PLAYED_CARDS_ARE_REMOVED);
        if (winningPlayer.isNpc()) {
            schnapsen2er.setPlayersTurn(false);
        } else {
            schnapsen2er.setPlayersTurn(true);
        }
        if (!schnapsen2er.isPlayersTurn()) {
            handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (schnapsen2er.getCpu().getHand().size() > 0) {
                        cpuPlayCard();
                    }


                }
            }, Schnapsen2er.MILLIS_UNTIL_PLAYED_CARDS_ARE_REMOVED);
        }
        endGameIfEnded();
    }

    private String gameEnd() {
        Player player = schnapsen2er.getPlayer();
        Player cpu = schnapsen2er.getCpu();
        if (player.getPoints() >= Schnapsen2er.MAXIMUM_POINTS) {
            return "Du hast gewonnen!";
        } else if (cpu.getPoints() >= Schnapsen2er.MAXIMUM_POINTS) {
            return "Du hast verloren!";
        } else if (player.getHand().size() == 0) {
            return "Unentschieden";
        } else {
            return null;
        }
    }

    private void cpuPlayCard() {
        cpuPlayCard(null);
    }

    private void cpuPlayCard(Cards card) {
        Cards cpuCard = getCPUCardToPlay(card);
        schnapsen2erFragment.playCard(cpuCard, schnapsen2er.getCpu());
        schnapsen2er.playCard(cpuCard, schnapsen2er.getCpu());
    }

    public void zudrehen() {
        schnapsen2er.setFarbegeben(true);
        schnapsen2erFragment.removeTrump();
        schnapsen2er.removeRestCards();
        schnapsen2er.setTrumpRemoved(true);
        schnapsen2er.setZudreht();
    }

    public String get20erButtonText(Cards c) {
        if(c.getFarbe()==schnapsen2er.getTrump().getFarbe()){
            return "40er";
        }else{
            return "20er "+c.getFarbe();
        }
    }

    public View.OnClickListener get20erButtonListener(Cards c) {
        View.OnClickListener ret=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c.getFarbe()==schnapsen2er.getTrump().getFarbe()){
                    schnapsen2er.add40er(schnapsen2er.getPlayer());
                }else{
                    schnapsen2er.add20er(schnapsen2er.getPlayer());
                }
                schnapsen2erFragment.updatePoints(schnapsen2er);
                endGameIfEnded();
                ((LinearLayout)v.getParent()).removeView(v);
                setCardsNotTouchableExept(c);
            }
        };
        return ret;
    }

    private void setCardsNotTouchableExept(Cards c) {
        schnapsen2erFragment.disableCardsExept(c,schnapsen2er.getPlayer().getHand());

    }

    public void endGameIfEnded(){
        String end=null;
        if ((end=gameEnd())!=null) {

            schnapsen2erFragment.showGameEndDialog(end);


        }
    }
}
