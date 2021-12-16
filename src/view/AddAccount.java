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
import java.awt.*;

public class AddAccount extends javax.swing.JDialog {
    private BankAccountController bank_account_controller;
    private BankAccount[] accounts;
    private User user;
    /**
     * Creates new form AddAccount
     */
    public AddAccount(javax.swing.JDialog parent, boolean modal,User user) {
        super(parent, modal);
        this.user = user;
        this.bank_account_controller = new BankAccountController();
        initComponents();

//        jPanel1.setBackground(Color.GREEN);
        setLocationRelativeTo(parent);

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        account_type_box = new javax.swing.JComboBox<>();
        submitButton = new javax.swing.JButton();
//        CancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        getContentPane().setLayout(new java.awt.GridLayout(2, 2));

        jLabel1.setText("Account Type");
        getContentPane().add(jLabel1);

        account_type_box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Checking", "Saving", "Security"}));
        getContentPane().add(account_type_box);

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(submitButton);
//        CancelButton.setText("Cancel");
//        CancelButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                CancelButtonActionPerformed(evt);
//            }
//        });
//        getContentPane().add(CancelButton);

        pack();
    }// </editor-fold>

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (account_type_box.getSelectedItem().toString().equalsIgnoreCase(AccountTypes.SAVING.getTypeString())){
            // Call the account creation controller
            bank_account_controller.openAccount(this.user.getUsername(), AccountTypes.SAVING.getTypeString());

        }
        if (account_type_box.getSelectedItem().toString().equalsIgnoreCase(AccountTypes.CHECKING.getTypeString())){
            // Call the account creation controller
            bank_account_controller.openAccount(this.user.getUsername(), AccountTypes.CHECKING.getTypeString());

        }
        if (account_type_box.getSelectedItem().toString().equalsIgnoreCase(AccountTypes.SECURITY.getTypeString())){
            // Call the account creation controller
            bank_account_controller.openAccount(this.user.getUsername(), AccountTypes.SECURITY.getTypeString());

        }

//        this.accounts = bank_account_controller.getAllBankAccounts(this.user.getUsername());
    }

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.dispose();
    }

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
//            java.util.logging.Logger.getLogger(AddAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AddAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AddAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AddAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                AddAccount dialog = new AddAccount(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify
//    private javax.swing.JButton CancelButton;
    private javax.swing.JComboBox<String> account_type_box;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton submitButton;
    // End of variables declaration
}
