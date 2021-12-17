package view;

import java.awt.Color;
import java.util.HashMap;
import java.util.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author oshorawal
 */
import controller.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SecurityAccount extends javax.swing.JFrame {

    /**
     * Creates new form User_stocks
     */
    private Security user_bank_account;
    private StockController stock_account_controller;

    public SecurityAccount(Security acc) {
        this.user_bank_account = acc;
        this.stock_account_controller = new StockController();
        initComponents();
        this.getContentPane().setBackground(Color.GREEN);
        jPanel1.setBackground(Color.GREEN);
        clear_table();
        addDataToTable();
        clear_table2();
        addDataToTable2();
        setTotal_balance_label();
        setTotal_realized_profit_label();
        setTotal_unrealized_profit_label();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buy_stock_button = new javax.swing.JButton();
        sell_stock_button = new javax.swing.JButton();
//        add_balance_button = new javax.swing.JButton();
        total_balance_label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stock_table = new javax.swing.JTable();
//        jScrollPane2 = new javax.swing.JScrollPane();
        stock_table2 = new javax.swing.JTable();
        total_realized_profit_label = new javax.swing.JLabel();
        total_unrealized_profit_label = new javax.swing.JLabel();
        total_balance = new javax.swing.JLabel();
        total_realized_profit = new javax.swing.JLabel();
        total_unrealized_profit = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        buy_stock_button.setText("Buy Stock");
        buy_stock_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buy_stock_buttonActionPerformed(evt);
            }
        });

        sell_stock_button.setText("Sell Stock");
        sell_stock_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_stock_buttonActionPerformed(evt);
            }
        });

//        add_balance_button.setText("Add Balance");
//        add_balance_button.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                add_balance_buttonActionPerformed(evt);
//            }
//        });

        total_balance_label.setText("Total Balance");

        stock_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Stock Name","Quantity","AVG Buy Price"
            }
        ));
        jScrollPane1.setViewportView(stock_table);

        stock_table2.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String [] {
                        "Stock Name","Price"
                }
        ));
        jScrollPane1.setViewportView(stock_table2);

        total_realized_profit_label.setText("Total Realized Profit");

        total_unrealized_profit_label.setText("Total Unrealized Profit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buy_stock_button)
                        .addGap(18, 18, 18)
                        .addComponent(sell_stock_button)
                        .addGap(18, 18, 18)
                        )
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(total_balance_label, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(total_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(total_realized_profit_label)
                                        .addComponent(total_unrealized_profit_label))
                                    .addGap(89, 89, 89)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(total_unrealized_profit, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(total_realized_profit, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buy_stock_button)
                    .addComponent(sell_stock_button)
                    )
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(total_balance_label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(total_balance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(total_realized_profit_label, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(total_realized_profit, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(total_unrealized_profit_label, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(total_unrealized_profit, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sell_stock_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_stock_buttonActionPerformed
        // TODO add your handling code here:
        UserSellStockFrame sell_stock_frame = new UserSellStockFrame(this,true,this.user_bank_account);
        sell_stock_frame.setVisible(true);
        setTotal_balance_label();
        setTotal_realized_profit_label();
        setTotal_unrealized_profit_label();
        clear_table();
        addDataToTable();
        clear_table2();
        addDataToTable2();
    }//GEN-LAST:event_sell_stock_buttonActionPerformed

    private void buy_stock_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buy_stock_buttonActionPerformed
        // TODO add your handling code here:
        UserBuyStockFrame buy_stock_frame = new UserBuyStockFrame(this,true, this.user_bank_account);
        buy_stock_frame.setVisible(true);
        setTotal_balance_label();
        setTotal_realized_profit_label();
        setTotal_unrealized_profit_label();
        clear_table();
        addDataToTable();
        clear_table2();
        addDataToTable2();
    }//GEN-LAST:event_buy_stock_buttonActionPerformed

//    private void add_balance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_balance_buttonActionPerformed
//        // TODO add your handling code here:
//
//    }//GEN-LAST:event_add_balance_buttonActionPerformed

    private void clear_table(){
        DefaultTableModel table = (DefaultTableModel)  stock_table.getModel();
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            table.removeRow(i);
        }
    }

    private void addDataToTable(){
        DefaultTableModel table = (DefaultTableModel)  stock_table.getModel();
        HashMap<Stock, Integer> Stocks = this.user_bank_account.getOwned();
        Set<Stock> keys =  Stocks.keySet();
        for(Stock stock: keys){
            int value = Stocks.get(stock);
            table.addRow(new Object[]{stock.getName(),value,stock.getPrice().getAmount()});
        }
    }

    private void setTotal_balance_label(){
        BaseCurrency crr = this.user_bank_account.getBalance();
        total_balance.setText(String.valueOf(crr.getAmount()));
    }

    private void setTotal_realized_profit_label(){
        BaseCurrency crr = this.user_bank_account.getRealizedProfit();
        total_realized_profit.setText(String.valueOf(crr.getAmount()));
    }
    private void setTotal_unrealized_profit_label(){
        BaseCurrency crr = this.user_bank_account.getUnrealizedProfit();
        total_unrealized_profit.setText(String.valueOf(crr.getAmount()));
    }

    private void addDataToTable2(){
        DefaultTableModel table = (DefaultTableModel)  stock_table2.getModel();
        LinkedList<Stock> stockList = (LinkedList<Stock>) this.stock_account_controller.getAllStocks().data;
        for (Stock stock : stockList) {
            table.addRow(new Object[]{stock.getName(),stock.getPrice().getAmount()});
        }
    }

    private void clear_table2(){
        DefaultTableModel table = (DefaultTableModel)  stock_table2.getModel();
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            table.removeRow(i);
        }
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
//            java.util.logging.Logger.getLogger(Security_account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Security_account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Security_account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Security_account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Security_account().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton add_balance_button;
    private javax.swing.JButton buy_stock_button;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
//    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton sell_stock_button;
    private javax.swing.JTable stock_table;
    private javax.swing.JTable stock_table2;
    private javax.swing.JLabel total_balance;
    private javax.swing.JLabel total_balance_label;
    private javax.swing.JLabel total_realized_profit;
    private javax.swing.JLabel total_realized_profit_label;
    private javax.swing.JLabel total_unrealized_profit;
    private javax.swing.JLabel total_unrealized_profit_label;
    // End of variables declaration//GEN-END:variables
}
