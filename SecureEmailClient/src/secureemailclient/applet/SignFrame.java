/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package secureemailclient.applet;

import secureemailclient.crypto.ecc.KeyPair;
import secureemailclient.crypto.ecc.PrivateKey;
import secureemailclient.crypto.ecc.ECC;
import secureemailclient.crypto.ecc.EllipticCurve;
import java.awt.Component;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Riady
 */
public class SignFrame extends javax.swing.JFrame {

    /**
     * Creates new form SignFrame
     */
    
    private PrivateKey pk;
    
    public SignFrame() {
        initComponents();
        
        setTitle(SecureEmailClient.APP_NAME + " - Sign Mail");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    
    public PrivateKey getPrivateKey(){
        return pk;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaPrivateKey = new javax.swing.JTextArea();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Generate Key Pair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Select Private Key");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Private Key");

        jTextAreaPrivateKey.setEditable(false);
        jTextAreaPrivateKey.setColumns(20);
        jTextAreaPrivateKey.setRows(5);
        jTextAreaPrivateKey.setText("None");
        jScrollPane1.setViewportView(jTextAreaPrivateKey);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton3))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         File file;
        JFileChooser jc = new JFileChooser();
        int returnVal = jc.showOpenDialog((Component)evt.getSource());
        if(returnVal == JFileChooser.APPROVE_OPTION){
            file = jc.getSelectedFile();
            pk = new PrivateKey(file.getAbsolutePath());
            jTextAreaPrivateKey.setText(pk.toString());
        }
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        EllipticCurve c = EllipticCurve.NIST_P_192;
        Random rnd = new Random(System.currentTimeMillis());
        try {
            KeyPair kp = ECC.generateKeyPair(c, rnd);
            
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Private Key");
            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                kp.getPrivateKey().saveToFile(path);
            } else {
                this.setVisible(false);
            }
            
            chooser = new JFileChooser();
            chooser.setDialogTitle("Save Public Key");
            returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                kp.getPublicKey().saveToFile(path);
            } else {
                this.setVisible(false);
            }
            
            pk = kp.getPrivateKey();
            jTextAreaPrivateKey.setText(pk.toString());
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(SignFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaPrivateKey;
    // End of variables declaration//GEN-END:variables
}
