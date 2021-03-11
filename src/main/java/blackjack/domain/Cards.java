package blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Cards {

    private static final int ACE_BONUS_SCORE = 10;
    private static final int BOUND_OF_CHANGE_ACE_VALUE = 11;
    private static final int NO_BONUS_SCORE = 0;

    private final List<Card> cards;

    public Cards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public Cards() {
        this(new ArrayList<>());
    }

    public void addAll(Cards targetCards) {
        cards.addAll(targetCards.cards);
    }

    public void add(Card card) {
        cards.add(card);
    }

    public int calculateScore() {
        List<Card> cardsWithLastSortedAces = cards.stream()
                .sorted(Comparator.comparing(card -> card.isAce()))
                .collect(Collectors.toList());
        int sum = 0;
        for (Card card : cardsWithLastSortedAces) {
            int score = card.getScore(sum);
            sum += score;
        }
        return sum;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
