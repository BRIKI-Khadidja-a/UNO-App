package framework.modal;

public class ActionCard extends Card {
   public ActionCard(String color, String type, int value) {
       super(color, type, value);
   }

   @Override
   public void applyEffect(Game game) {
       // Action cards will have their specific effects in subclasses
   }
}
