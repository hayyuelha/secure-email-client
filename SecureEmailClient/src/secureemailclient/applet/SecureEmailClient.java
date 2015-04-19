/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureemailclient.applet;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Toshiba
 */
public class SecureEmailClient {
    public static String APP_NAME = "Joker";
    public static String APP_SLOGAN = "It's not about cryptography. It's about sending a message";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Set the look and feel to users OS LaF.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AuthFrame().setVisible(true);
            }
        });

    }

}
