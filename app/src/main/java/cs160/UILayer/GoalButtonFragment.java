package cs160.UILayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import cs160.dataLayer.*;

public class GoalButtonFragment extends Fragment {
    private Button mAddNewButton;
    private int mLastClickedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        mAddNewButton = (Button) view.findViewById(R.id.add_new_button);
        mAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goal goal = new Goal();
                GoalLab goalLab = GoalLab.get(getActivity());
                goalLab.addGoal(goal);
                Intent intent = GoalActivity.newIntent(getActivity(), goal.getId());
                startActivity(intent);
            }
        });
        return view;
    }
}
