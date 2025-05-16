package executer;

import framework.components.CustomFrame;
import framework.controller.NavigationController;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class UnoGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                //  Créer la frame principale
                CustomFrame mainFrame = new CustomFrame("UNO Game", 1200, 1000);
                mainFrame.setFrameIcon("resources/images/UNO_Logo.png");
                //  Créer et démarrer le NavigationController
                 new NavigationController(mainFrame);

                //  Afficher la frame
           
                mainFrame.display();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Erreur lors du démarrage du jeu:\n" + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}
