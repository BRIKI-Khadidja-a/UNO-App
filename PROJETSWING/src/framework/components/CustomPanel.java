package framework.components;
import javax.swing.*;
import java.awt.*;


public class CustomPanel extends JPanel {
     
    public CustomPanel() {
        super();
        initialize();
    }
    
    public CustomPanel(LayoutManager layout) {
        super(layout);
        initialize();
    }
  
    public CustomPanel(Color bgColor) {
        super();
        setBackground(bgColor);
        initialize();
    }
    
  
    public CustomPanel(LayoutManager layout, Color bgColor) {
        super(layout);
        setBackground(bgColor);
        initialize();
    }
    
    private void initialize() {
        setOpaque(true);
        setDoubleBuffered(true);
    }
    

    public CustomPanel withLayout(LayoutManager layout) {
        setLayout(layout);
        return this;
    }
    
 
    public CustomPanel withBackgroundColor(Color bgColor) {
        setBackground(bgColor);
        return this;
    }
    
   
    public CustomPanel withPadding(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return this;
    }
    
   
    public CustomPanel withPadding(int padding) {
        return withPadding(padding, padding, padding, padding);
    }
}