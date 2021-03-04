package blackjack.controller;

import blackjack.domain.Dealer;
import blackjack.domain.Player;
import blackjack.domain.Users;
import blackjack.domain.card.Card;
import blackjack.domain.card.CardDeck;
import blackjack.view.InputView;
import blackjack.view.OutputView;

public class BlackJackController {
    private static final int DEALER_REDRAW_STANDARD = 17;

    private final Users users;
    private final Dealer dealer;
    private final CardDeck cardDeck;

    public BlackJackController() {
        this.dealer = new Dealer();
        this.cardDeck = CardDeck.createDeck();
        this.users = new Users(dealer, InputView.scanPlayerNames());
        users.initialHit(cardDeck);
        OutputView.printInitialComment(users);
    }

    public void run() {
        if (dealer.isBlackJack()) {
            OutputView.printResult(users.checkWinOrLose(dealer.getScore()));
            return;
        }
        users.getPlayers()
                .forEach(this::playGameForEachPlayer);
        while (dealer.getScore() < DEALER_REDRAW_STANDARD && dealer.getScore() != Card.BUST) {
            dealer.hit(cardDeck.drawCard());
            OutputView.printDealerGetNewCardsMessage();
        }
        OutputView.printCardsOfPlayersWithScore(users);
        OutputView.printResult(users.checkWinOrLose(dealer.getScore()));
    }

    private void playGameForEachPlayer(Player player) {
        while (!player.isStay()) {
            requestHitOrNot(player);
        }
    }

    private void requestHitOrNot(Player player) {
        if (InputView.isHit(player.getName())) {
            player.hit(cardDeck.drawCard());
            OutputView.printCardsOfPlayer(player);
            return;
        }
        player.stay();
    }
}
