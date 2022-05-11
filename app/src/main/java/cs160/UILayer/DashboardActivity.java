package cs160.UILayer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import cs160.dataLayer.Balance;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button goalList;
    private Button expenseList;
    private Button transactionList;
    private Button addIncomeBtn;
    private Button logoutBtn;
//    private TextView signedInUser;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static boolean dataPopulated = false;
    private static boolean incomePopulated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);


        goalList = findViewById(R.id.linkToGoal);
        expenseList = findViewById(R.id.linkToExpense);
        transactionList = findViewById(R.id.linkToTransaction);
        addIncomeBtn = findViewById(R.id.addIncome);
        logoutBtn = findViewById(R.id.logoutBtn);
//        signedInUser = findViewById(R.id.signedInUser);

        mAuth = FirebaseAuth.getInstance();

        logoutBtn.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        });

        goalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, GoalListActivity.class);
                startActivity(intent);
            }
        });

        expenseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ExpenseListActivity.class);
                startActivity(intent);
            }
        });

        transactionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        addIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser current = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("Users").document(current.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && (!incomePopulated)) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        double total = 0.0;
//                        for(double amount:sumOfBudget){
//                            total += amount;
//                        }
                        Balance.addIncome(document.getDouble("Income"));
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        incomePopulated = true;
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}