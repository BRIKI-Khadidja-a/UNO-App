package framework.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class CustomButton extends JButton {
    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private int width;
    private int height;
    private int cornerRadius = 10;
    private String iconPath;
    
    // Default size dimensions
    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 40;

    
    public CustomButton(String text, Color bgColor, Color textColor) {
        this(text, bgColor, textColor, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

   
    public CustomButton(String text, Color bgColor, Color textColor, int width, int height) {
        super(text);
        this.normalColor = bgColor;
        this.hoverColor = bgColor.brighter();
        this.pressedColor = bgColor.darker();
        this.textColor = textColor;
        this.width = width;
        this.height = height;

        initButton();
        setupHoverEffects();
    }
     
    public CustomButton() {
        this("", Color.BLUE, Color.WHITE); // defaults values
    }


    public CustomButton(String text, Color bgColor, Color textColor, int width, int height, String iconPath) {
        super(text);
        this.normalColor = bgColor;
        this.hoverColor = bgColor.brighter();
        this.pressedColor = bgColor.darker();
        this.textColor = textColor;
        this.width = width;
        this.height = height;
        this.iconPath = iconPath;
        
        // Set icon from path and position it to the right of the text
        if (iconPath != null && !iconPath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(iconPath);
                setIcon(icon);
                setHorizontalTextPosition(SwingConstants.LEFT);
                setIconTextGap(10);
            } catch (Exception e) {
                System.err.println("Failed to load icon from path: " + iconPath);
                e.printStackTrace();
            }
        }
        
        initButton();
        setupHoverEffects();
    }
    
    
    public CustomButton(String text, Color bgColor, Color textColor, String iconPath) {
        this(text, bgColor, textColor, DEFAULT_WIDTH, DEFAULT_HEIGHT, iconPath);
    }
 
    public CustomButton(String text) {
        this(text, Color.BLUE, Color.WHITE);  
    }


    private void initButton() {
        setPreferredSize(new Dimension(width, height));
        setForeground(textColor);
        setBackground(normalColor);
        setFocusPainted(false);
        setBorder(new EmptyBorder(5, 15, 5, 15));
        setFont(new Font("Arial", Font.BOLD, 14));
        setContentAreaFilled(false);
    }

    private void setupHoverEffects() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (contains(e.getPoint())) {
                    setBackground(hoverColor);
                } else {
                    setBackground(normalColor);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
       
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        super.paintComponent(g2);
        g2.dispose();
    }

    
   

   
    public void setButtonSize(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
    }

    
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setNormalColor(Color color) {
        this.normalColor = color;
        setBackground(color);
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    public void setPressedColor(Color color) {
        this.pressedColor = color;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
        setForeground(color);
    }

    public void addCustomActionListener(ActionListener listener) {
        this.addActionListener(listener);
    }

   
    public String getIconPath() {
        return iconPath;
    }
    public void setFontSize(int size) {
        setFont(new Font(getFont().getName(), getFont().getStyle(), size));
    }
    public void setBold(boolean bold) {
        Font currentFont = getFont();
        if (bold) {
            setFont(currentFont.deriveFont(Font.BOLD));
        } else {
            setFont(currentFont.deriveFont(Font.PLAIN));
        }
    }

}