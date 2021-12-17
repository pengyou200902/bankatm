/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author oshorawal
 */
package view;
import controller.*;
import model.*;
import java.awt.*;
public class LoginPage extends javax.swing.JFrame {

    /**
     * Creates new form Login_page
     */
    private LoginController login_controller;
    
    public LoginPage() {
        this.login_controller = new LoginController();
        initComponents();
        login_pannel.setBackground(Color.GREEN);
        buttons_pannel.setBackground(Color.GREEN);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login_pannel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        username_textfield = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        password_textfield = new javax.swing.JPasswordField();
        buttons_pannel = new javax.swing.JPanel();
        login_button = new javax.swing.JButton();
//        change_password_button = new javax.swing.JButton();
        sign_up_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        login_pannel.setLayout(new java.awt.GridLayout(5, 0, 20, 20));

        jLabel1.setText("Username");
        login_pannel.add(jLabel1);
        login_pannel.add(username_textfield);

        jLabel2.setText("Password");
        login_pannel.add(jLabel2);
        login_pannel.add(password_textfield);

        buttons_pannel.setLayout(new java.awt.GridLayout(1, 3));

        login_button.setText("Login");
        login_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_buttonActionPerformed(evt);
            }
        });
        buttons_pannel.add(login_button);

//        change_password_button.setText("Change password");
//        change_password_button.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                change_password_buttonActionPerformed(evt);
//            }
//        });
//        buttons_pannel.add(change_password_button);

        sign_up_button.setText("Sign up");
        sign_up_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sign_up_buttonActionPerformed(evt);
            }
        });
        buttons_pannel.add(sign_up_button);

        login_pannel.add(buttons_pannel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(login_pannel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(login_pannel, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void login_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_buttonActionPerformed
        // TODO add your handling code here:
        String username = username_textfield.getText();
        String pass = new String(password_textfield.getPassword());
        User user = login_controller.login(username,pass);

        if (user != null){
            if(username.equalsIgnoreCase("admin")){
              ManagerFrame manager = new ManagerFrame();
              this.setVisible(false);
              manager.setVisible(true);
            }
            else{
                UserFrame user_main_menu = new UserFrame(user);
                this.setVisible(false);
                user_main_menu.setVisible(true);
            }
        }
        else{
            javax.swing.JOptionPane.showMessageDialog(this,"Wrong credentials","Login Error",javax.swing.JOptionPane.WARNING_MESSAGE);

        }
       
    }//GEN-LAST:event_login_buttonActionPerformed

    private void change_password_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_change_password_buttonActionPerformed
        // TODO add your handling code here:
        PasswordChange change_password_frame = new PasswordChange();
        change_password_frame.setLocationRelativeTo(null);
        change_password_frame.setVisible(true);
    }//GEN-LAST:event_change_password_buttonActionPerformed

    private void sign_up_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sign_up_buttonActionPerformed
        // TODO add your handling code here:
        SignUp sign_up_frame = new SignUp(this,true);
        sign_up_frame.setVisible(true);
    }//GEN-LAST:event_sign_up_buttonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttons_pannel;
//    private javax.swing.JButton change_password_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton login_button;
    private javax.swing.JPanel login_pannel;
    private javax.swing.JPasswordField password_textfield;
    private javax.swing.JButton sign_up_button;
    private javax.swing.JTextField username_textfield;
    // End of variables declaration//GEN-END:variables
}
