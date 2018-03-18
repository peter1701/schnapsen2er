package com.example.peter.cardanimationtest.gamelogic;

import java.util.List;

/**
 * Created by Peter on 06.02.2018.
 */

public interface Game {

    // -1 when it is not over yet, otherwise the index of the player who won/lost
    public int playerWon(List<Player> players );

    public int playerLost(List<Player> players);

    //One turn is played
    public Game playOneTurn(Game game);

    //Setting up the gui and standard values
    public void setUp();
}
