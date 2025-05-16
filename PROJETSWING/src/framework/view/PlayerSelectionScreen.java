package framework.view;

import java.awt.*;
import framework.components.*;
import framework.style.*;

public class PlayerSelectionScreen extends CustomPanel {
    private CustomButton backButton;
    private CustomButton nextButton;
    private CustomRadioButton[] playerOptions;
    private  CustomButtonGroup  playerButtonGroup;
    public PlayerSelectionScreen() {
        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);

        // Create title
        CustomLabel titleLabel = new CustomLabel("Select Number of Players");
        titleLabel.setFontSize(32).setBold(true).setAlignment("center");
        titleLabel.setForeground(Color.BLACK);

        // Create options panel
        CustomPanel optionsPanel = new CustomPanel(new GridLayout(3, 1, 0, 20), ColorPalette.BACKGROUND);
        optionsPanel.withPadding(30);

        playerButtonGroup = new CustomButtonGroup();
        playerOptions = new CustomRadioButton[3];

        // Create radio button options
        String[] labels = { "2 Players", "3 Players", "4 Players" };
        playerOptions = new CustomRadioButton[3];

        for (int i = 0; i < labels.length; i++) {
            playerOptions[i] = new CustomRadioButton(labels[i], 20, Fonts.MainFontBOLD, 24, ColorPalette.SELECTED);
        }

        // Select the first option by default
        playerOptions[0].setSelected(true);

        // Add radio buttons to button group and panel
        for (CustomRadioButton option : playerOptions) {
            playerButtonGroup.add(option);
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
    }

    public CustomButton getBackButton() {
        return backButton;
    }

    public CustomButton getNextButton() {
        return nextButton;
    }

    public int getSelectedPlayerCount() {
        for (int i = 0; i < playerOptions.length; i++) {
            if (playerOptions[i].isSelected()) {
                return i + 2; // Options are 2, 3, 4 players
            }
        }
        return 2; // Default if somehow nothing is selected
    }
}