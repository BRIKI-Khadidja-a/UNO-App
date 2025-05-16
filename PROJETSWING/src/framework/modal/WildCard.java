package framework.modal;


public class WildCard extends Card {
   private String chosenColor;

   public WildCard() {
       super("Wild", "Wild", 50);
   }

   public void setColor(String color) {
       this.chosenColor = color;
   }

   @Override
   public String getColor() {
       return chosenColor != null ? chosenColor : "Wild";
   }

   @Override
   public void applyEffect(Game game) {
       // Wild cards only change color, no turn-skipping effect
   }

   @Override
   public String toString() {
       if (chosenColor != null) {
           return chosenColor + " " + getType();
       }
       return getType();
   }
}