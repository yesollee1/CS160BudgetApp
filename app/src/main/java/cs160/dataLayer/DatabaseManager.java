package cs160.dataLayer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void addToExpenses(Category category) {
        FirebaseUser current = mAuth.getCurrentUser();

        db.collection("Users").document(current.getUid()).collection("Budget").
                document(category.getTitle()).set(category,SetOptions.merge());
    }

    public void updateExpenses(Context context, String expenseName) {
        FirebaseUser current = mAuth.getCurrentUser();

        Expense expense = ExpenseLab.get(context).getExpenseByName(expenseName);

        db.collection("Users").document(current.getUid()).collection("Budget").
                document(expense.getTitle()).set(expense,SetOptions.merge());
    }

    public void deleteExpense(Category category) {
        FirebaseUser current = mAuth.getCurrentUser();
        db.collection("Users").document(current.getUid()).collection("Budget").
                document(category.getTitle()).delete();
    }

    public void addToGoals(Category category) {
        FirebaseUser current = mAuth.getCurrentUser();
        db.collection("Users").document(current.getUid()).collection("Goals").
                document(category.getTitle()).set(category,SetOptions.merge());
    }

    public void deleteGoal(Category category) {
        FirebaseUser current = mAuth.getCurrentUser();
        db.collection("Users").document(current.getUid()).collection("Goals").
                document(category.getTitle()).delete();
    }

    public void addToTransactions(Transaction receipt) {
        FirebaseUser current = mAuth.getCurrentUser();

        db.collection("Users").document(current.getUid()).collection("Transactions").
                document(String.valueOf(receipt.getId())).set(receipt,SetOptions.merge());

    }

    public void deleteTransaction(Transaction receipt) {
        FirebaseUser current = mAuth.getCurrentUser();
        db.collection("Users").document(current.getUid()).collection("Transactions").
                document(String.valueOf(receipt.getId())).delete();
    }

    public static void addNewUser(String uid, String name) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("Income", 0.0);
        db.collection("Users").document(uid).set(data,SetOptions.merge());
    }

    public void addIncome(Double income) {
        FirebaseUser current = mAuth.getCurrentUser();

        Map<String, Object> inc = new HashMap<>();
        inc.put("Income", income);

        db.collection("Users").document(current.getUid()).
                set(inc,SetOptions.merge());
    }

    public void subtractBalanceMoney(Double income) {
        //TODO: subtract balance money in db
    }

    public Double getIncome() {
        FirebaseUser current = mAuth.getCurrentUser();
        final Double[] income = {0.0};

        DocumentReference docRef = db.collection("cities").document(current.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        income[0] = doc.getDouble("Income");
                        Log.d(TAG, "DocumentSnapshot data ");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return (income[0]);
    }

}

