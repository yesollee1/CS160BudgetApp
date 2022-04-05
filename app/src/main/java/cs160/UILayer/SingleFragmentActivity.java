package cs160.UILayer;

import static android.content.ContentValues.TAG;

import cs160.dataLayer.*;

import android.content.Intent; // *** new
import android.os.Bundle;
import android.util.Log; // *** new
import android.view.View;
import android.widget.Button; // *** new

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    private Button goalBtn;
    private Button expenseBtn;
    private Button transactionBtn; // to be implemented later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment) // Creates fragment transaction
                    .addToBackStack("main")
                    .commit(); // Commits fragment transaction
        }

        goalBtn = (Button) findViewById(R.id.goalButton);
        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalListActivity gla = new GoalListActivity();
                Fragment goalFragment = gla.createFragment();

                fm.beginTransaction()
                        .replace(R.id.fragment_container, goalFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("goals")
                        .commit();
            }
        });

        expenseBtn = (Button) findViewById(R.id.expenseButton);
        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseListActivity ela = new ExpenseListActivity();
                Fragment expenseFragment = ela.createFragment();

                fm.beginTransaction()
                        .replace(R.id.fragment_container, expenseFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("expenses")
                        .commit();
            }
        });
    }

}
