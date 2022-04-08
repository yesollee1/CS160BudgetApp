package cs160.UILayer;

import cs160.dataLayer.*;

import static android.widget.CompoundButton.*;

import android.content.Intent;
import android.app.ActionBar;
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
    private Button mDateButton, mBack;
    //TODO: implement "completed" feature for goals
    private CheckBox mCompletedCheckBox;

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
        mGoal = GoalLab.get(getActivity()).getGoal(goalId);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The third argument of the method below tells whether to add the inflated view to the view's parent
        // Here, we pass in false because we will add the view in the activity's code
        View v = inflater.inflate(R.layout.fragment_goal, container, false);

        mTitleField = (EditText) v.findViewById(R.id.goal_title);
        mTitleField.setText(mGoal.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                mGoal.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank
            }
        });

        // Change amount for goal in onPause() method
        mAmountField = (EditText) v.findViewById(R.id.goal_amount);
        Double proposedAmount = mGoal.getProposedAmount();
        if (proposedAmount != 0) {
            mAmountField.setText(proposedAmount.toString());
        }
        mAmountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    Double amount = Double.parseDouble(s.toString());
                    mGoal.setProposedAmount(amount);
                } catch (NumberFormatException e) {
                    // on empty string (when user backspaces) or non-number input, amount should be 0
                    mGoal.setProposedAmount(0.0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.goal_date);
        updateDate();
//        mDateButton.setEnabled(false); // Disables button

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getChildFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mGoal.getDate());
                manager.setFragmentResultListener(DatePickerFragment.ARG_DATE, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Date date = (Date) result.getSerializable(DatePickerFragment.EXTRA_DATE);
                        mGoal.setDate(date);
                        updateDate();
                    }
                });
                dialog.show(manager, DIALOG_DATE);
            }
        });
    //    ActionBar action = getSupportActionBar();
//        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.goal_solved);
//        mSolvedCheckBox.setChecked(mGoal.isSolved());
//        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mGoal.setSolved(isChecked);
//            }
//        });

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
        CharSequence formattedDate = df.format("E, MMM d, yyyy", mGoal.getDate());
        mDateButton.setText(formattedDate);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
//        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
//        if (mSubtitleVisible) {
//            subtitleItem.setTitle(R.string.hide_subtitle);
//        } else {
//            subtitleItem.setTitle(R.string.show_subtitle);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.expense_list_activity:
//                Expense crime = new Expense();
//                ExpenseLab.get(getActivity()).addExpense(crime);
//                Intent intent = ExpenseActivity.newIntent(getActivity(), crime.getId());
                intent = new Intent(getActivity(), ExpenseListActivity.class);
                startActivity(intent);
                return true;
            case R.id.goal_list_activity:
//                Expense crime = new Expense();
//                ExpenseLab.get(getActivity()).addExpense(crime);
//                Intent intent = ExpenseActivity.newIntent(getActivity(), crime.getId());
                intent = new Intent(getActivity(), GoalListActivity.class);
                startActivity(intent);
                return true;
            case R.id.transaction_list_activity:
//                Expense crime = new Expense();
//                ExpenseLab.get(getActivity()).addExpense(crime);
//                Intent intent = ExpenseActivity.newIntent(getActivity(), crime.getId());
                intent = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
