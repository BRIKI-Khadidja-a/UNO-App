package framework.components;

import framework.modal.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.IOException;

public class UnoCardComponent extends CustomPanel {
    private static final String IMAGE_PATH = "cards/";
    private static final Dimension CARD_SIZE = new Dimension(80, 120);

    private Card card;
    private BufferedImage cardImage;
    private boolean playable;

    
    public UnoCardComponent(Card card) {
        this.card = card;
        initializeComponent();
        loadCardImage();
    }

    
    public UnoCardComponent(String imagePath) {
        this.card = null; 
        initializeComponent();
        loadCustomImage(imagePath);
    }

    private void initializeComponent() {
        setOpaque(false);
        setPreferredSize(CARD_SIZE);
        setLayout(new BorderLayout());
    }

    private void loadCustomImage(String imagePath) {
        try {
            String normalizedPath = imagePath.startsWith("/") ? imagePath : "/" + imagePath;
            InputStream is = getClass().getResourceAsStream(normalizedPath);
            if (is != null) {
                cardImage = ImageIO.read(is);
                is.close();
            } else {
                System.err.println("Image not found: " + normalizedPath);
                cardImage = null;
            }
        } catch (IOException e) {
            System.err.println("Error loading custom image: " + e.getMessage());
            cardImage = null;
        }
    }

    private void loadCardImage() {
        try {
            String imagePath = getImagePathForCard();
            loadImageFromResources(imagePath);
        } catch (Exception e) {
            System.err.println("Error loading card image: " + e.getMessage());
            cardImage = null;
        }
    }

    private String getImagePathForCard() {
        if (card == null) return "/" + IMAGE_PATH + "back.png";

        String color = capitalize(card.getColor());
        String type = card.getType().toLowerCase();

        if (card instanceof NumberCard) {
            int number = ((NumberCard) card).getNumber();
            return "/" + IMAGE_PATH + color + "_" + number + ".jpg";
        }

        // Gestion spéciale des cartes d'action
        if (type.contains("draw")) {
            if (type.contains("four")) {
                return "/" + IMAGE_PATH + "Wild_draw_4.jpg";
            } else {
                return "/" + IMAGE_PATH + color + "_draw_2.jpg";
            }
        }

        if (type.contains("skip")) {
            return "/" + IMAGE_PATH + color + "_skip.jpg";
        }

        if (type.contains("reverse")) {
            return "/" + IMAGE_PATH + color + "_reverse.jpg";
        }

        if (color.equalsIgnoreCase("wild")) {
            return "/" + IMAGE_PATH + "Wild.jpg";
        }

        // Par défaut
        return "/" + IMAGE_PATH + color + "_" + type + ".jpg";
    }



    private void loadImageFromResources(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is != null) {
            try {
                cardImage = ImageIO.read(is);
            } finally {
                is.close();
            }
        } else {
            throw new IOException("Image not found at: " + path);
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawCardBackground(g2d);
        drawCardImage(g2d);
        drawCardBorder(g2d);
    }

    private void drawCardBackground(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }

    private void drawCardImage(Graphics2D g2d) {
        if (cardImage != null) {
            g2d.drawImage(cardImage, 10, 10, getWidth()-20, getHeight()-20, null);
        } else {
            drawFallbackText(g2d);
        }
    }

    private void drawCardBorder(Graphics2D g2d) {
        g2d.setColor(playable ? new Color(0, 200, 0) : Color.BLACK);
        g2d.setStroke(new BasicStroke(playable ? 4 : 2));
        g2d.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, 15, 15);
    }

    private void drawFallbackText(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        if (card != null) {
            g2d.drawString(card.toString(), 20, getHeight()/2);
        } else {
            g2d.drawString("UNO", getWidth()/2-20, getHeight()/2);
        }
    }

    // Getters/Setters
    public void setPlayable(boolean playable) {
        this.playable = playable;
        repaint();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card newCard) {
        this.card = newCard;
        loadCardImage();
        repaint();
    }
    // changer la taille d'une carte après sa création.
    public void resizeCard(Dimension newSize) {
        setPreferredSize(newSize);
        revalidate();
        repaint();
    }

}
