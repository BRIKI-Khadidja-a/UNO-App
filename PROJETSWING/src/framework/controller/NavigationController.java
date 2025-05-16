package framework.controller;

import java.util.ArrayList;

import framework.components.CustomFrame;
import framework.view.*;

public class NavigationController {
    private final CustomFrame mainFrame;
    private final UnoMainPage mainMenu;
    private final PlayerSelectionScreen playerSelectionScreen;
    private final BotSelectionScreen botSelectionScreen;
    private final NameEntryScreen nameEntryScreen;
    private GameScreen gameScreen;
    private GameController gameController;

    public NavigationController(CustomFrame frame) {
        this.mainFrame = frame;

        // Initialize all screens
        this.mainMenu = new UnoMainPage();
        this.playerSelectionScreen = new PlayerSelectionScreen();
        this.botSelectionScreen = new BotSelectionScreen();
        this.nameEntryScreen = new NameEntryScreen();

        setupEventListeners();
        showMainMenu();
    }

    private void setupEventListeners() {
        // Main menu
        mainMenu.getPlayButton().addActionListener(e -> showPlayerSelection());

        // Player selection
        playerSelectionScreen.getBackButton().addActionListener(e -> showMainMenu());
        playerSelectionScreen.getNextButton().addActionListener(e -> showBotSelection());

        // Bot selection
        botSelectionScreen.getBackButton().addActionListener(e -> showPlayerSelection());
        botSelectionScreen.getNextButton().addActionListener(e -> showNameEntry());

        // Name entry
        nameEntryScreen.getBackButton().addActionListener(e -> showBotSelection());
        nameEntryScreen.getNextButton().addActionListener(e -> startGame());
    }

    // Navigation methods
    public void showMainMenu() {
        cleanupGameResources();
        mainFrame.setContentPanel(mainMenu);
    }

    public void showPlayerSelection() {
        mainFrame.setContentPanel(playerSelectionScreen);
    }

    public void showBotSelection() {
        int totalPlayers = playerSelectionScreen.getSelectedPlayerCount();
        botSelectionScreen.updateBotOptions(totalPlayers);
        mainFrame.setContentPanel(botSelectionScreen);
    }

    public void showNameEntry() {
        int humanPlayers = botSelectionScreen.getHumanPlayerCount();
        nameEntryScreen.updateForPlayerCount(humanPlayers);
        mainFrame.setContentPanel(nameEntryScreen);
    }

    // Game initialization
    public void startGame() {
        int humanPlayers = botSelectionScreen.getHumanPlayerCount();
        int botPlayers = botSelectionScreen.getSelectedBotCount();
        ArrayList<String> playerNames = new ArrayList<>();
        
        // Get player names from name entry screen
        for (String name : nameEntryScreen.getPlayerNames()) {
            if (name != null && !name.trim().isEmpty()) {
                playerNames.add(name);
            }
        }

        // Create game controller with default constructor
        gameController = new GameController();
        gameScreen = new GameScreen(gameController);
        
        // Establish bidirectional connection
        gameController.setGameScreen(gameScreen);
        
        // Set content and initialize game
        mainFrame.setContentPanel(gameScreen);
        
        // Initialize the game with the correct parameters
        gameController.initializeGame(humanPlayers, botPlayers, playerNames);
        
        // Log the game start
        gameScreen.appendToGameLog("Game started with " + humanPlayers + " human players and " + 
                                  botPlayers + " bot players");
    }

    // Cleanup resources
    private void cleanupGameResources() {
        if (gameController != null) {
            // Remove any event listeners to prevent memory leaks
            gameController = null;
        }
        gameScreen = null;
        System.gc(); // Request garbage collection
    }

    // Allow game screen to navigate back
    public void returnToMainMenu() {
        showMainMenu();
    }
}