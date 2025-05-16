package framework.view;

import framework.modal.*;
import framework.controller.GameController;
import framework.components.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends CustomPanel {
    private GameController controller;
    
    // Components for players
    private final CustomPanel northPlayerPanel;
    private final CustomPanel westPlayerPanel;
    private final CustomPanel eastPlayerPanel;
    private final CustomLabel[] playerLabels;
    private final CustomPanel[] playerCardsPanels;
    
    // Center components
    private final CustomPanel centerPanel;
    private final UnoCardComponent deckComponent;
    private UnoCardComponent discardPileComponent;
    private CustomLabel infoLabel;
    private CustomLabel currentColorLabel;
    private CustomLabel direction;
    
    // Game log panel
    private final JTextArea gameLogArea;
    private final JScrollPane gameLogScrollPane;
    
    // Player hand panels (for multiple human players)
    private final ArrayList<CustomPanel> humanPlayerPanels;
    private final CustomPanel currentPlayerPanel;
    private CustomLabel currentPlayerLabel;
    
    // Control panel for game actions
    private final CustomPanel controlPanel;
    private final CustomButton unoButton;
    private final CustomButton drawCardButton;
   
    
    private final Timer animationTimer;

    public GameScreen(GameController controller) {
        super(new GridBagLayout());
        this.controller = controller;
        
        // Initialize player components
        this.playerLabels = new CustomLabel[3];
        this.playerCardsPanels = new CustomPanel[3];
        
        this.northPlayerPanel = new CustomPanel(new BorderLayout());
        this.westPlayerPanel = new CustomPanel(new BorderLayout());
        this.eastPlayerPanel = new CustomPanel(new BorderLayout());
        
        // Initialize other components
        this.centerPanel = new CustomPanel(new BorderLayout());
        this.currentPlayerPanel = new CustomPanel(new BorderLayout());
        this.humanPlayerPanels = new ArrayList<>();
        
        // Initialize game log
        this.gameLogArea = new JTextArea(10, 30);
        this.gameLogScrollPane = new JScrollPane(gameLogArea);
        
        // Initialize control panel
        this.controlPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        this.unoButton = new CustomButton("UNO!", new Color(255, 0, 0), Color.WHITE);
        this.drawCardButton = new CustomButton("Draw Card", new Color(0, 100, 200), Color.WHITE);
     
        this.animationTimer = new Timer(50, null);
        this.infoLabel = new CustomLabel();
        this.currentColorLabel = new CustomLabel();
        this.direction = new CustomLabel();
        this.currentPlayerLabel = new CustomLabel();
        this.deckComponent = new UnoCardComponent("/cards/draw-pile-4-cards.png");
        this.discardPileComponent = new UnoCardComponent((Card)null);
        
        initializeUI();
        setupEventHandlers();
    }

    private void initializeUI() {
        setBackground(new Color(204, 0, 0)); // Red background (#CC0000)

        // Configure player panels
        configurePlayerPanels();
        configureCenterPanel();
        configureGameLogPanel();
        configureControlPanel();
        configureCurrentPlayerPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        //  Row 0 
        gbc.gridy = 0;

        gbc.gridx = 0;
        add(createEmptyPanel(), gbc);
        gbc.gridx = 1;
        add(northPlayerPanel, gbc);
        gbc.gridx = 2;
        add(createEmptyPanel(), gbc);

        //  Row 1 
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(westPlayerPanel, gbc);
        gbc.gridx = 1;
        add(centerPanel, gbc);
        gbc.gridx = 2;
        add(eastPlayerPanel, gbc);

        // Row 2
        gbc.gridy = 2;
        gbc.weighty = 0.1; // Give less height to the game log
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        add(gameLogScrollPane, gbc);

        // Row 3
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.05; // Make control panel smaller
        add(controlPanel, gbc);
        
        // Row 4 
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.6; // Make current player panel larger
        add(currentPlayerPanel, gbc);
    }

    // Helper for empty panel
    private CustomPanel createEmptyPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setOpaque(false); // Transparent
        return panel;
    }

    private void configurePlayerPanels() {
        for (int i = 0; i < 3; i++) {
            playerLabels[i] = new CustomLabel()
                .setAlignment("center")
                .setBold(true)
                .setFontSize(14);
            playerLabels[i].setForeground(Color.WHITE);

            playerCardsPanels[i] = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            playerCardsPanels[i].setOpaque(true);
            playerCardsPanels[i].setBackground(new Color(153, 0, 0, 150)); // Semi-transparent red
            playerCardsPanels[i].setBorder(new LineBorder(Color.WHITE, 2)); // White border
        }

        northPlayerPanel.add(playerLabels[0], BorderLayout.NORTH);
        northPlayerPanel.add(playerCardsPanels[0], BorderLayout.CENTER);
        northPlayerPanel.setOpaque(false);

        westPlayerPanel.add(playerLabels[1], BorderLayout.NORTH);
        westPlayerPanel.add(playerCardsPanels[1], BorderLayout.CENTER);
        westPlayerPanel.setOpaque(false);

        eastPlayerPanel.add(playerLabels[2], BorderLayout.NORTH);
        eastPlayerPanel.add(playerCardsPanels[2], BorderLayout.CENTER);
        eastPlayerPanel.setOpaque(false);
    }

    private void configureCenterPanel() {
        centerPanel.setOpaque(true);
        centerPanel.setBackground(new Color(153, 0, 0, 150));

        infoLabel = new CustomLabel()
            .setAlignment("center")
            .setBold(true)
            .setFontSize(18);
        infoLabel.setForeground(Color.WHITE);

        currentColorLabel = new CustomLabel()
            .setAlignment("center")
            .setBold(true)
            .setFontSize(14);
        currentColorLabel.setForeground(Color.WHITE);

        direction = new CustomLabel()
            .setAlignment("center")
            .setBold(true)
            .setFontSize(12);
            direction.setForeground(Color.WHITE);

        CustomPanel infoPanel = new CustomPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(infoLabel);
        infoPanel.add(currentColorLabel);
        infoPanel.add(direction);

        deckComponent.setPreferredSize(new Dimension(80, 120));
        discardPileComponent.setPreferredSize(new Dimension(80, 120));

        CustomPanel cardsPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.add(deckComponent);
        cardsPanel.add(discardPileComponent);

        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(cardsPanel, BorderLayout.CENTER);
    }
    
    private void configureGameLogPanel() {
        gameLogArea.setEditable(false);
        gameLogArea.setLineWrap(true);
        gameLogArea.setWrapStyleWord(true);
        gameLogArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gameLogArea.setBackground(new Color(255, 255, 220)); // Light yellow background
        gameLogArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Add a titled border to the scroll pane
        gameLogScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), 
            "Game Log",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        // Add initial message
        appendToGameLog("Welcome to UNO! Game starting...");
    }
    
    private void configureControlPanel() {
        controlPanel.setOpaque(true);
        controlPanel.setBackground(new Color(50, 50, 50, 200));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        unoButton.setPreferredSize(new Dimension(100, 40));
        drawCardButton.setPreferredSize(new Dimension(150, 40));
      
        
        controlPanel.add(unoButton);
        controlPanel.add(drawCardButton);
      
    }
    
    private void configureCurrentPlayerPanel() {
        currentPlayerPanel.setOpaque(true);
        currentPlayerPanel.setBackground(new Color(30, 30, 30, 200));
        currentPlayerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        currentPlayerLabel = new CustomLabel("YOUR HAND")
            .setAlignment("center")
            .setBold(true)
            .setFontSize(16);
        currentPlayerLabel.setForeground(Color.WHITE);
        
        CustomPanel handContainer = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        handContainer.setOpaque(false);
        
        currentPlayerPanel.add(currentPlayerLabel, BorderLayout.NORTH);
        currentPlayerPanel.add(handContainer, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        // Click handler for deck
        deckComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null && controller.canDrawCard()) {
                    controller.drawCard();
                    appendToGameLog(controller.getCurrentPlayerName() + " drew a card.");
                } else {
                    Toolkit.getDefaultToolkit().beep(); // Sound feedback
                    appendToGameLog("You cannot draw a card right now.");
                }
            }
        });
        
        // UNO button handler
        unoButton.addCustomActionListener(e -> {
            if (controller != null) {
                controller.callUno();
                appendToGameLog(controller.getCurrentPlayerName() + " called UNO!");
            }
        });
        
        // Draw card button handler
        drawCardButton.addCustomActionListener(e -> {
            if (controller != null && controller.canDrawCard()) {
                controller.drawCard();
                // Désactiver le bouton après pioche
                drawCardButton.setEnabled(false);
            } else {
                Toolkit.getDefaultToolkit().beep();
                appendToGameLog("You cannot draw a card right now.");
            }
        });
    }

    public void appendToGameLog(String message) {
        gameLogArea.append(message + "\n");
        // Auto-scroll to bottom
        gameLogArea.setCaretPosition(gameLogArea.getDocument().getLength());
    }

    public void updateGameBoard(Player currentPlayer, List<Player> opponents, Card topCard) {
        if (controller == null) return;
        
        // Update opponent names and hands
        for (int i = 0; i < Math.min(opponents.size(), 3); i++) {
            Player opponent = opponents.get(i);
            playerLabels[i].setText(opponent.getName() + " (" + opponent.getHand().size() + " cards)");
            
            playerCardsPanels[i].removeAll();
            for (int j = 0; j < opponent.getHand().size(); j++) {
                UnoCardComponent cardBack = new UnoCardComponent("/cards/back.png");
                cardBack.setPreferredSize(new Dimension(40, 60));
                playerCardsPanels[i].add(cardBack);
            }
            playerCardsPanels[i].revalidate();
            playerCardsPanels[i].repaint();
        }
        
        // Hide empty player panels
        for (int i = opponents.size(); i < 3; i++) {
            playerLabels[i].setText("");
            playerCardsPanels[i].removeAll();
            playerCardsPanels[i].revalidate();
            playerCardsPanels[i].repaint();
        }
        
        // Update other elements
        updateDiscardPile(topCard);
        updateCurrentPlayerHand(currentPlayer);
        updateGameInfo(currentPlayer, topCard);
        
        // Update buttons state
        updateControlButtons(currentPlayer);
    }

    private void updateDiscardPile(Card topCard) {
        if (discardPileComponent == null) {
            discardPileComponent = new UnoCardComponent(topCard);
            discardPileComponent.setPreferredSize(new Dimension(80, 120));
        } else {
            discardPileComponent.setCard(topCard);
        }
        
        if (topCard != null) {
            animateCardPlay();
            showCardEffect(topCard);
        }
    }

    private void animateCardPlay() {
        if (animationTimer.getActionListeners().length > 0) {
            animationTimer.removeActionListener(animationTimer.getActionListeners()[0]);
        }
        
        discardPileComponent.setSize(60, 90);
        
        animationTimer.addActionListener(e -> {
            int newWidth = discardPileComponent.getWidth() + 4;
            int newHeight = discardPileComponent.getHeight() + 6;
            
            if (newWidth >= 80) {
                discardPileComponent.setPreferredSize(new Dimension(80, 120));
                discardPileComponent.setSize(80, 120);
                animationTimer.stop();
            } else {
                discardPileComponent.setSize(newWidth, newHeight);
            }
        });
        
        animationTimer.start();
    }

    private void showCardEffect(Card card) {
        Color flashColor = getEffectColor(card);
        
        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    discardPileComponent.setBorder(new LineBorder(flashColor, 3));
                    Thread.sleep(200);
                    discardPileComponent.setBorder(null);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private Color getEffectColor(Card card) {
        if (card instanceof SkipCard) return Color.RED;
        if (card instanceof ReverseCard) return Color.BLUE;
        if (card instanceof DrawTwoCard) return Color.YELLOW;
        if (card instanceof WildDrawFourCard) return Color.BLACK;
        return Color.WHITE;
    }

    private void updateCurrentPlayerHand(Player player) {
        // Get the container for the current player's hand
        CustomPanel handContainer = (CustomPanel) currentPlayerPanel.getComponent(1);
        handContainer.removeAll();
        
        currentPlayerLabel.setText(player.getName() + "'s HAND");
        
        if (player.isHuman()) {
            displayPlayerCards(handContainer, player.getHand());
        } else {
            // If it's a bot player, show card backs
            displayCardBacks(handContainer, player.getHandSize());
            appendToGameLog("Waiting for " + player.getName() + " to play...");
        }
        
        handContainer.revalidate();
        handContainer.repaint();
    }
    
    private void displayPlayerCards(CustomPanel container, List<Card> cards) {
        int cardCount = cards.size();
        Dimension newSize;

        // Choose proper size based on number of cards
        if (cardCount <= 7) {
            newSize = new Dimension(80, 120); // Normal size
        } else if (cardCount <= 12) {
            newSize = new Dimension(70, 105); // Medium size
        } else {
            newSize = new Dimension(60, 90); // Small size
        }

        for (Card card : cards) {
            UnoCardComponent cardComponent = new UnoCardComponent(card);
            cardComponent.setPreferredSize(newSize);
            boolean isPlayable = controller != null && controller.canPlayCard(card);

            if (isPlayable) {
                cardComponent.setBorder(new LineBorder(Color.GREEN, 2));
                cardComponent.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                cardComponent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            cardComponent.addMouseListener(new CardMouseListener(card));
            container.add(cardComponent);
        }
    }
    
    private void displayCardBacks(CustomPanel container, int count) {
        for (int i = 0; i < count; i++) {
            UnoCardComponent cardBack = new UnoCardComponent("/cards/back.png");
            cardBack.setPreferredSize(new Dimension(60, 90));
            container.add(cardBack);
        }
    }

    private class CardMouseListener extends MouseAdapter {
        private final Card card;
        private Point originalLocation;
        
        public CardMouseListener(Card card) {
            this.card = card;
        }
        
        public void mouseClicked(MouseEvent e) {
            if (controller != null && controller.canPlayCard(card)) {
                if (card instanceof WildCard) {
                    handleWildCard((WildCard) card);
                }
                controller.playCard(card);
               
            } else {
                Toolkit.getDefaultToolkit().beep();
                appendToGameLog("You cannot play this card right now.");
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            UnoCardComponent component = (UnoCardComponent) e.getSource();
            originalLocation = component.getLocation();
            component.setLocation(originalLocation.x, originalLocation.y - 20);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            if (originalLocation != null) {
                UnoCardComponent component = (UnoCardComponent) e.getSource();
                component.setLocation(originalLocation);
            }
        }
    }

    private void handleWildCard(WildCard wildCard) {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        
        CustomPanel dialogPanel = new CustomPanel(new BorderLayout());
        CustomLabel messageLabel = new CustomLabel("Choose a color for the Wild card:")
            .setAlignment("center")
            .setBold(true)
            .setFontSize(14);
        
        CustomPanel buttonPanel = new CustomPanel(new GridLayout(2, 2, 10, 10));
        
        // Create color selection dialog using custom components
        JDialog colorDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Color Selection", true);
        colorDialog.setLayout(new BorderLayout());
        
        // Create a button for each color
        CustomButton redButton = new CustomButton("Red", Color.RED, Color.WHITE);
        CustomButton blueButton = new CustomButton("Blue", Color.BLUE, Color.WHITE);
        CustomButton greenButton = new CustomButton("Green", Color.GREEN, Color.WHITE);
        CustomButton yellowButton = new CustomButton("Yellow", Color.YELLOW, Color.BLACK);
        
        redButton.addCustomActionListener(e -> {
            wildCard.setColor("Red");
            colorDialog.dispose();
            appendToGameLog("Selected color: Red");
        });
        
        blueButton.addCustomActionListener(e -> {
            wildCard.setColor("Blue");
            colorDialog.dispose();
            appendToGameLog("Selected color: Blue");
        });
        
        greenButton.addCustomActionListener(e -> {
            wildCard.setColor("Green");
            colorDialog.dispose();
            appendToGameLog("Selected color: Green");
        });
        
        yellowButton.addCustomActionListener(e -> {
            wildCard.setColor("Yellow");
            colorDialog.dispose();
            appendToGameLog("Selected color: Yellow");
        });
        
        buttonPanel.add(redButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(yellowButton);
        
        dialogPanel.add(messageLabel, BorderLayout.NORTH);
        dialogPanel.add(buttonPanel, BorderLayout.CENTER);
        
        colorDialog.add(dialogPanel);
        colorDialog.pack();
        colorDialog.setLocationRelativeTo(this);
        colorDialog.setVisible(true);
    }

    private void updateGameInfo(Player currentPlayer, Card topCard) {
        String turnInfo = currentPlayer.isHuman() ? "YOUR TURN" : currentPlayer.getName() + "'S TURN";
        infoLabel.setText(turnInfo);
        
        if (topCard instanceof WildCard) {
            currentColorLabel.setText("Current color: " + ((WildCard) topCard).getColor());
            currentColorLabel.setForeground(getColorFromString(((WildCard) topCard).getColor()));
        } else if (topCard != null) {
            currentColorLabel.setText("Current color: " + topCard.getColor());
            currentColorLabel.setForeground(getColorFromString(topCard.getColor()));
        } else {
            currentColorLabel.setText("");
        }
        
      // Update direction information
      boolean isClockwise = controller.isClockwise();
      direction.setText("Direction: " + (isClockwise ? "→ Player 1 to last" : "last player to first"));
    }
    
    private void updateControlButtons(Player currentPlayer) {
        boolean isHumanTurn = currentPlayer.isHuman();
        
        unoButton.setEnabled(isHumanTurn && currentPlayer.getHandSize() == 1);
        // Réactiver le bouton de pioche au début du tour du joueur
        drawCardButton.setEnabled(isHumanTurn && controller.canDrawCard());
     
    }

    private Color getColorFromString(String color) {
        if (color == null) return Color.WHITE;
        
        switch(color.toLowerCase()) {
            case "red": return Color.RED;
            case "blue": return Color.BLUE;
            case "green": return Color.GREEN;
            case "yellow": return Color.YELLOW;
            default: return Color.WHITE;
        }
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    // Method to handle player switching for multiple human players
    public void switchToNextHumanPlayer(Player newPlayer) {
        appendToGameLog("Switching to " + newPlayer.getName() + "'s turn");
        updateCurrentPlayerHand(newPlayer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Redraw elements with new size
        rescaleComponents();
    }

    private void rescaleComponents() {
        int width = getWidth();
        int height = getHeight();

        // Adjust sizes based on window dimensions
        if (width > 1000 || height > 700) {
            deckComponent.setPreferredSize(new Dimension(100, 150));
            discardPileComponent.setPreferredSize(new Dimension(100, 150));
            // Adjust other components...
        } else {
            deckComponent.setPreferredSize(new Dimension(80, 120));
            discardPileComponent.setPreferredSize(new Dimension(80, 120));
        }

        revalidate();
        repaint();
    }
}