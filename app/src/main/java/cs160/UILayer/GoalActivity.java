package cs160.UILayer;

import cs160.dataLayer.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class GoalActivity extends SingleFragmentActivity {

    private static final String EXTRA_GOAL_ID = "com.bignerdranch.android.criminal_intent.expense_id";

    // This method is called to start a new Expense Activity
    public static Intent newIntent(Context packageContext, UUID goalId) {
        Intent intent = new Intent(packageContext, GoalActivity.class);
        intent.putExtra(EXTRA_GOAL_ID, goalId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //return new ExpenseFragment();
        UUID goalId = (UUID) getIntent().getSerializableExtra(EXTRA_GOAL_ID);
        return GoalFragment.newInstance(goalId);
    }
//    // This code is now handled in the SingleFragmentActivity class
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//        if (fragment == null) {
//            fragment = new ExpenseFragment();
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment) // Creates fragment transaction
//                    .commit(); // Commits fragment transaction
//        }
//    }
}