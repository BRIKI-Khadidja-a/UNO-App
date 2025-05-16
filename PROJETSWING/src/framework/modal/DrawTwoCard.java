package framework.modal;

public class DrawTwoCard extends ActionCard {
   public DrawTwoCard(String color) {
       super(color, "Draw Two", 20);
   }

   @Override
   public void applyEffect(Game game) {
       Player nextPlayer = game.getNextPlayer();
       nextPlayer.drawCard(game.getDeck());
       nextPlayer.drawCard(game.getDeck());
       game.nextPlayer(); // Skip next player's turn
   }
}
