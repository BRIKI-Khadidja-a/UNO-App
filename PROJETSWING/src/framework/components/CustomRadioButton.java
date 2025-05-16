package framework.components;
import java.awt.*;
import javax.swing.*;


public class CustomRadioButton extends JRadioButton {
    
    private Color selectedColor = new Color(0, 0, 0);
    private Color unselectedColor = Color.WHITE;
    private Color borderColor = Color.GRAY;
    
    // Size of the radio button circle
    private int buttonSize = 16;
    
    
    public CustomRadioButton(String text) {
        super(text);
        configure();
    }
    
    
    public CustomRadioButton(String text, int buttonSize) {
        super(text);
        this.buttonSize = buttonSize;
        configure();
    }
    
   
    public CustomRadioButton(String text, int buttonSize, Font font, int fontSize ,Color selectedColor) {
        super(text);
        this.buttonSize = buttonSize;
        setFont(new Font(getFont().getName(), getFont().getStyle(), fontSize));
        setFont(font);
        this.selectedColor = selectedColor;
        configure();
    }
    
    
    public CustomRadioButton(String text, int buttonSize, Font font) {
        super(text);
        this.buttonSize = buttonSize;
        setFont(font);
        configure();
    }
    
    private void configure() {
        setOpaque(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setIconTextGap(8);
    }
    
   
    public void setButtonSize(int size) {
        this.buttonSize = size;
        repaint();
    }
    
    
    public int getButtonSize() {
        return this.buttonSize;
    }
    
    
    public void setFontSize(int size) {
        setFont(new Font(getFont().getName(), getFont().getStyle(), size));
        repaint();
    }
    
    
    public void setSelectedColor(Color color) {
        this.selectedColor = color;
        repaint();
    }
    
    
    public void setUnselectedColor(Color color) {
        this.unselectedColor = color;
        repaint();
    }
    
   
    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw the radio button
        int x = 2;
        int y = getHeight() / 2 - buttonSize / 2;
        
        // Draw outer circle (border)
        g2.setColor(borderColor);
        g2.drawOval(x, y, buttonSize, buttonSize);
        
        // Fill the background of the radio button
        g2.setColor(unselectedColor);
        g2.fillOval(x + 1, y + 1, buttonSize - 2, buttonSize - 2);
        
        // Draw the selected state if needed
        if (isSelected()) {
            g2.setColor(selectedColor);
            int innerSize = buttonSize / 2;
            int innerX = x + (buttonSize - innerSize) / 2;
            int innerY = y + (buttonSize - innerSize) / 2;
            g2.fillOval(innerX, innerY, innerSize, innerSize);
        }
        
        // Set the font and draw text
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textX = x + buttonSize + getIconTextGap();
        int textY = getHeight() / 2 + fm.getAscent() / 2 - 2;
        
        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);
    }
}