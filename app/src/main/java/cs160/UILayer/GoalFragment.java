package cs160.UILayer;

import cs160.dataLayer.*;

import static android.widget.CompoundButton.*;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import java.util.Date;
import java.util.UUID;

public class GoalFragment extends Fragment {
    private static final String ARG_GOAL_ID = "goal_id";
    private static final String DIALOG_DATE = "DialogDate";

    private Goal mGoal;
    private EditText mTitleField;
    private EditText mAmountField;
    private Button mDateButton;
    private Button mConfirmBtn;
    //TODO: implement "completed" feature for goals
    private CheckBox mCompletedCheckBox;

    private Date mDate; // this is for saving a date before the goal has been saved

    public static GoalFragment newInstance(UUID goalId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GOAL_ID, goalId);
        GoalFragment fragment = new GoalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID goalId = (UUID) getArguments().getSerializable(ARG_GOAL_ID);
        if (goalId != null) {
            mGoal = GoalLab.get(getActivity()).getGoal(goalId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The third argument of the method below tells whether to add the inflated view to the view's parent
        // Here, we pass in false because we will add the view in the activity's code
        View v = inflater.inflate(R.layout.fragment_goal, container, false);

        mTitleField = (EditText) v.findViewById(R.id.goal_title);
        if (mGoal != null) {
            mTitleField.setText(mGoal.getTitle());
        }
//        mTitleField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // This space intentionally left blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int count, int after) {
//                mGoal.setTitle(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // This space intentionally left blank
//            }
//        });

        // Change amount for goal in onPause() method
        mAmountField = (EditText) v.findViewById(R.id.goal_amount);
        if (mGoal != null) {
            Double proposedAmount = mGoal.getProposedAmount();
            if (proposedAmount != 0) {
                mAmountField.setText(proposedAmount.toString());
            }
        }
//        mAmountField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // This space intentionally left blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int count, int after) {
//                try {
//                    Double amount = Double.parseDouble(s.toString());
//                    mGoal.setProposedAmount(amount);
//                } catch (NumberFormatException e) {
//                    // on empty string (when user backspaces) or non-number input, amount should be 0
//                    mGoal.setProposedAmount(0.0);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // This space intentionally left blank
//            }
//        });

        mDateButton = (Button) v.findViewById(R.id.goal_date);
        updateDate();
//        mDateButton.setEnabled(false); // Disables button

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getChildFragmentManager();
                DatePickerFragment dialog;
                if (mGoal != null) {
                    dialog = DatePickerFragment.newInstance(mGoal.getDate());
                } else {
                    dialog = DatePickerFragment.newInstance(new Date());
                }
                manager.setFragmentResultListener(DatePickerFragment.ARG_DATE, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        mDate = (Date) result.getSerializable(DatePickerFragment.EXTRA_DATE);
                        updateDate();
                    }
                });
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mConfirmBtn = v.findViewById(R.id.confirm_button);
        mConfirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoal == null) {
                    Goal goal = new Goal();
                    GoalLab goalLab = GoalLab.get(getActivity());
                    goalLab.addGoal(goal);
                    mGoal = goal;
                }
                String title = mTitleField.getText().toString();
                if (title.isEmpty()) {
                    mTitleField.setError("Title must not be empty");
                    mGoal.setTitle("Bug needs to be addressed! Empty title can be created!");
                }else{
                    Intent intent = new Intent(GoalFragment.this.getActivity(), GoalListActivity.class);
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        double amount;
        try {
            amount = Double.parseDouble(mAmountField.getText().toString());
        } catch (Exception e) {
            amount = 0.0;
        }
        mGoal.setProposedAmount(amount);
    }

    private void updateDate() {
//        mDateButton.setText(mGoal.getDate().toString());
        DateFormat df = new DateFormat();
        if (mDate == null) {
            mDate = new Date();
        }
        CharSequence formattedDate = df.format("E, MMM d, yyyy", mDate);
        mDateButton.setText(formattedDate);
    }

//    private void onConfirmClicked() {
//        String title = mTitleField.getText().toString();
//        if (title.isEmpty()) {
//            mTitleField.setError("Title must not be empty");
//        }
//    }
}
