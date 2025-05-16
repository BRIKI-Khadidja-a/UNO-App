package framework.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import framework.components.*;
import framework.style.ColorPalette;
import framework.style.Fonts;

public class NameEntryScreen extends CustomPanel {
    private CustomButton backButton;
    private CustomButton nextButton;
    private List<CustomTextField> nameFields;
    private List<CustomLabel> nameLabels;
    private CustomPanel namesPanel;
    private int currentPlayerCount = 2;
    
    public NameEntryScreen() {
        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);
        
        // Initialize collections
        nameFields = new ArrayList<>();
        nameLabels = new ArrayList<>();
        
        // Create content components
        CustomLabel titleLabel = new CustomLabel("Enter Player Names");
        titleLabel.setFontSize(32).setBold(true).setAlignment("center");
        titleLabel.setForeground(Color.BLACK);
        
        // Panel for name fields
        namesPanel = new CustomPanel(new GridLayout(0, 1, 0, 15), ColorPalette.BACKGROUND);
        namesPanel.withPadding(35);
        
        // Create navigation buttons
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 40, 0), ColorPalette.BACKGROUND);
        
        backButton = new CustomButton("Back",ColorPalette.BUTTON_BACK , Color.BLACK, 120, 50);
        nextButton = new CustomButton("Next", ColorPalette.BUTTON_COLOR, Color.WHITE, 120, 50);
        
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        
        // Layout content in a BorderLayout
        CustomPanel contentPanel = new CustomPanel(new BorderLayout(0, 50), ColorPalette.BACKGROUND);
        contentPanel.withPadding(20, 50, 20, 50);
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(namesPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Center everything
        CustomPanel centerPanel = new CustomPanel(new GridBagLayout(), ColorPalette.BACKGROUND);
        centerPanel.add(contentPanel);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Initialize with default player count
        updateForPlayerCount(2);
    }
    
    
    public void updateForPlayerCount(int playerCount) {
        this.currentPlayerCount = playerCount;
        
        nameLabels.clear();
        nameFields.clear();
        namesPanel.removeAll();
        
        // Create name entry fields for each human player
        for (int i = 0; i < playerCount; i++) {
            final int playerNum = i + 1;
            
            // Create a panel for each row
            CustomPanel rowPanel = new CustomPanel(new BorderLayout(15, 0), ColorPalette.BACKGROUND);
            
            // Create label
            CustomLabel nameLabel = new CustomLabel("Name of Human " + playerNum);
            nameLabel.setFontSize(18).setBold(true);
            nameLabel.setForeground(Color.BLACK);
            
            // Create text field
            CustomTextField nameField = new CustomTextField(20);
            nameField.setFont(Fonts.MainFontSmaller);
            
            // Store references
            nameLabels.add(nameLabel);
            nameFields.add(nameField);
            
            // Add to row panel
            rowPanel.add(nameLabel, BorderLayout.NORTH);
            rowPanel.add(nameField, BorderLayout.CENTER);
            
            // Add row to names panel
            namesPanel.add(rowPanel);
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
    
   
    public String[] getPlayerNames() {
        String[] names = new String[currentPlayerCount];
        
        for (int i = 0; i < currentPlayerCount; i++) {
            String name = nameFields.get(i).getText().trim();
            names[i] = name.isEmpty() ? "Player " + (i + 1) : name;
        }
        
        return names;
    }
}