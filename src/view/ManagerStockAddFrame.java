/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

/**
 *
 * @author oshorawal
 */
package view;
import controller.*;
import model.*;
public class ManagerStockAddFrame extends javax.swing.JDialog {
    
    private StockController stock_controller;
    private Stock stock;
    /**
     * Creates new form manager_stock_add_frame
     */
    public ManagerStockAddFrame(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        stock_controller = new StockController();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stock_name_label = new javax.swing.JLabel();
        stock_name_textfield = new javax.swing.JTextField();
        stock_price_label = new javax.swing.JLabel();
        stock_price_textfield = new javax.swing.JTextField();
        submit_button = new javax.swing.JButton();
        cancel_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(3, 2));

        stock_name_label.setText("Stock Name");
        getContentPane().add(stock_name_label);
        getContentPane().add(stock_name_textfield);

        stock_price_label.setText("Stock Price");
        getContentPane().add(stock_price_label);
        getContentPane().add(stock_price_textfield);

        submit_button.setText("Submit");
        submit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(submit_button);

        cancel_button.setText("Cancel");
        cancel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(cancel_button);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_buttonActionPerformed
        // TODO add your handling code here:
        String name = stock_name_textfield.getText();
        Double price = Double.parseDouble(stock_price_textfield.getText());
        OpResponse res;
        res = this.stock_controller.addStock(name,price);
        if(res.status){
                this.stock = (Stock) res.data;
                javax.swing.JOptionPane.showMessageDialog(this,res.response,"Message",javax.swing.JOptionPane.PLAIN_MESSAGE);
                
        }
        else{
                javax.swing.JOptionPane.showMessageDialog(this,"Unsuccessfull","Message",javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_submit_buttonActionPerformed

    private void cancel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_buttonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_buttonActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ManagerStockAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ManagerStockAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ManagerStockAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ManagerStockAddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                ManagerStockAddFrame dialog = new ManagerStockAddFrame(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel_button;
    private javax.swing.JLabel stock_name_label;
    private javax.swing.JTextField stock_name_textfield;
    private javax.swing.JLabel stock_price_label;
    private javax.swing.JTextField stock_price_textfield;
    private javax.swing.JButton submit_button;
    // End of variables declaration//GEN-END:variables
}
