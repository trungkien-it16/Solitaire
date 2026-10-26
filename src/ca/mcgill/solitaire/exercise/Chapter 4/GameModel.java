package ca.mcgill.solitaire.exercise;

import ca.mcgill.solitaire.cards.Deck;

public final class GameModel {

    private static final GameModel INSTANCE = new GameModel();

    private final Deck aDeck = new Deck();

    private GameModel() {
    }

    public static GameModel instance() {
        return INSTANCE;
    }

    public void newGame() {
        aDeck.shuffle();
    }
}