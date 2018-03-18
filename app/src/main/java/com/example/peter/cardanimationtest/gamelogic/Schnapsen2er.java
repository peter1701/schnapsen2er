package com.example.peter.cardanimationtest.gamelogic;

import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Peter on 06.02.2018.
 */

public class Schnapsen2er implements Game {


    public static final int MAXIMUM_POINTS = 66;
    private List<Cards> cardsList = new ArrayList<>();
    public static final int MILLIS_UNTIL_PLAYED_CARDS_ARE_REMOVED = 2000;

    ArrayList<Cards> cards;
    Cards trump;
    Cards save;
    Cards cardPlayer;
    Cards cardCPU;

    boolean gameEnd = false;
    boolean farbegeben = false;
    boolean playersTurn = true;
    boolean trumpRemoved = false;

    Player cpu = new Player();
    Player player = new Player();
    List<Player> players=new ArrayList<>();

    List<View> l;

    private boolean zudreht;

    public Schnapsen2er() {
        playersTurn = true;
        cards = new ArrayList<>(Arrays.asList(Cards.values()));

        player = new Player();
        player.setNpc(false);
        cpu = new Player();
        cpu.setNpc(true);

        players.add(player);
        players.add(cpu);
        gameEnd = false;


    }

    public void changeTrump(Cards card) {
        player.getHand().add(trump);
        player.getHand().remove(card);

        System.out.println("Cards after chaging trump: " + player.getHand().size());

        trump = card;
    }

    @Override
    public int playerWon(List<Player> players) {
        return 0;
    }

    @Override
    public int playerLost(List<Player> players) {
        return 0;
    }

    @Override
    public Game playOneTurn(Game game) {
        return null;
    }

    @Override
    public void setUp() {

    }

    private void reset() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                setUp();

            }
        }, 3000);
    }

    private Cards drawCard() {
        if (cards.size() == 0 && !trumpRemoved) {
            trumpRemoved = true;
            return trump;
        } else if (cards.size() == 0 && trumpRemoved) {
            return null;
        }
        int tr = (int) (Math.random() * 100) % cards.size();
        Cards c = cards.get(tr);
        cards.remove(c);
        return c;
    }

    public Cards playerDrawCard(Player player) {
        Cards c = drawCard();
        if (c != null)
            player.getHand().add(c);
        return c;
    }


    public void setTrump() {
        trump = drawCard();
    }

    public List<Cards> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<Cards> cardsList) {
        this.cardsList = cardsList;
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }

    public Cards getTrump() {
        return trump;
    }

    public void setTrump(Cards trump) {
        this.trump = trump;
    }

    public Cards getSave() {
        return save;
    }

    public void setSave(Cards save) {
        this.save = save;
    }

    public Cards getCardPlayer() {
        return cardPlayer;
    }

    public void setCardPlayer(Cards cardPlayer) {
        this.cardPlayer = cardPlayer;
    }

    public Cards getCardCPU() {
        return cardCPU;
    }

    public void setCardCPU(Cards cardCPU) {
        this.cardCPU = cardCPU;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public boolean isFarbegeben() {
        return farbegeben;
    }

    public void setFarbegeben(boolean farbegeben) {
        this.farbegeben = farbegeben;
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }

    public void setPlayersTurn(boolean playersTurn) {
        this.playersTurn = playersTurn;
    }

    public Player getCpu() {
        return cpu;
    }

    public void setCpu(Player cpu) {
        this.cpu = cpu;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<View> getL() {
        return l;
    }

    public void setL(List<View> l) {
        this.l = l;
    }

    public void playCard(Cards card, Player p) {
        p.setPlayedCard(card);
        p.getHand().remove(card);


    }

    public void addCardsToStock(Player winningPlayer) {
        winningPlayer.getStock().add(player.getPlayedCard());
        winningPlayer.getStock().add(cpu.getPlayedCard());

    }

    public boolean isTrumpRemoved() {
        return trumpRemoved;
    }

    public void setTrumpRemoved(boolean trumpRemoved) {
        this.trumpRemoved = trumpRemoved;
    }

    public void resetDeck() {
        this.cards = new ArrayList<>(Arrays.asList(Cards.values()));
    }

    public void removeRestCards() {
        cards.removeAll(cards);
    }

    public void setZudreht() {
        this.zudreht=true;

    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void add40er(Player player) {
        player.setSpecialPoints(player.getSpecialPoints()+40);
    }

    public void add20er(Player player) {
        player.setSpecialPoints(player.getSpecialPoints()+20);
    }
}