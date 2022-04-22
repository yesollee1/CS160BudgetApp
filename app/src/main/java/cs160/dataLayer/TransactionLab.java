package cs160.dataLayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionLab {
    private static TransactionLab sTransactionLab;
    private List<Transaction> mTransactions;
    private Map<UUID, Transaction> mTransactionMap;

    public static TransactionLab get(Context context) {
        if (sTransactionLab == null) {
            sTransactionLab = new TransactionLab(context);
        }
        return sTransactionLab;
    }

    private TransactionLab(Context context) {
        mTransactions = new ArrayList<>();
        mTransactionMap = new HashMap<>();
    }

    public void addTransaction(Transaction transaction) {
        mTransactions.add(transaction);
        mTransactionMap.put(transaction.getId(), transaction);
    }

    public List<Transaction> getTransactions() {
        return mTransactions;
    }

    public Transaction getTransaction(UUID id) {
        return mTransactionMap.get(id);
    }
}
