package cs160.UILayer;

import cs160.dataLayer.*;

import androidx.fragment.app.Fragment;

public class ExpenseListActivity extends ButtonFragmentActivity {
    @Override
    protected Fragment createButtonFragment() {
        return new ExpenseButtonFragment();
    }

    @Override
    protected Fragment createListFragment() {
        return new ExpenseListFragment();
    }
}
