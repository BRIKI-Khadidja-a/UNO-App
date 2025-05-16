package framework.modal;

public class ReverseCard extends ActionCard {
   public ReverseCard(String color) {
       super(color, "Reverse", 20);
   }

   @Override
   public void applyEffect(Game game) {
       game.reverseDirection();
   }
}
