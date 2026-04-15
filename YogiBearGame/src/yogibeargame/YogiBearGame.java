/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package yogibeargame;

import javax.swing.SwingUtilities;

/**
 *
 * @author jamesmadoc
 
 * Creates and displays the game window.
 * */
public class YogiBearGame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameWindow window = new GameWindow();
                window.setVisible(true);
            }
        });
    
        
    }
    
}
