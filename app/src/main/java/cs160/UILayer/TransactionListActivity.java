package cs160.UILayer;

import androidx.fragment.app.Fragment;

public class TransactionListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TransactionListFragment();
    }
}