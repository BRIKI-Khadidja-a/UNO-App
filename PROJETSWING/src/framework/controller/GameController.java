package framework.controller;

import framework.modal.*;
import framework.view.GameScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class GameController {
    private Game game;
    private GameScreen gameScreen;
    private Player currentPlayer;
    private List<Player> players;
    private List<Player> opponents;
  
    private boolean gameStarted;
    private Timer botTimer;
    boolean cardJustDrawn = true;
    Card lastDrawnCard = null;
    
    
    public GameController() {
        this.players = new ArrayList<>();
        this.opponents = new ArrayList<>();
       
        this.gameStarted = false;
        
        // Initialise un Timer sans action pour le moment 
        this.botTimer = new Timer(1000, e -> {
            // Action par défaut ( ne fait rien)
        });
        this.botTimer.setRepeats(false);
    }

    
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
    
    public void initializeGame(int numHumanPlayers, int numBotPlayers, List<String> playerNames) {
        // créer players
        players.clear();
        opponents.clear();
        
        // ajouter human players
        for (int i = 0; i < numHumanPlayers; i++) {
            String name = (playerNames != null && i < playerNames.size()) ? 
                          playerNames.get(i) : "Player " + (i + 1);
            HumanPlayer humanPlayer = new HumanPlayer(name);
            players.add(humanPlayer);
        }
        
        // ajouter bot players
        for (int i = 0; i < numBotPlayers; i++) {
            BotPlayer botPlayer = new BotPlayer("Bot " + (i + 1));
            players.add(botPlayer);
        }
        
        // Initializer game
        game = new Game(players);
        game.initialize();
        
        // Set current player et les oppenents
        currentPlayer = players.get(0);
        updateOpponentsList();
        
        gameStarted = true;
        
        // mise a jour de UI
        if (gameScreen != null) {
            gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
            gameScreen.appendToGameLog("Game started! " + currentPlayer.getName() + " goes first.");
        }
        
        //si le premier joueur un bot, make it play
        if (!currentPlayer.isHuman()) {
            scheduleBotTurn();
        }
    }
    
    
    private void updateOpponentsList() {
        opponents.clear();
        for (Player player : players) {
            if (player != currentPlayer) {
                opponents.add(player);
            }
        }
    }
    
   
    public void drawCard() {
        if (!gameStarted || !isCurrentPlayerHuman()) {
            return;
        }
        
        Card card = game.getDeck().drawCard();
        if (card != null) {
            currentPlayer.getHand().add(card);
            
            // Marquer que la carte vient d'être piochée
            cardJustDrawn = true;
            lastDrawnCard = card;
            
            if (gameScreen != null) {
                gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                gameScreen.appendToGameLog(currentPlayer.getName() + " drew a card.");
                
                // Vérifier si la carte piochée est jouable
                boolean canPlayDrawnCard = card.canPlayOn(game.getTopCard());
                if (!canPlayDrawnCard) {
                    gameScreen.appendToGameLog(currentPlayer.getName() + " cannot play the drawn card. Turn passes.");
                    
                    // Réinitialiser l'état
                    cardJustDrawn = false;
                    lastDrawnCard = null;

                    // Utiliser Swing Timer correctement
                    Timer timer = new Timer(1000, e -> nextPlayer());
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    gameScreen.appendToGameLog("The drawn card can be played! Click on it to play or click 'Pass' to end your turn.");
                    // Ne pas passer automatiquement au joueur suivant
                    // Le joueur doit décider s'il veut jouer la carte piochée ou passer
                }
            }
        }
    }

   
    public void playCard(Card card) {
        if (!gameStarted || !isCurrentPlayerHuman()) {
            return;
        }
        
        // verifier si la cartes est jouable
        if (canPlayCard(card)) {
            
            boolean wasJustDrawn = cardJustDrawn && card == lastDrawnCard;
            currentPlayer.getHand().remove(card);
            
            // Ajouter a discard pile et mise a jr de top card
            game.playCard(card);
            
            // Reset card drawing state
            cardJustDrawn = false;
            lastDrawnCard = null;
            
            // mise a jr de UI
            if (gameScreen != null) {
                gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                gameScreen.appendToGameLog(currentPlayer.getName() + " played " + card.toString());
                
                // vérification du win condition
                if (currentPlayer.hasWon()) {
                    endGame();
                    return;
                }
                
                // les effets de cartes spéciales
                if (card instanceof SkipCard) {
                    handleSkipCard();
                    return;
                } else if (card instanceof ReverseCard) {
                    nextPlayer();
                    return;
                } else if (card instanceof DrawTwoCard) {
                    handleDrawTwoCard();
                    return;
                } else if (card instanceof WildDrawFourCard) {
                    handleWildDrawFourCard();
                    return;
                }
            }
            
            // pour les cartes normales ,just move to next player
            nextPlayer();
        }
    }
    
    
    private void handleSkipCard() {
        if (gameScreen != null) {
            gameScreen.appendToGameLog("Skipping next player's turn!");
        }
        
        // Skip the next player in line
        game.nextPlayer(); // Move to the player being skipped
        currentPlayer = game.getCurrentPlayer();
        updateOpponentsList();
        
        if (gameScreen != null) {
            gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
            gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
            
            // If the next player is a bot, schedule its turn
            if (!currentPlayer.isHuman()) {
                scheduleBotTurn();
            }
        }
    }

   
    private void handleDrawTwoCard() {
        Player nextPlayer = game.getNextPlayer();
        
        // Make the next player draw 2 cards
        for (int i = 0; i < 2; i++) {
            Card drawnCard = game.getDeck().drawCard();
            if (drawnCard != null) {
                nextPlayer.getHand().add(drawnCard);
            }
        }
        
        if (gameScreen != null) {
            gameScreen.appendToGameLog(nextPlayer.getName() + " draws 2 cards and misses a turn!");
        }
        
        // Skip the affected player
        game.nextPlayer(); // Move to the player who drew cards
        currentPlayer = game.getCurrentPlayer();
        updateOpponentsList();
        
        if (gameScreen != null) {
            gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
            gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
            
            // If the next player is a bot, schedule its turn
            if (!currentPlayer.isHuman()) {
                scheduleBotTurn();
            }
        }
    }

   
    private void handleWildDrawFourCard() {
        Player nextPlayer = game.getNextPlayer();
        
        // Make the next player draw 4 cards
        for (int i = 0; i < 4; i++) {
            Card drawnCard = game.getDeck().drawCard();
            if (drawnCard != null) {
                nextPlayer.getHand().add(drawnCard);
            }
        }
        
        if (gameScreen != null) {
            gameScreen.appendToGameLog(nextPlayer.getName() + " draws 4 cards and misses a turn!");
        }
        
        // Skip the affected player
        game.nextPlayer(); // Move to the player who drew cards
       
        currentPlayer = game.getCurrentPlayer();
        updateOpponentsList();
        
        if (gameScreen != null) {
            gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
            gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
            
            // If the next player is a bot, schedule its turn
            if (!currentPlayer.isHuman()) {
                scheduleBotTurn();
            }
        }
    }
    
    
    public void nextPlayer() {
        if (!gameStarted) {
            return;
        }
        
        game.nextPlayer();
        currentPlayer = game.getCurrentPlayer();
        updateOpponentsList();
        
        if (gameScreen != null) {
            gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
            gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
            
            // If the next player is a bot, schedule its turn
            if (!currentPlayer.isHuman()) {
                scheduleBotTurn();
            }
        }
    }
    
    
    private void scheduleBotTurn() {
        Timer botTimer = new Timer(1500, e -> {
            playBotTurn();
        });
        botTimer.setRepeats(false); // Le bot joue une seule fois après 1.5 sec
        botTimer.start();
    }

   
    private void playBotTurn() {
        if (!gameStarted || isCurrentPlayerHuman()) {
            return;
        }
        
        BotPlayer botPlayer = (BotPlayer) currentPlayer;
        Card selectedCard = botPlayer.chooseBestCard(game);
        
        if (selectedCard != null) {
            // Jouer la carte sélectionnée
            if (selectedCard instanceof WildCard) {
                // Bot selects a random color
                String[] colors = {"Red", "Blue", "Green", "Yellow"};
                String chosenColor = colors[new Random().nextInt(colors.length)];
                ((WildCard) selectedCard).setColor(chosenColor);
                
                if (gameScreen != null) {
                    gameScreen.appendToGameLog(botPlayer.getName() + " chose " + chosenColor);
                }
            }
            
            // supprimer  la carte de la main
            botPlayer.getHand().remove(selectedCard);
            
            // jouer la carte
            game.playCard(selectedCard);
            
            if (gameScreen != null) {
                gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                gameScreen.appendToGameLog(botPlayer.getName() + " played " + selectedCard);
                
                // Check for win condition
                if (botPlayer.hasWon()) {
                    endGame();
                    return;
                }
                
                // Handle special cards effects for bot
                if (selectedCard instanceof SkipCard) {
                    if (gameScreen != null) {
                        gameScreen.appendToGameLog("Skipping next player's turn!");
                    }
                    
                    // Skip the next player in line
                    game.nextPlayer(); // Move to the player being skipped
                    game.nextPlayer(); // Move to the player after the skipped one
                    currentPlayer = game.getCurrentPlayer();
                    updateOpponentsList();
                    
                    if (gameScreen != null) {
                        gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                        gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
                        
                        // If the next player is a bot, schedule its turn
                        if (!currentPlayer.isHuman()) {
                            scheduleBotTurn();
                        }
                    }
                    return; // Skip the nextPlayer() call at the end
                } else if (selectedCard instanceof DrawTwoCard) {
                    Player nextPlayer = game.getNextPlayer();
                    
                    // Make the next player draw 2 cards
                    for (int i = 0; i < 2; i++) {
                        Card drawnCard = game.getDeck().drawCard();
                        if (drawnCard != null) {
                            nextPlayer.getHand().add(drawnCard);
                        }
                    }
                    
                    if (gameScreen != null) {
                        gameScreen.appendToGameLog(nextPlayer.getName() + " draws 2 cards and misses a turn!");
                    }
                    
                    // Skip the affected player
                    game.nextPlayer(); // Move to the player who drew cards
                    game.nextPlayer(); // Move to the player after them
                    currentPlayer = game.getCurrentPlayer();
                    updateOpponentsList();
                    
                    if (gameScreen != null) {
                        gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                        gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
                        
                        // If the next player is a bot, schedule its turn
                        if (!currentPlayer.isHuman()) {
                            scheduleBotTurn();
                        }
                    }
                    return; // Skip the nextPlayer() call at the end
                } else if (selectedCard instanceof WildDrawFourCard) {
                    Player nextPlayer = game.getNextPlayer();
                    
                    // Make the next player draw 4 cards
                    for (int i = 0; i < 4; i++) {
                        Card drawnCard = game.getDeck().drawCard();
                        if (drawnCard != null) {
                            nextPlayer.getHand().add(drawnCard);
                        }
                    }
                    
                    if (gameScreen != null) {
                        gameScreen.appendToGameLog(nextPlayer.getName() + " draws 4 cards and misses a turn!");
                    }
                    
                    // Skip the affected player
                    game.nextPlayer(); // Move to the player who drew cards
                    game.nextPlayer(); // Move to the player after them
                    currentPlayer = game.getCurrentPlayer();
                    updateOpponentsList();
                    
                    if (gameScreen != null) {
                        gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                        gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
                        
                        // If the next player is a bot, schedule its turn
                        if (!currentPlayer.isHuman()) {
                            scheduleBotTurn();
                        }
                    }
                    return; // Skip the nextPlayer() call at the end
                } else if (selectedCard instanceof ReverseCard) {
                    // Reverse effect is already handled in game.playCard()
                    nextPlayer();
                    return;
                }
            }
        } else {
            // Draw a card
            Card drawnCard = game.getDeck().drawCard();
            if (drawnCard != null) {
                botPlayer.getHand().add(drawnCard);
                
                if (gameScreen != null) {
                    gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                    gameScreen.appendToGameLog(botPlayer.getName() + " drew a card.");
                    
                    // Check if the drawn card can be played
                    if (drawnCard.canPlayOn(game.getTopCard())) {
                        // If yes, play the card
                        botPlayer.getHand().remove(drawnCard);
                        game.playCard(drawnCard);
                        
                        if (gameScreen != null) {
                            gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                            gameScreen.appendToGameLog(botPlayer.getName() + " played the drawn card: " + drawnCard);
                            
                            if (botPlayer.hasWon()) {
                                endGame();
                                return;
                            }
                            
                            // Handle special cards effects for drawn cards
                            if (drawnCard instanceof SkipCard) {
                                if (gameScreen != null) {
                                    gameScreen.appendToGameLog("Skipping next player's turn!");
                                }
                                
                                // Skip the next player in line
                                game.nextPlayer(); // Move to the player being skipped
                                game.nextPlayer(); // Move to the player after the skipped one
                                currentPlayer = game.getCurrentPlayer();
                                updateOpponentsList();
                                
                                if (gameScreen != null) {
                                    gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                                    gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
                                    
                                    // If the next player is a bot, schedule its turn
                                    if (!currentPlayer.isHuman()) {
                                        scheduleBotTurn();
                                    }
                                }
                                return; // Skip the nextPlayer() call at the end
                            } else if (drawnCard instanceof DrawTwoCard) {
                                Player nextPlayer = game.getNextPlayer();
                                
                                // Make the next player draw 2 cards
                                for (int i = 0; i < 2; i++) {
                                    Card drawn = game.getDeck().drawCard();
                                    if (drawn != null) {
                                        nextPlayer.getHand().add(drawn);
                                    }
                                }
                                
                                if (gameScreen != null) {
                                    gameScreen.appendToGameLog(nextPlayer.getName() + " draws 2 cards and misses a turn!");
                                }
                                
                                // Skip the affected player
                                game.nextPlayer(); // Move to the player who drew cards
                                game.nextPlayer(); // Move to the player after them
                                currentPlayer = game.getCurrentPlayer();
                                updateOpponentsList();
                                
                                if (gameScreen != null) {
                                    gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                                    gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
                                    
                                    // If the next player is a bot, schedule its turn
                                    if (!currentPlayer.isHuman()) {
                                        scheduleBotTurn();
                                    }
                                }
                                return; // Skip the nextPlayer() call at the end
                            } else if (drawnCard instanceof WildDrawFourCard) {
                                Player nextPlayer = game.getNextPlayer();
                                
                                // Make the next player draw 4 cards
                                for (int i = 0; i < 4; i++) {
                                    Card drawn = game.getDeck().drawCard();
                                    if (drawn != null) {
                                        nextPlayer.getHand().add(drawn);
                                    }
                                }
                                
                                if (gameScreen != null) {
                                    gameScreen.appendToGameLog(nextPlayer.getName() + " draws 4 cards and misses a turn!");
                                }
                                
                                // Skip the affected player
                                game.nextPlayer(); // Move to the player who drew cards
                                game.nextPlayer(); // Move to the player after them
                                currentPlayer = game.getCurrentPlayer();
                                updateOpponentsList();
                                
                                if (gameScreen != null) {
                                    gameScreen.updateGameBoard(currentPlayer, opponents, game.getTopCard());
                                    gameScreen.appendToGameLog("Now it's " + currentPlayer.getName() + "'s turn.");
                                    
                                    // If the next player is a bot, schedule its turn
                                    if (!currentPlayer.isHuman()) {
                                        scheduleBotTurn();
                                    }
                                }
                                return; // Skip the nextPlayer() call at the end
                            } else if (drawnCard instanceof ReverseCard) {
                                // Reverse effect is already handled in game.playCard()
                                nextPlayer();
                                return;
                            }
                        }
                    } else {
                        // If the card cannot be played, add a message
                        gameScreen.appendToGameLog(botPlayer.getName() + " cannot play the drawn card. Turn passes.");
                    }
                }
            }
        }
        
        // Move to the next player
        nextPlayer();
    }
    
    
    public void callUno() {
        if (!gameStarted || !isCurrentPlayerHuman()) {
            return;
        }
        
        if (currentPlayer.getHand().size() == 1) {
            if (gameScreen != null) {
                gameScreen.appendToGameLog(currentPlayer.getName() + " called UNO!");
            }
        } else {
            if (gameScreen != null) {
                gameScreen.appendToGameLog("You must have exactly 1 card to call UNO!");
            }
        }
    }
    
   
    private void endGame() {
        gameStarted = false;
        
        if (gameScreen != null) {
            gameScreen.appendToGameLog("*****************************************");
            gameScreen.appendToGameLog("🎉 " + currentPlayer.getName() + " WON THE GAME! 🎉");
            gameScreen.appendToGameLog("*****************************************");
        }
        
        // Arrêter proprement le timer Swing
        if (botTimer != null && botTimer.isRunning()) {
            botTimer.stop();
        }
    }

    
    public boolean canPlayCard(Card card) {
        return card != null && game.getTopCard() != null && card.canPlayOn(game.getTopCard());
    }
   
    public boolean canDrawCard() {
        return gameStarted && isCurrentPlayerHuman();
    }
    
    
    public boolean isCurrentPlayerHuman() {
        return currentPlayer != null && currentPlayer.isHuman();
    }
    
    
    // Getters
    
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    
    public List<Player> getOpponents() {
        return opponents;
    }
   
    public Card getTopCard() {
        return game != null ? game.getTopCard() : null;
    }
    
  
    /**
     * Gets the current player's name.
     * 
     * @return The current player's name
     */
    public String getCurrentPlayerName() {
        return currentPlayer != null ? currentPlayer.getName() : "";
    }
    
    
    public boolean isGameStarted() {
        return gameStarted;
    } 
   
    public void passTurn() {
        if (!gameStarted || !isCurrentPlayerHuman()) {
            return;
        }

        // Réinitialiser l'état de pioche
        cardJustDrawn = false;
        lastDrawnCard = null;

        if (gameScreen != null) {
            gameScreen.appendToGameLog(currentPlayer.getName() + " passes their turn.");
        }

        // Passer au joueur suivant
        nextPlayer();
    }
    public boolean isClockwise() {
        return game != null && game.getDirection() == 1;
    }
}