package cs160.UILayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cs160.dataLayer.Budget;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button goalList;
    private Button expenseList;
    private Button transactionList;
    private Button addIncomeBtn;
    private Button logoutBtn;
    private TextView signedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);


        goalList = findViewById(R.id.linkToGoal);
        expenseList = findViewById(R.id.linkToExpense);
        transactionList = findViewById(R.id.linkToTransaction);
        addIncomeBtn = findViewById(R.id.addIncome);
        logoutBtn = findViewById(R.id.logoutBtn);
        signedInUser = findViewById(R.id.signedInUser);

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


    }

}