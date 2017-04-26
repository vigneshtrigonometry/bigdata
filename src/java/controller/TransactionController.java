/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Transaction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Vignesh
 */
@Stateless
public class TransactionController {

    public List<Transaction> getAllTransactionsForCity(String city) {
        HiveController controller;
        List<Transaction> transactions = new ArrayList<>();
        try {
            controller = new HiveController();
            ResultSet result = controller.getTransactionsForCity(city);
            while (result.next()) {
                Transaction t = new Transaction();
                t.setDeviceId(result.getInt("deviceid"));
                t.setId(result.getLong("txid"));
                t.setTransactionAmt(result.getFloat("txvalue"));
                t.setAccountNo(result.getLong("accountid"));
                t.setIsFraud(result.getBoolean("fraud"));
                transactions.add(t);
            }
            return transactions;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
