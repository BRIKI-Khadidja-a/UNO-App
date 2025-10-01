# UNO Card Game - Java Swing Implementation

A complete implementation of the classic UNO card game built with Java Swing, featuring a custom GUI framework and modern game mechanics.

## 🎮 Game Features

- **Complete UNO Game Logic**: Full implementation of official UNO rules
- **Custom GUI Framework**: Custom-built components extending Java Swing
- **Multiple Player Support**: Play against AI bots or with human players
- **Beautiful Interface**: Modern, intuitive user interface with custom styling
- **Card Animations**: Visual card representations with proper UNO card designs
- **Smart AI**: Intelligent bot players with strategic decision-making

## 🎯 Game Rules

The game follows standard UNO rules:
- Players start with 7 cards each
- Match the top card by color, number, or symbol
- Special cards: Skip, Reverse, Draw Two, Wild, Wild Draw Four
- First player to empty their hand wins
- Say "UNO" when you have one card left!

## 🏗️ Project Structure

```
PROJETSWING/
├── src/
│   ├── executer/
│   │   └── UnoGame.java              # Main entry point
│   ├── framework/
│   │   ├── components/               # Custom GUI components
│   │   │   ├── CustomButton.java
│   │   │   ├── CustomFrame.java
│   │   │   ├── CustomLabel.java
│   │   │   ├── CustomPanel.java
│   │   │   ├── CustomTextField.java
│   │   │   ├── CustomRadioButton.java
│   │   │   ├── CustomButtonGroup.java
│   │   │   ├── CustomOptionPane.java
│   │   │   └── UnoCardComponent.java
│   │   ├── controller/               # Game logic controllers
│   │   │   ├── GameController.java
│   │   │   └── NavigationController.java
│   │   ├── modal/                    # Game model classes
│   │   │   ├── Card.java
│   │   │   ├── Deck.java
│   │   │   ├── Game.java
│   │   │   ├── Player.java
│   │   │   ├── HumanPlayer.java
│   │   │   ├── BotPlayer.java
│   │   │   ├── NumberCard.java
│   │   │   ├── ActionCard.java
│   │   │   ├── WildCard.java
│   │   │   ├── SkipCard.java
│   │   │   ├── ReverseCard.java
│   │   │   ├── DrawTwoCard.java
│   │   │   └── WildDrawFourCard.java
│   │   ├── view/                     # User interface screens
│   │   │   ├── UnoMainPage.java
│   │   │   ├── PlayerSelectionScreen.java
│   │   │   ├── NameEntryScreen.java
│   │   │   ├── BotSelectionScreen.java
│   │   │   └── GameScreen.java
│   │   └── style/                    # UI styling and themes
│   │       ├── ColorPalette.java
│   │       └── Fonts.java
│   └── cards/                        # Card image assets
└── resources/
    └── images/                       # UI images and icons
```

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

### Running the Game

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd UNO1
   ```

2. **Compile the project**
   ```bash
   javac -d . PROJETSWING/src/**/*.java
   ```

3. **Run the game**
   ```bash
   java executer.UnoGame
   ```

### Alternative: Using an IDE

1. Open the project in your preferred Java IDE
2. Navigate to `PROJETSWING/src/executer/UnoGame.java`
3. Run the `main` method

## 🎨 Custom GUI Framework

This project implements a custom GUI framework built on top of Java Swing, featuring:

- **Custom Components**: All UI elements are custom-built for consistent styling
- **Theme System**: Centralized color palette and font management
- **Responsive Design**: Components adapt to different screen sizes
- **Modern UI**: Clean, intuitive interface design

### Key Framework Components

- `CustomFrame`: Main application window with custom styling
- `CustomPanel`: Enhanced panel with built-in styling options
- `CustomButton`: Styled buttons with hover effects and icons
- `CustomLabel`: Text labels with custom fonts and colors
- `UnoCardComponent`: Specialized component for displaying UNO cards

## 🎲 Game Mechanics

### Card Types

1. **Number Cards (0-9)**: Basic matching cards in four colors
2. **Skip Cards**: Skip the next player's turn
3. **Reverse Cards**: Reverse the direction of play
4. **Draw Two Cards**: Next player draws 2 cards and skips turn
5. **Wild Cards**: Change the current color
6. **Wild Draw Four Cards**: Change color and next player draws 4 cards

### AI Behavior

The bot players use intelligent strategies:
- Prioritize playing cards that match the current color
- Save powerful cards (Wild Draw Four) for strategic moments
- Try to empty their hand quickly while blocking opponents

## 🛠️ Technical Details

- **Architecture**: Model-View-Controller (MVC) pattern
- **GUI Framework**: Custom components extending Java Swing
- **Game Logic**: Object-oriented design with proper inheritance
- **Image Handling**: Custom card rendering with PNG image assets
- **Event Handling**: Swing event system for user interactions

## 🎯 Features in Detail

### Player Management
- Support for 2-10 players (mix of humans and bots)
- Custom player names
- Player statistics tracking

### Game Flow
- Intuitive navigation between screens
- Real-time game state updates
- Visual feedback for all player actions
- Automatic game progression

### Visual Design
- Authentic UNO card designs
- Smooth animations and transitions
- Responsive layout system
- Consistent color scheme

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🎮 How to Play

1. **Start the Game**: Launch `UnoGame.java`
2. **Select Players**: Choose number of human and bot players
3. **Enter Names**: Provide names for human players
4. **Play**: Click on cards to play them, or click "Draw Card" if no valid moves
5. **Win**: Be the first to empty your hand and say "UNO"!

## 🐛 Known Issues

- Game is currently optimized for single-screen play
- Some advanced UNO variants are not implemented

## 🔮 Future Enhancements

- [ ] Network multiplayer support
- [ ] Sound effects and background music
- [ ] Tournament mode
- [ ] Custom card designs
- [ ] Statistics tracking
- [ ] Mobile version

---

**Enjoy playing UNO!** 🎉

For questions or support, please open an issue on GitHub.