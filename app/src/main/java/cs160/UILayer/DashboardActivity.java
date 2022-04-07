package cs160.UILayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {
    private Button goalList;
    private Button expenseList;
    private Button transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        goalList = findViewById(R.id.linkToGoal);
        expenseList = findViewById(R.id.linkToExpense);
        transactionList = findViewById(R.id.linkToTransaction);

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


    }
}