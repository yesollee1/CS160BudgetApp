package cs160.UILayer;

import cs160.dataLayer.*;

import androidx.fragment.app.Fragment;

public class GoalListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new GoalListFragment();
    }
}
