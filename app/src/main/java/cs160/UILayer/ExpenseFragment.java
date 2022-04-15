package cs160.UILayer;

import cs160.dataLayer.*;

import static android.widget.CompoundButton.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class ExpenseFragment extends Fragment {
    private static final String ARG_EXPENSE_ID = "expense_id";
//    private static final String DIALOG_DATE = "DialogDate";

    private Expense mExpense;
    private EditText mTitleField;
    private EditText mAmountField;
    private Button mConfirmBtn;
//    private Button mDateButton;
//    private CheckBox mSolvedCheckBox;

    public static ExpenseFragment newInstance(UUID expenseId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXPENSE_ID, expenseId);
        ExpenseFragment fragment = new ExpenseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID expenseId = (UUID) getArguments().getSerializable(ARG_EXPENSE_ID);
        if (expenseId != null) {
            mExpense = ExpenseLab.get(getActivity()).getExpense(expenseId);
        }
        setHasOptionsMenu(true);
        if (mExpense != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Expense: " + mExpense.getTitle());
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Expense");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The third argument of the method below tells whether to add the inflated view to the view's parent
        // Here, we pass in false because we will add the view in the activity's code
        View v = inflater.inflate(R.layout.fragment_expense, container, false);

        mTitleField = (EditText) v.findViewById(R.id.expense_title);
        if (mExpense != null) {
            mTitleField.setText(mExpense.getTitle());
        }

//        mTitleField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // This space intentionally left blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int count, int after) {
//                mExpense.setTitle(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // This space intentionally left blank
//            }
//        });

        // Change amount for expense in onPause() method
        mAmountField = (EditText) v.findViewById(R.id.expense_amount);
        if (mExpense != null) {
            Double proposedAmount = mExpense.getProposedAmount();
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
//                    mExpense.setProposedAmount(amount);
//                } catch (NumberFormatException e) {
//                    // on empty string (when user backspaces) or non-number input, amount should be 0
//                    mExpense.setProposedAmount(0.0);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // This space intentionally left blank
//            }
//        });

//        mDateButton = (Button) v.findViewById(R.id.expense_date);
//        updateDate();
////        mDateButton.setEnabled(false); // Disables button
//        mDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager manager = getChildFragmentManager();
//                DatePickerFragment dialog = DatePickerFragment.newInstance(mExpense.getDate());
//                manager.setFragmentResultListener(DatePickerFragment.ARG_DATE, getViewLifecycleOwner(), new FragmentResultListener() {
//                    @Override
//                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                        Date date = (Date) result.getSerializable(DatePickerFragment.EXTRA_DATE);
//                        mExpense.setDate(date);
//                        updateDate();
//                    }
//                });
//                dialog.show(manager, DIALOG_DATE);
//            }
//        });

//        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.expense_solved);
//        mSolvedCheckBox.setChecked(mExpense.isSolved());
//        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mExpense.setSolved(isChecked);
//            }
//        });

        mConfirmBtn = v.findViewById(R.id.confirm_button);
        mConfirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExpense == null) {
                    Expense expense = new Expense();
                    ExpenseLab expenseLab = ExpenseLab.get(getActivity());
                    expenseLab.addExpense(expense);
                    mExpense = expense;
                }
                String title = mTitleField.getText().toString();
                if (title.isEmpty()) {
                    mTitleField.setError("Title must not be empty");
                } else {
                    mExpense.setTitle(title);
                    try {
                        Double amount = Double.parseDouble(mAmountField.getText().toString());
                        mExpense.setProposedAmount(amount);

//                        mExpense.setDate(mDate);

                        Intent intent = new Intent(ExpenseFragment.this.getActivity(), ExpenseListActivity.class);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        mAmountField.setError("Amount must be a valid number");
                    }
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
//        mExpense.setProposedAmount(amount);
    }

//    private void updateDate() {
////        mDateButton.setText(mExpense.getDate().toString());
//        DateFormat df = new DateFormat();
//        CharSequence formattedDate = df.format("E, MMM d, yyyy", mExpense.getDate());
//        mDateButton.setText(formattedDate);
//    }
}
