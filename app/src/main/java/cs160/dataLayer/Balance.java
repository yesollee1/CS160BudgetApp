package cs160.dataLayer;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class Balance {
    private static Double mBalance = 0.0;

    public static Double getBalance() {
        return mBalance;
    }

    public static void addIncome(Double amount) {
        final DatabaseManager databaseManager = new DatabaseManager();
        mBalance += amount;
        databaseManager.addIncome(mBalance);
    }

    public static void subtractBalance(Double amount) {
        mBalance -= amount;
        //TODO DB
    }

    public static void moveMoney(Context context, String from, String to, Double amount) {
        if (from == to) {
        } else if (from == null) {
            Expense toExpense = ExpenseLab.get(context).getExpenseByName(to);
            subtractBalance(amount);
            toExpense.addMoney(amount);
        } else if (to == null) {
            Expense fromExpense = ExpenseLab.get(context).getExpenseByName(from);
            fromExpense.spend(amount);
            addIncome(amount);
        } else {
            Expense toExpense = ExpenseLab.get(context).getExpenseByName(to);
            Expense fromExpense = ExpenseLab.get(context).getExpenseByName(from);
            fromExpense.spend(amount);
            toExpense.addMoney(amount);
        }
    }
}
