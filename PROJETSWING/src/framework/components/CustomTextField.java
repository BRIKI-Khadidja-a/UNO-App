package framework.components;
import java.awt.*;
import javax.swing.*;

public class CustomTextField extends JTextField {
   private Color backgroundColor = Color.WHITE;
   private Color borderColor = new Color(200, 200, 200);
   private boolean rounded = true;
   private int radius = 10;
   
   public CustomTextField() {
       super();
       setup();
   }
   
   public CustomTextField(String text) {
       super(text);
       setup();
   }
   
   public CustomTextField(int columns) {
       super(columns);
       setup();
   }
   
   private void setup() {
       setOpaque(false);
       setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
   }
   
   public void setBackgroundColor(Color color) {
       this.backgroundColor = color;
       repaint();
   }
   
   public void setBorderColor(Color color) {
       this.borderColor = color;
       repaint();
   }
   
   public void setRounded(boolean rounded, int radius) {
       this.rounded = rounded;
       this.radius = radius;
       repaint();
   }
   
   @Override
   protected void paintComponent(Graphics g) {
       Graphics2D g2 = (Graphics2D) g;
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
       // Draw background
       g2.setColor(backgroundColor);
       if (rounded) {
           g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
           g2.setColor(borderColor);
           g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
       } else {
           g2.fillRect(0, 0, getWidth(), getHeight());
           g2.setColor(borderColor);
           g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
       }
       
       super.paintComponent(g);
   }
}