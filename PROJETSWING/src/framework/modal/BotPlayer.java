package framework.modal;

import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {
    private Random random;

    public BotPlayer(String name) {
        super(name);
        this.random = new Random();
        
    }

    public Card chooseBestCard(Game game) {
        List<Card> validMoves = getValidMoves(game.getTopCard());
        if (validMoves.isEmpty()) {
            return null;
        }
        
        // Enhanced bot strategy - prioritize special cards
        // First, try to play a special card
        for (Card card : validMoves) {
            if (card instanceof ActionCard || card instanceof WildCard) {
                return card;
            }
        }
        
        // If no special cards, play a card matching the color of the top card
        Card topCard = game.getTopCard();
        for (Card card : validMoves) {
            if (card.getColor().equals(topCard.getColor())) {
                return card;
            }
        }
        
        // If no matching color cards, play any valid card
        return validMoves.get(random.nextInt(validMoves.size()));
    }
}