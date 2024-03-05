package blackjack.domain.card;

import java.util.Stack;

public class Deck {

    private final Stack<Card> deck;

    public Deck(DeckFactory deckFactory) {
        this.deck = deckFactory.generate();
    }
}
