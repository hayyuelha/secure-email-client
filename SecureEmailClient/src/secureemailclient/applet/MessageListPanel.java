/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureemailclient.applet;

import java.util.List;

import com.google.api.services.gmail.model.Message;
import java.io.IOException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Toshiba
 */
public class MessageListPanel extends javax.swing.JPanel {

    /**
     * Creates new form MessageListPanel
     */
    public MessageListPanel(String label) {
        initComponents();
        
        // hide the id
        jTableMessages.removeColumn(jTableMessages.getColumnModel().getColumn(3));

        List<String> labels = new ArrayList<String>();
        labels.add(label);
        try {
            loadMessageList(GmailHelper.listMessagesWithLabels(GmailAuth.getService(), "me", labels));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMessages = new javax.swing.JTable();

        jTableMessages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Recipient", "Snippet", "Time", "Message Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMessages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMessagesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMessages);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableMessagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMessagesMouseClicked
        if (evt.getClickCount() == 2) {
            int row = jTableMessages.getSelectedRow();
            System.out.println("Selected row: " + row);
            String messageId = jTableMessages.getModel().getValueAt(row, 3).toString();
            System.out.println("Selected message: " + messageId);
        }
    }//GEN-LAST:event_jTableMessagesMouseClicked

    public void loadMessageList(List<Message> messageList) {
        DefaultTableModel model = (DefaultTableModel) jTableMessages.getModel();
        model.setRowCount(0);
        for (Message message : messageList) {
            model.addRow(new Object[]{message.getId(), message.getSnippet(), message.getThreadId(), message.getId()});
        }
    }
    
    public void loadMessage(String messageId) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMessages;
    // End of variables declaration//GEN-END:variables
}
