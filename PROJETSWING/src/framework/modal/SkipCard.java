package framework.modal;

public class SkipCard extends ActionCard {
    public SkipCard(String color) {
        super(color, "Skip", 20);
    }
 
    @Override
    public void applyEffect(Game game) {
        game.nextPlayer(); // Skip next player's turn
    }
 }