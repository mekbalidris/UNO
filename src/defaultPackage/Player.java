package defaultPackage;

import java.util.ArrayList;
import java.util.List;

import defaultPackage.Card.Value;

public abstract class Player {
    private String name;
    private List<Card> hand = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public Card removeFromHand(int index) {
        return hand.remove(index);
    }
    public void removeFromHand(Card card) {
        hand.remove(card);
    }

    public abstract Card putCard(Card lastCard, Deck gameDeck);

    protected boolean isValidCard(Card card, Card lastCard) {
        return card.getColor() == lastCard.getColor() || 
               card.getValue() == lastCard.getValue();
    }
    List<Card> counter(Value value){
        List<Card> available = new ArrayList<Card>();
        for (Card card : hand) {
            if (card.getValue() == value) {
                available.add(card);
            }
        }
        return available;
    }
}
