package framework.modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private int direction;
    private Scanner scanner;
 
    public Game(int numHumanPlayers, int numBotPlayers, String[] playerNames, Scanner scanner) {
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.currentPlayerIndex = 0;
        this.direction = 1;
        this.scanner = scanner;
 
        // Initialize players
        for (int i = 0; i < numHumanPlayers; i++) {
            String name = (playerNames != null && i < playerNames.length) ? 
                          playerNames[i] : 
                          "Player " + (i + 1);
            players.add(new HumanPlayer(name));
        }
 
        for (int i = 0; i < numBotPlayers; i++) {
            players.add(new BotPlayer("Bot " + (i + 1)));
        }
    }
 
    public void initialize() {
        // Deal initial cards
        for (Player player : players) {
            for (int i = 0; i < Deck.INITIAL_HAND_SIZE; i++) {
                player.drawCard(deck);
            }
        }

        // Place first card  , only number cards allowed
        Card firstCard;
        do {
            firstCard = deck.drawCard();
            // If it's not a number card, add it back to the bottom of the deck
            if (!(firstCard instanceof NumberCard)) {
                deck.addToBottom(firstCard);
            }
        } while (!(firstCard instanceof NumberCard));

        deck.addToDiscardPile(firstCard);
    }
    public Game(List<Player> players) {
        this.players = new ArrayList<>(players); // Create a copy of the provided list
        this.deck = new Deck();
        this.currentPlayerIndex = 0;
        this.direction = 1;
       
    }
    
    // Add a method to get all opponents
    public List<Player> getOpponents() {
        List<Player> opponents = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (i != currentPlayerIndex) {
                opponents.add(players.get(i));
            }
        }
        return opponents;
    }
 
    public void start() {
        initialize();
 
        while (!isGameOver()) {
            displayGameStatus();
            playTurn();
            
            if (getCurrentPlayer().hasWon()) {
                System.out.println("\n " + getCurrentPlayer().getName() + " has won the game! ");
                break;
            }
            
            nextPlayer();
        }
    }

    private void playTurn() {
        Player currentPlayer = getCurrentPlayer();
        System.out.println("\n" + currentPlayer.getName() + "'s turn");
 
        if (currentPlayer instanceof HumanPlayer) {
            playHumanTurn((HumanPlayer) currentPlayer);
        } else {
            playBotTurn((BotPlayer) currentPlayer);
        }
    }
 
    private void playHumanTurn(HumanPlayer player) {
        player.displayHand();
        List<Card> validMoves = player.getValidMoves(getTopCard());
        if (validMoves.isEmpty()) {
            System.out.println("No valid moves. Drawing a card...");
            player.drawCard(deck);
            return;
        }
        System.out.println("Valid moves:");
        for (int i = 0; i < validMoves.size(); i++) {
            System.out.println((i + 1) + ". " + validMoves.get(i));
        }
        System.out.println((validMoves.size() + 1) + ". Draw a card");
 
        int choice = getValidInput(1, validMoves.size() + 1) - 1;
        if (choice < validMoves.size()) {
            Card selectedCard = validMoves.get(choice);
            if (selectedCard instanceof WildCard) {
                handleWildCard((WildCard) selectedCard);
            }
            player.playCard(selectedCard, this);
            System.out.println("Played: " + selectedCard);
        } else {
            player.drawCard(deck);
            System.out.println("Drew a card");
        }
    }
 
    private void playBotTurn(BotPlayer player) {
       Card selectedCard = player.chooseBestCard(this);
       if (selectedCard != null) {
           if (selectedCard instanceof WildCard) {
               // Randomly select a color for wild cards
             String[] colors = { "Red", "Blue", "Green", "Yellow" };
             ((WildCard) selectedCard).setColor(colors[(int) (Math.random() * colors.length)]);
          }
          player.playCard(selectedCard, this);
          System.out.println("=========================");
          System.out.println("Played: " + selectedCard);
       } else {
          player.drawCard(deck);
          System.out.println("Drew a card");
       }
    }
 
    private void handleWildCard(WildCard card) {
        System.out.println("Choose a color:");
        System.out.println("1. Red");
        System.out.println("2. Blue");
        System.out.println("3. Green");
        System.out.println("4. Yellow");
 
        int choice = getValidInput(1, 4);
        String color = "";
        switch (choice) {
            case 1: color = "Red"; break;
            case 2: color = "Blue"; break;
            case 3: color = "Green"; break;
            case 4: color = "Yellow"; break;
        }
        card.setColor(color);
    }

    public void displayGameStatus() {
        System.out.println("\n\n=== Game Status ===");
        System.out.println("Top Card: " + getTopCard());
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getHandSize() + " cards");
        }
        System.out.println("====================\n");
    }
 
    private int getValidInput(int min, int max) {
        int choice;
        do {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number!");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < min || choice > max);
        return choice;
    }
 
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
    }
 
    public void reverseDirection() {
        direction *= -1;
        if (players.size() == 2) {
            nextPlayer(); // Skip next player's turn in 2-player game
        }
    }
 
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
 
    public Player getNextPlayer() {
        int nextIndex = (currentPlayerIndex + direction + players.size()) % players.size();
        return players.get(nextIndex);
    }
 
    public Card getTopCard() {
        return deck.getTopCard();
    }

    public void playCard(Card card) {
        deck.addToDiscardPile(card);
        card.applyEffect(this);
    }

    public boolean isGameOver() {
       for (Player player : players) {
           if (player.hasWon()) {
               return true;
           }
       }
       return false;
    }
 
    public Deck getDeck() {
        return deck;
    }
    
    public List<Player> getPlayers() {
        return players;
    }
    
    public int getDirection() {
        return direction;
    }
}