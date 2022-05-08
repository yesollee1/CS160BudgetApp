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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private Button mDeleteBtn;
    //TODO: Implement "completed" feature for goals
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
        if (mGoal != null) {
            mDate = mGoal.getDate();
        }
        if (mDate == null) {
            mDate = new Date();
        }
        setHasOptionsMenu(true);
        if (mGoal != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Goal: " + mGoal.getTitle());
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Goal");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.expense_list_activity:
                intent = new Intent(getActivity(), ExpenseListActivity.class);
                startActivity(intent);
                return true;
            case R.id.goal_list_activity:
                intent = new Intent(getActivity(), GoalListActivity.class);
                startActivity(intent);
                return true;
            case R.id.transaction_list_activity:
                intent = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

        mAmountField = (EditText) v.findViewById(R.id.goal_amount);
        if (mGoal != null) {
            Double proposedAmount = mGoal.getProposedAmount();
            if (proposedAmount != 0) {
                mAmountField.setText(proposedAmount.toString());
            }
        }

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
                String title = mTitleField.getText().toString();
                if (title.isEmpty()) {
                    mTitleField.setError("Title must not be empty");
                } else {
                    try {
                        Double amount = Double.parseDouble(mAmountField.getText().toString());
                        if (mGoal == null) {
                            Goal goal = new Goal(title, Frequency.MONTHLY, amount, mDate);
                            GoalLab goalLab = GoalLab.get(getActivity());
                            goalLab.addGoal(goal);
                            mGoal = goal;
                        } else {
                            mGoal.setTitle(title);
                            mGoal.setProposedAmount(amount);
                            mGoal.setDate(mDate);
                        }

                        databaseManager.addToGoals(mGoal);

                        Intent intent = new Intent(GoalFragment.this.getActivity(), GoalListActivity.class);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        mAmountField.setError("Amount must be a valid number");
                    }
                }
            }
        });

        mDeleteBtn = v.findViewById(R.id.delete_button);
        mDeleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitleField.getText().toString();
                try {
                    if (mGoal != null) {
                        GoalLab goalLab = GoalLab.get(getActivity());
                        goalLab.deleteGoal(mGoal.getId());
                        Intent intent = new Intent(GoalFragment.this.getActivity(), GoalListActivity.class);
                        startActivity(intent);
                    }
                } catch (NullPointerException e) {
                    mAmountField.setError("Cannot Delete Non-Saved Goal");
                }
            }

        });

        return v;
    }

    private final DatabaseManager databaseManager = new DatabaseManager();

//// We should probably use the onPause() method to store info when the date fragment pops up
//    @Override
//    public void onPause() {
//        super.onPause();
//        double amount;
//        try {
//            amount = Double.parseDouble(mAmountField.getText().toString());
//        } catch (Exception e) {
//            amount = 0.0;
//        }
////        mGoal.setProposedAmount(amount);
//    }

    private void updateDate() {
        DateFormat df = new DateFormat();
        CharSequence formattedDate = df.format("E, MMM d, yyyy", mDate);
        mDateButton.setText(formattedDate);
    }
}
