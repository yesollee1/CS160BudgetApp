package cs160.dataLayer;

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
    }
}
