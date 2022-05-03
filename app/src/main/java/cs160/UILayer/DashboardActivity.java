package cs160.UILayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button goalList;
    private Button expenseList;
    private Button transactionList;
    private Button logoutBtn;
    private TextView signedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        //init firebaseAuth
       // firebaseAuth = FirebaseAuth.getInstance();
        //checkUser();





        goalList = findViewById(R.id.linkToGoal);
        expenseList = findViewById(R.id.linkToExpense);
        transactionList = findViewById(R.id.linkToTransaction);
        logoutBtn = findViewById(R.id.logoutBtn);
        signedInUser = findViewById(R.id.signedInUser);

//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseAuth.signOut();
//                checkUser();
//            }
//        });

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


    }

//    private void checkUser() {
//        //get current user
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if(firebaseUser == null){
//            // user not logged in
//            startActivity(new Intent(this, SignIn_Activity.class));
//            finish();
//        }
//        else{
//            //user logged in
//            // get user info
//            String email = firebaseUser.getEmail();
//            //set email
//            signedInUser.setText(email);
//        }
//    }
}