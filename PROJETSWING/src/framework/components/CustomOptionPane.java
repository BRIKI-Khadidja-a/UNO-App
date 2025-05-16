package framework.components ;
import java.awt.*;

import javax.swing.*;
public class CustomOptionPane extends JOptionPane {

    
    public CustomOptionPane() {
        super();
    }

    public static void showCustomMessage(String message, String title) {
        // Crée un message personnalisé avec un titre personnalisé
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        
        // Personnalisation de la fenêtre de dialogue
        JDialog dialog = pane.createDialog(title);
        
       
        dialog.getContentPane().setBackground(Color.CYAN);
      
        dialog.setVisible(true);
        
      
        for (Component comp : dialog.getContentPane().getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                
                button.setBackground(Color.BLUE);
                button.setForeground(Color.WHITE);
                button.setText("Ok"); 
            }
        }
    }

   
    public static String showCustomInputDialog(String message, String title) {
        String userInput = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
        
        
        return userInput;
    }

    
    public static int showCustomConfirmDialog(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }
}
