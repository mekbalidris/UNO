package defaultPackage;

import defaultPackage.Card.Color;
import defaultPackage.Card.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck() {
        Card[] tempCards = new Card[100]; // adjusted size without Wild cards

        for (int i = 0; i < 4; i++) { // four colors: Red, Blue, Green, Yellow
            int j = getRandomIndex(tempCards);

            // Add one card with value 0 for each color
            tempCards[j] = new Card(Color.getColors(i), Value.getValue(0));

            // Add two cards for each value 1-12 for each color
            for (int l = 1; l <= 12; l++) {
                for (int f = 0; f < 2; f++) {
                    int k = getRandomIndex(tempCards);
                    tempCards[k] = new Card(Color.getColors(i), Value.getValue(l));
                }
            }
        }

        // Add the cards to the deck
        for (Card card : tempCards) {
            this.cards.add(card);
        }
    }

    private int getRandomIndex(Card[] cards) {
        Random random = new Random();
        int i;
        do {
            i = random.nextInt(100);
        } while (cards[i] != null);
        return i;
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public int numOfCards() {
        return cards.size();
    }

    public Card popCard() {
        return cards.remove(cards.size() - 1);
    }
}
