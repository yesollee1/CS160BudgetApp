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

    private static final String EXTRA_GOAL_ID = "cs160.UILayer.goal_id";

    // This method is called to start a new Goal Activity
    public static Intent newIntent(Context packageContext, UUID goalId) {
        Intent intent = new Intent(packageContext, GoalActivity.class);
        intent.putExtra(EXTRA_GOAL_ID, goalId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID goalId = (UUID) getIntent().getSerializableExtra(EXTRA_GOAL_ID);
        return GoalFragment.newInstance(goalId);
    }
}