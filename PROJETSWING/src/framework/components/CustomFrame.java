package framework.components;

import javax.swing.*;
import java.awt.*;


public class CustomFrame extends JFrame {
    private static final Color DEFAULT_BACKGROUND = new Color(245, 245, 245);
    private static final String DEFAULT_TITLE = "Custom Application";
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    
    public CustomFrame() {
        this(DEFAULT_TITLE);
 
    }
    
    public CustomFrame(String title) {
        super(title);
        initializeFrame();
    }

    public CustomFrame(String title, int width, int height) {
        super(title);
        setSize(width, height);
        initializeFrame();
    }

    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setBackground(DEFAULT_BACKGROUND);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null); // Center on screen
        
    }
   
    public void setContentPanel(CustomPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }
    
  
    public void setFrameIcon(String iconPath) {
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
        }
    }
   
   
    public void setFrameResizable(boolean resizable) {
        setResizable(resizable);
    }
    
   
    public void display() {
        setVisible(true);
    }
   
    public void centerOnScreen() {
        setLocationRelativeTo(null);
    }
}