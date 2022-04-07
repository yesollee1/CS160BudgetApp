package cs160.UILayer;

import androidx.fragment.app.Fragment;

public class TransactionListActivity extends ButtonFragmentActivity {
    @Override
    protected Fragment createButtonFragment() {
        return new TransactionButtonFragment();
    }

    @Override
    protected Fragment createListFragment() {
        return new TransactionListFragment();
    }
}