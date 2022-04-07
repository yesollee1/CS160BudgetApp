package cs160.UILayer;

import cs160.dataLayer.*;

import androidx.fragment.app.Fragment;

public class GoalListActivity extends ButtonFragmentActivity {
    @Override
    protected Fragment createButtonFragment() {
        return new GoalButtonFragment();
    }
    @Override
    protected Fragment createListFragment() {
        return new GoalListFragment();
    }
}
