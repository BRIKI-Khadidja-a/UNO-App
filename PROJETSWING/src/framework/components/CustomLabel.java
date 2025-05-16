package framework.components;
import java.awt.Color;
import javax.swing.Icon;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class CustomLabel extends JLabel {
    
   
    public CustomLabel() {
        super();
        initializeDefaults();
    }
    
    
    public CustomLabel(String text) {
        super(text);
        initializeDefaults();
    }
    
    
    public CustomLabel(String text, String horizontalAlignment) {
        super(text, convertAlignment(horizontalAlignment));
        initializeDefaults();
    }
    
    
    public CustomLabel(String text, String imagePath, String horizontalAlignment, String verticalAlignment) {
        super(text, convertAlignment(horizontalAlignment));
        setIcon(new ImageIcon(imagePath));
        setVerticalAlignment(convertAlignment(verticalAlignment));
        initializeDefaults();
    }
    
    public CustomLabel(Icon icon) {
        super(icon);
        initializeDefaults();
    }

    
    private static int convertAlignment(String alignment) {
        if (alignment == null) {
            return SwingConstants.LEADING;
        }
        
        switch (alignment.toLowerCase()) {
            case "left":
                return SwingConstants.LEFT;
            case "center":
                return SwingConstants.CENTER;
            case "right":
                return SwingConstants.RIGHT;
            case "bottom":
                return SwingConstants.BOTTOM;
            case "top":
                return SwingConstants.TOP;
            default:
                return SwingConstants.LEADING;
        }
    }
    
    
    private void initializeDefaults() {
        setOpaque(false);
        setFocusable(false);
    }
    
    
    public CustomLabel setFontSize(int size) {
        setFont(getFont().deriveFont((float) size));
        return this;
    }
    
    
    public CustomLabel centerImage() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        return this;
    }

    public CustomLabel setImageWithSize(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        ImageIcon resizedIcon = new ImageIcon(originalIcon.getImage()
                .getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        setIcon(resizedIcon);
        centerImage();
        return this;
    }
    
    
    public CustomLabel setUnderlined() {
        Border current = getBorder();
        Border underline = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY);
        setBorder(BorderFactory.createCompoundBorder(current != null ? current : BorderFactory.createEmptyBorder(), underline));
        return this;
    }

    
    public CustomLabel setBold(boolean bold) {
        setFont(getFont().deriveFont(bold ? 
                java.awt.Font.BOLD : java.awt.Font.PLAIN));
        return this;
    }
    
   
    public CustomLabel setAlignment(String alignment) {
        setHorizontalAlignment(convertAlignment(alignment));
        return this;
    }
}