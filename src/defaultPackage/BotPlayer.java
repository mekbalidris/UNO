package defaultPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {

    @Override
    public Card putCard(Card lastCard, Deck gameDeck) {
        List<Card> validCards = new ArrayList<>();

        for (Card card : getHand()) {
            if (isValidCard(card, lastCard)) {
                validCards.add(card);
            }
        }

        if (!validCards.isEmpty()) {
            Random random = new Random();
            Card selectedCard = validCards.get(random.nextInt(validCards.size()));
            getHand().remove(selectedCard);
            System.out.println(getName() + " played: " + selectedCard);
            return selectedCard;
        } else {
            Card newCard = gameDeck.popCard();
            addCard(newCard);
            System.out.println(getName() + " drew a card.");
            return null;
        }
        
    }
}
