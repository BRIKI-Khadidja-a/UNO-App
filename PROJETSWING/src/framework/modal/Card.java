package framework.modal;
public abstract class Card {
    private String color;
    private String type;
    private int value;
 
    public Card(String color, String type, int value) {
        this.color = color;
        this.type = type;
        this.value = value;
    }
 
    public String getColor() {
        return color;
    }
 
    public void setColor(String color) {
        this.color = color;
    }
 
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }
 
    public int getValue() {
        return value;
    }
 
    public boolean canPlayOn(Card topCard) {
        // Wild cards can always be played
        if (this.isWild()) {
            return true;
        }
        
        // If the top card is a wild card, match the chosen color
        if (topCard.isWild()) {
            return this.color.equalsIgnoreCase(topCard.getColor());
        }
        
        // Match by color or type
        return this.color.equalsIgnoreCase(topCard.getColor()) || 
               this.type.equals(topCard.getType());
    }
    
    public boolean isWild() {
        return this.type.equals("Wild") || this.type.equals("Wild Draw Four");
    }
 
    @Override
    public String toString() {
        if (this.isWild() && this.getColor() != null && !this.getColor().equals("Wild")) {
            return this.getColor() + " " + type;
        } else if (this.isWild()) {
            return type;
        }
        return color + " " + type;
    }
 
    public abstract void applyEffect(Game game);
}