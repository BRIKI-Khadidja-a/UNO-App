package framework.modal;

public class NumberCard extends Card {
    public NumberCard(String color, int number) {
        super(color, String.valueOf(number), number);
    }
 
    @Override
    public void applyEffect(Game game) {
        // Number cards have no special effect
    }
    public int getNumber() {
        return super.getValue(); // Retourne la valeur numérique de la carte
    }
    
 }
 
 