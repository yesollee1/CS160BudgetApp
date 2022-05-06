package cs160.UILayer;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class IncomeActivity extends SingleFragmentActivity {

    // This method is called to start a new Expense Activity
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, IncomeActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new IncomeFragment();
//        return IncomeFragment.newInstance();
    }
}