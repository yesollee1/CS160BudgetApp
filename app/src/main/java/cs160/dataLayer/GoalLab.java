package cs160.dataLayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GoalLab {
    private static GoalLab sGoalLab;
    private List<Goal> mGoals;
    private Map<UUID, Goal> mGoalMap;

    public static GoalLab get(Context context) {
        if (sGoalLab == null) {
            sGoalLab = new GoalLab(context);
        }
        return sGoalLab;
    }

    private GoalLab(Context context) {
        mGoals = new ArrayList<>();
        mGoalMap = new HashMap<>();
    }

    public void addGoal(Goal goal) {
        mGoals.add(goal);
        mGoalMap.put(goal.getId(), goal);
    }

    public List<Goal> getGoals() {
        return mGoals;
    }

    public Goal getGoal(UUID id) {
        return mGoalMap.get(id);
    }
}
