package cs160.UILayer;

import cs160.dataLayer.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class ExpenseActivity extends SingleFragmentActivity {

    private static final String EXTRA_EXPENSE_ID = "cs160.UILayer.expense_id";

    // This method is called to start a new Expense Activity
    public static Intent newIntent(Context packageContext, UUID expenseId) {
        Intent intent = new Intent(packageContext, ExpenseActivity.class);
        intent.putExtra(EXTRA_EXPENSE_ID, expenseId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID expenseId = (UUID) getIntent().getSerializableExtra(EXTRA_EXPENSE_ID);
        return ExpenseFragment.newInstance(expenseId);
    }
}