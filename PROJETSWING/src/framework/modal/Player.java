package framework.modal;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
    private boolean unoCalled;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.unoCalled = false;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void drawCard(Deck deck) {
        Card card = deck.drawCard();
        if (card != null) {
            hand.add(card);
        }
    }

    public void addCardToHand(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public boolean playCard(Card card, Game game) {
        if (hand.contains(card) && card.canPlayOn(game.getTopCard())) {
            hand.remove(card);
            game.playCard(card);
            return true;
        }
        return false;
    }

    public List<Card> getValidMoves(Card topCard) {
        List<Card> validMoves = new ArrayList<>();
        for (Card card : hand) {
            if (card.canPlayOn(topCard)) {
                validMoves.add(card);
            }
        }
        return validMoves;
    }

    public int getHandSize() {
        return hand.size();
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }
    
    public boolean isUnoCalled() {
        return unoCalled;
    }
    
    public void setUnoCalled(boolean unoCalled) {
        this.unoCalled = unoCalled;
    }
    
    public boolean isHuman() {
        return this instanceof HumanPlayer;
    }
}