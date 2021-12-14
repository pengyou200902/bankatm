/**
 * @author You Peng
 * @date 12/11/2021 2:09 PM
 */


package controller;

import dao.TransactionDao;
import model.OpResponse;
import model.Transaction;

import java.sql.Timestamp;
import java.util.List;

public class TransactionController {
    private final TransactionDao transactionDao;

    public TransactionController() {
        transactionDao = TransactionDao.getInstance();
    }

    public OpResponse getAll() {
        List<Transaction> transactions = transactionDao.getAll();
        return new OpResponse(1, true, "Success", transactions);
    }

    public OpResponse getByDate(Timestamp date) {

        // only get by a specific day, 2021-12-13

        List<Transaction> transactions = transactionDao.getByDate(date);
        return new OpResponse(1, true, "Success", transactions);
    }

}
