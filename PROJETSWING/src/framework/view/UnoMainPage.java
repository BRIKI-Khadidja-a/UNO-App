package framework.view;

import java.awt.*;
import framework.components.*;
import framework.style.ColorPalette;


 //  Main page for the UNO Game application 
public class UnoMainPage extends CustomPanel {
    private CustomButton playButton;
    
    public UnoMainPage() {
        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);

        // Create a central panel to hold our content
        CustomPanel centerPanel = new CustomPanel(new GridBagLayout(), ColorPalette.BACKGROUND);
        
        // Create a vertical box panel for the logo and button
        CustomPanel contentPanel = new CustomPanel(new BorderLayout(0, 80), ColorPalette.BACKGROUND);
        contentPanel.withPadding(20);
        
        // Create the logo panel
        CustomPanel logoPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER), ColorPalette.BACKGROUND);
        CustomLabel logoLabel = new CustomLabel();
        logoLabel.setImageWithSize("resources/images/UNO_Logo.png", 300, 150);
        logoPanel.add(logoLabel);
        
        // Create the button panel
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER), ColorPalette.BACKGROUND);
        playButton = new CustomButton("Play Game", ColorPalette.BUTTON_COLOR, ColorPalette.TEXT_PRIMARY, 200, 50, "resources/images/arrow.png");
        buttonPanel.add(playButton);
        
        // Add components to content panel
        contentPanel.add(logoPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add content panel to the center panel using GridBagLayout for true centering
        centerPanel.add(contentPanel);
        
        // Add center panel to main panel
        add(centerPanel, BorderLayout.CENTER);
    }
    
    public CustomButton getPlayButton() {
        return playButton;
    }
}