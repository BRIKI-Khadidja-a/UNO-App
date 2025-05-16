package framework.view;

import java.awt.*;
import framework.components.*;
import framework.style.ColorPalette;
import framework.style.Fonts;

/**
 * Screen for selecting the number of bot players
 */
public class BotSelectionScreen extends CustomPanel {
    private CustomButton backButton;
    private CustomButton nextButton;
    private CustomRadioButton[] botOptions;
    private CustomButtonGroup botButtonGroup;
    private int totalPlayers = 2; // Default to 2 total players
    
    public BotSelectionScreen() {
        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);
        
        // Create title
        CustomLabel titleLabel = new CustomLabel("Select Number of Bot Players");
        titleLabel.setFontSize(32).setBold(true).setAlignment("center");
        titleLabel.setForeground(Color.BLACK);
        
        // Create options panel
        CustomPanel optionsPanel = new CustomPanel(new GridLayout(4, 1, 0, 15), ColorPalette.BACKGROUND);
        optionsPanel.withPadding(30);
        
        botButtonGroup = new CustomButtonGroup();
        botOptions = new CustomRadioButton[4];
        
        // Create radio button options
        botOptions[0] = new CustomRadioButton("0 Bot Players", 20, Fonts.MainFontBOLD, 24, ColorPalette.SELECTED);
        botOptions[1] = new CustomRadioButton("1 Bot Player", 20, Fonts.MainFontBOLD, 24, ColorPalette.SELECTED);
        botOptions[2] = new CustomRadioButton("2 Bot Players", 20, Fonts.MainFontBOLD, 24, ColorPalette.SELECTED);
        botOptions[3] = new CustomRadioButton("3 Bot Players", 20, Fonts.MainFontBOLD, 24, ColorPalette.SELECTED);
        
        // Select the first option by default
        botOptions[0].setSelected(true);
        
        // Add radio buttons to button group and panel
        for (CustomRadioButton option : botOptions) {
            botButtonGroup.add(option);
            optionsPanel.add(option);
        }
        
        // Create navigation buttons
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 40, 0), ColorPalette.BACKGROUND);
        
        backButton = new CustomButton("Back", ColorPalette.BUTTON_BACK, Color.BLACK, 120, 50);
        nextButton = new CustomButton("Next", ColorPalette.BUTTON_COLOR, Color.WHITE, 120, 50);
        
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        
        // Create main content panel to center everything
        CustomPanel contentPanel = new CustomPanel(new BorderLayout(0, 50), ColorPalette.BACKGROUND);
        contentPanel.withPadding(20, 50, 20, 50);
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(optionsPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Center everything
        CustomPanel centerPanel = new CustomPanel(new GridBagLayout(), ColorPalette.BACKGROUND);
        centerPanel.add(contentPanel);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Initialize with default values
        updateBotOptions(2);
    }

    public void updateBotOptions(int totalPlayerCount) {
        this.totalPlayers = totalPlayerCount;
        
        // Calculate the maximum number of bots allowed (total players - 1 human)
        int maxBots = totalPlayerCount - 1;
        
        // Update visibility and enabled state of bot options
        for (int i = 0; i < botOptions.length; i++) {
            if (i <= maxBots) {
                botOptions[i].setVisible(true);
                botOptions[i].setEnabled(true);
            } else {
                botOptions[i].setVisible(false);
                botOptions[i].setEnabled(false);
            }
        }
        
        // If the currently selected option is no longer valid, select the first valid option
        boolean validSelection = false;
        for (int i = 0; i <= maxBots; i++) {
            if (botOptions[i].isSelected()) {
                validSelection = true;
                break;
            }
        }
        
        if (!validSelection) {
            botOptions[0].setSelected(true);
        }
        
        revalidate();
        repaint();
    }
    
    public CustomButton getBackButton() {
        return backButton;
    }
    
    public CustomButton getNextButton() {
        return nextButton;
    }
    
    public int getSelectedBotCount() {
        for (int i = 0; i < botOptions.length; i++) {
            if (botOptions[i].isSelected()) {
                return i; // Options are 0, 1, 2, 3 bots
            }
        }
        return 0; // Default if somehow nothing is selected
    }
    
   
    public int getHumanPlayerCount() {
        return totalPlayers - getSelectedBotCount();
    }
}