package framework.modal;

public class DrawFourCard extends WildCard {
   public DrawFourCard() {
       super();
       setType("Wild Draw Four");
   }

   @Override
   public void applyEffect(Game game) {
       Player nextPlayer = game.getNextPlayer();
       // Make the next player draw 4 cards
       for (int i = 0; i < 4; i++) {
           nextPlayer.drawCard(game.getDeck());
       }
       // Skip their turn
       game.nextPlayer();
   }
}